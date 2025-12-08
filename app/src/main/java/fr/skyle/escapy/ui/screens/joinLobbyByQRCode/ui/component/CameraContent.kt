package fr.skyle.escapy.ui.screens.joinLobbyByQRCode.ui.component

import android.widget.FrameLayout
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import fr.skyle.escapy.designsystem.theme.ProjectTheme

@Composable
fun CameraContent(
    imageAnalysis: ImageAnalysis?,
    isFlashOn: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var camera by remember { mutableStateOf<Camera?>(null) }
    val cameraProviderFuture by remember {
        mutableStateOf(ProcessCameraProvider.getInstance(context))
    }

    LaunchedEffect(key1 = isFlashOn, block = {
        camera?.cameraControl?.enableTorch(isFlashOn)
    })

    Box(modifier = modifier) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { viewContext ->
                val previewView = PreviewView(viewContext)

                cameraProviderFuture.apply {
                    addListener(
                        {
                            val preview = androidx.camera.core.Preview.Builder().build().also {
                                it.surfaceProvider = previewView.surfaceProvider
                            }

                            try {
                                val cameraProvider = get()

                                cameraProvider.unbindAll()

                                camera = cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    CameraSelector.DEFAULT_BACK_CAMERA,
                                    imageAnalysis,
                                    preview
                                )
                            } catch (e: Exception) {
                                // Already bind
                            }
                        },
                        ContextCompat.getMainExecutor(context)
                    )
                }

                val frame = FrameLayout(context)
                frame.addView(previewView)
                frame
            }
        )
    }
}

@Preview
@Composable
private fun CameraContentPreview() {
    ProjectTheme {
        CameraContent(
            imageAnalysis = null,
            isFlashOn = false
        )
    }
}