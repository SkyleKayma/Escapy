package fr.skyle.escapy.ui.screens.joinLobbyByQRCode.ui

import androidx.camera.core.ImageAnalysis
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.common.Barcode
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.MIN_DELAY_TO_SHOW_LOADER
import fr.skyle.escapy.data.usecase.lobby.ValidateLobbyQRCodeUseCase
import fr.skyle.escapy.data.usecase.lobby.ValidateLobbyQRCodeUseCaseInvalidQRCode
import fr.skyle.escapy.utils.BarcodeAnalyzer
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@OptIn(FlowPreview::class)
@HiltViewModel
class JoinLobbyByQRCodeViewModel @Inject constructor(
    private val validateLobbyQRCodeUseCase: ValidateLobbyQRCodeUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow<State>(State())
    val state: StateFlow<State> by lazy { _state.asStateFlow() }

    // Camera
    private val cameraExecutor = Executors.newSingleThreadExecutor()

    private var lastScanTime = 0L
    private var barcodeAnalyzer: BarcodeAnalyzer =
        BarcodeAnalyzer { barcode ->
            val now = System.currentTimeMillis()

            if (state.value.isLoading) {
                return@BarcodeAnalyzer
            }

            if (now - lastScanTime < DELAY_BETWEEN_EACH_SCAN.inWholeMilliseconds) {
                return@BarcodeAnalyzer
            }

            lastScanTime = now

            handleBarcode(barcode)
        }
    var imageAnalysis: ImageAnalysis =
        ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .apply { setAnalyzer(cameraExecutor, barcodeAnalyzer) }

    private fun handleBarcode(barcode: Barcode) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            try {
                val qrInfo = barcode.displayValue?.toJoinLobbyQRCodeInfo()

                if (qrInfo != null) {
                    val minDelayJob = async(SupervisorJob()) { delay(MIN_DELAY_TO_SHOW_LOADER) }

                    val validateJob = async(SupervisorJob()) {
                        validateLobbyQRCodeUseCase(
                            lobbyId = qrInfo.lobbyId,
                            password = qrInfo.password
                        )
                    }

                    awaitAll(minDelayJob, validateJob)

                    _state.update {
                        it.copy(
                            event = JoinLobbyByQRCodeEvent.ScanSuccess(qrInfo.lobbyId)
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            event = JoinLobbyByQRCodeEvent.InvalidQRCode
                        )
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: ValidateLobbyQRCodeUseCaseInvalidQRCode) {
                Timber.e(e)
                _state.update {
                    it.copy(
                        event = JoinLobbyByQRCodeEvent.InvalidQRCode
                    )
                }
            } catch (e: Exception) {
                Timber.e(e)
                _state.update {
                    it.copy(
                        event = JoinLobbyByQRCodeEvent.ScanError(e.message)
                    )
                }
            } finally {
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    fun eventDelivered() {
        _state.update {
            it.copy(
                event = null
            )
        }
    }

    data class State(
        val isLoading: Boolean = false,
        val isFlashOn: Boolean = false,
        val event: JoinLobbyByQRCodeEvent? = null,
    )

    sealed interface JoinLobbyByQRCodeEvent {
        data class ScanSuccess(val lobbyId: String) : JoinLobbyByQRCodeEvent
        data object InvalidQRCode : JoinLobbyByQRCodeEvent
        data class ScanError(val message: String?) : JoinLobbyByQRCodeEvent
    }

    companion object {
        private val DELAY_BETWEEN_EACH_SCAN = 2.seconds
    }
}