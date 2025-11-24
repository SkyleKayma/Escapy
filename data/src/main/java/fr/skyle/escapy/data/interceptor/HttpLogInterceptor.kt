package fr.skyle.escapy.data.interceptor

import android.text.SpannableStringBuilder
import okhttp3.*
import okhttp3.internal.platform.Platform
import okio.Buffer
import okio.GzipSource
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class HttpLogInterceptor @Inject constructor() : Interceptor {

    private val logger: Logger = Logger.DEFAULT

    var level = Level()

    /**
     * isRequestLogEnabled:
     * ➡️ POST https://...
     * 🟢 200 https://...
     * 🟠 304 https://...
     * 🔴 500 https://...
     *
     * isRequestHeadersLogEnabled:
     * | Accept:
     * | X-AUTH-TOKEN:
     * | Content-Type:
     * ...
     *
     * isRequestBodyLogEnabled:
     * { ... }
     *
     * isResponseHeadersLogEnabled:
     * | etag:
     * | content-type:
     * | date:
     * ...
     *
     * isResponseBodyLogEnabled:
     * { ... }
     *
     * isCurLEnabled:
     * cURL -v -X GET -H "Accept-Encoding: gzip" -H "Accept-Language: fr" ...
     */
    data class Level(
        val isRequestLogEnabled: Boolean = false,
        val isCallHeadersLogEnabled: Boolean = false,
        val isCallBodyLogEnabled: Boolean = false,
        val isResponseHeadersLogEnabled: Boolean = false,
        val isResponseBodyLogEnabled: Boolean = false,
        val isCurLEnabled: Boolean = false,
        val maxBodySize: Int = 1000
    )

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // If there is nothing to log, don't go further
        if (!level.isRequestLogEnabled && !level.isCallHeadersLogEnabled && !level.isCallBodyLogEnabled
            && !level.isResponseHeadersLogEnabled && !level.isResponseBodyLogEnabled
        )
            return chain.proceed(request)

        // Log request
        logCall(request)

        // Start WS call
        val startNs = System.nanoTime()
        val response: Response

        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            logger.log("<-- HTTP FAILED: $e")
            throw e
        }

        // Log response
        logResponse(response, startNs)

        return response
    }

    private fun logCall(request: Request) {
        val requestBody = request.body

        val httpCallRequest = getCallRequest(request)
        val httpCallHeaders = getCallHeaders(request, requestBody)
        val httpCallCurL = getCallCurl(request)
        val httpCallBody = getCallBody(request, requestBody)

        synchronized(logger) {
            if (level.isRequestLogEnabled) {
                logger.log(httpCallRequest)
            }

            if (level.isCallHeadersLogEnabled) {
                logger.log(httpCallHeaders)
            }

            if (level.isCurLEnabled) {
                logger.log(httpCallCurL)
            }

            if (level.isCallBodyLogEnabled && requestBody != null && requestBody.contentLength() != 0L) {
                logger.log(httpCallBody)
            }
        }
    }

    private fun getCallRequest(request: Request): String =
        ("➡️ ${request.method} ${request.url}")

    private fun getCallHeaders(request: Request, requestBody: RequestBody?): String {
        val headers = request.headers

        var textHeaders = ""

        requestBody?.let { body ->
            // Request body headers are only present when installed as a network interceptor. When not
            // already present, force them to be included (if available) so their values are known.
            body.contentType()?.let {
                if (headers["Content-Type"] == null) {
                    textHeaders += "| Content-Type: $it \n"
                }
            }

            if (body.contentLength() != -1L) {
                if (headers["Content-Length"] == null) {
                    textHeaders += "| Content-Length: ${body.contentLength()} \n"
                }
            }
        }

        for (i in 0 until headers.size) {
            textHeaders += getHeaderAtPos(headers, i)
        }

        return textHeaders
    }

    private fun getCallCurl(request: Request): String =
        getCallCurLPretty(SpannableStringBuilder().apply {
            // Add cURL command
            append("cURL -v ")

            // Add request type
            append("-X ${request.method.uppercase()} ")

            // Adding headers
            for (headerName in request.headers.names()) {
                append(getCallCurLHeader(headerName, request.headers[headerName]!!))
            }

            // Adding request body
            request.body?.let {
                val buffer = Buffer()
                it.writeTo(buffer)

                val charset: Charset
                it.contentType()?.let { contentType ->
                    append(getCallCurLHeader(CONTENT_TYPE, contentType.toString()))
                    charset = contentType.charset(Charsets.UTF_8) ?: Charsets.UTF_8
                    append("-d '${buffer.readString(charset)}' ")
                }
            }

            // Add request URL
            append("-L \"${request.url}\"")
        }.toString())

    private fun getCallCurLPretty(textCurL: String): String =
        StringBuilder()
            .append(DIVIDER)
            .appendLine()
            .append(textCurL)
            .appendLine()
            .append(DIVIDER)
            .toString()

    private fun getCallCurLHeader(headerName: String, headerValue: String) =
        "-H \"$headerName: $headerValue\" "

    private fun getCallBody(request: Request, requestBody: RequestBody?): String {
        var textBody = ""

        if (bodyHasUnknownEncoding(request.headers)) {
            textBody += "| BODY: omitted cause of unknown encoding"
        } else if (requestBody?.isDuplex() == true) {
            textBody += "| BODY: duplex request body omitted"
        } else if (requestBody?.isOneShot() == true) {
            textBody += "| BODY: one-shot body omitted"
        } else {
            val buffer = Buffer()
            requestBody?.writeTo(buffer)

            val contentType = requestBody?.contentType()
            val charset = contentType?.charset(Charsets.UTF_8) ?: Charsets.UTF_8

            if (isProbablyUtf8(buffer)) {
                if (level.isCallHeadersLogEnabled) {
                    textBody += "| BODY: (${requestBody?.contentLength()}-byte body) \n"
                }
                textBody += buffer.readString(charset) + "\n"
            } else {
                textBody += "| BODY: binary ${requestBody?.contentLength()}-byte body omitted"
            }
        }

        return textBody
    }

    private fun logResponse(response: Response, startNs: Long) {
        val responseBody = response.body!!

        val httpResponseRequest = getResponseRequest(response, startNs)
        val httpResponseHeaders = getResponseHeaders(response)
        val httpResponseBody = getResponseBody(response, responseBody).let {
            if (level.maxBodySize > -1) {
                it.take(level.maxBodySize)
            } else {
                it
            }
        }

        synchronized(logger) {
            if (level.isRequestLogEnabled) {
                logger.log(httpResponseRequest)
            }

            if (level.isResponseHeadersLogEnabled) {
                logger.log(httpResponseHeaders)
            }

            if (level.isResponseBodyLogEnabled) {
                logger.log(httpResponseBody)
            }
        }
    }

    private fun getResponseRequest(response: Response, startNs: Long): String {
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val successStatus = when (response.code) {
            in 100..199 -> "🟡"
            in 200..299 -> "🟢"
            in 300..399 -> "🟠️"
            else -> "🔴"
        }

        return "$successStatus ${response.code}${if (response.message.isEmpty()) "" else ' ' + response.message} ${response.request.url} (${tookMs}ms)"
    }

    private fun getResponseHeaders(response: Response): String {
        var textHeaders = ""

        val headers = response.headers
        for (i in 0 until headers.size) {
            textHeaders += getHeaderAtPos(headers, i)
        }

        return textHeaders
    }

    private fun getResponseBody(response: Response, responseBody: ResponseBody): String {
        try {
            var textBody = ""

            val headers = response.headers
            val contentLength = responseBody.contentLength()

            if (bodyHasUnknownEncoding(headers)) {
                textBody += "| BODY: encoded body omitted"
                return textBody
            } else {
                val source = responseBody.source()
                source.request(Long.MAX_VALUE) // Buffer the entire body.
                var buffer = source.buffer

                var gzippedLength: Long? = null
                if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
                    gzippedLength = buffer.size
                    GzipSource(buffer.clone()).use { gzippedResponseBody ->
                        buffer = Buffer()
                        buffer.writeAll(gzippedResponseBody)
                    }
                }

                val contentType = responseBody.contentType()
                val charset = contentType?.charset(Charsets.UTF_8) ?: Charsets.UTF_8

                if (!isProbablyUtf8(buffer)) {
                    textBody += "| BODY: binary ${buffer.size}-byte body omitted"
                    return textBody
                }

                if (level.isResponseHeadersLogEnabled) {
                    textBody += "| BODY: (${buffer.size}-byte" + (gzippedLength?.let { " $gzippedLength-gzipped-byte body" } ?: "" + ")\n")
                }

                if (contentLength != 0L) {
                    textBody += buffer.clone().readString(charset) + "\n"
                }

                return textBody
            }
        } catch (e: Throwable) {
            return "Can't show body content, size too large"
        }
    }

    // Useful methods
    // -------------------------------------------------

    private fun getHeaderAtPos(headers: Headers, i: Int): String =
        "| " + headers.name(i) + ": " + headers.value(i) + "\n"

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"] ?: return false
        return !contentEncoding.equals("identity", ignoreCase = true) &&
            !contentEncoding.equals("gzip", ignoreCase = true)
    }

    private fun isProbablyUtf8(buffer: Buffer): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = buffer.size.coerceAtMost(64)
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0 until 16) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (_: EOFException) {
            return false
        }
    }

    // Interface
    // -------------------------------------------------

    interface Logger {
        fun log(message: String)

        companion object {
            val DEFAULT: Logger = object : Logger {
                override fun log(message: String) {
                    Platform.get().log(message)
                }
            }
        }
    }

    companion object {
        private const val DIVIDER = "────────────────────────────────────────────"
        private const val CONTENT_TYPE = "Content-Type"
    }
}