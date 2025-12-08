package fr.skyle.escapy.utils

import android.annotation.SuppressLint
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import timber.log.Timber

class BarcodeAnalyzer(
    private val onBarCodeDetected: (barcode: Barcode) -> Unit
) : ImageAnalysis.Analyzer {

    // Options
    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
        .build()

    // Scanner
    private val scanner = BarcodeScanning.getClient(options)

    @SuppressLint("UnsafeExperimentalUsageError")
    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.image?.let {
            // Rotate image
            val visionImage =
                InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)

            // Process
            scanner.process(visionImage)
                .addOnSuccessListener { barcodes ->
                    barcodes.firstOrNull()?.let { barcode ->
                        onBarCodeDetected(barcode)
                    }
                    imageProxy.close()
                }.addOnFailureListener {
                    Timber.e(it)
                    imageProxy.close()
                }
        }
    }
}