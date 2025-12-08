package fr.skyle.escapy.ui.screens.joinLobbyByQRCode.ui

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.snackbar.ProjectSnackbarDefaults
import fr.skyle.escapy.designsystem.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.ui.core.dialog.ProjectLoadingDialog

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun JoinLobbyByQRCodeRoute(
    navigateToLobby: (lobbyId: String) -> Unit,
    navigateBack: () -> Unit,
    joinLobbyByQRCodeViewModel: JoinLobbyByQRCodeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val haptics = LocalHapticFeedback.current

    val joinLobbyByQRCodeState by joinLobbyByQRCodeViewModel.state.collectAsStateWithLifecycle()

    val snackbarState = rememberProjectSnackbarState()

    val cameraPermissionState = rememberPermissionState(
        permission = Manifest.permission.CAMERA
    )

    LaunchedEffect(joinLobbyByQRCodeState.event) {
        joinLobbyByQRCodeState.event?.let { event ->
            when (event) {
                JoinLobbyByQRCodeViewModel.JoinLobbyByQRCodeEvent.InvalidQRCode -> {
                    haptics.performHapticFeedback(HapticFeedbackType.Reject)
                    snackbarState.showSnackbar(
                        message = context.getString(R.string.join_lobby_by_qrcode_error_qrcode_invalid),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }

                is JoinLobbyByQRCodeViewModel.JoinLobbyByQRCodeEvent.ScanError -> {
                    haptics.performHapticFeedback(HapticFeedbackType.Reject)
                    snackbarState.showSnackbar(
                        message = context.getString(
                            R.string.generic_error_format,
                            event.message ?: "-"
                        ),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }

                is JoinLobbyByQRCodeViewModel.JoinLobbyByQRCodeEvent.ScanSuccess -> {
                    haptics.performHapticFeedback(HapticFeedbackType.Confirm)
                    navigateToLobby(event.lobbyId)
                }
            }

            joinLobbyByQRCodeViewModel.eventDelivered()
        }
    }

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    JoinLobbyByQRCodeScreen(
        snackbarState = snackbarState,
        state = joinLobbyByQRCodeState,
        imageAnalysis = joinLobbyByQRCodeViewModel.imageAnalysis,
        isCameraPermissionGranted = cameraPermissionState.status.isGranted,
        shouldShowCameraPermissionRationale = cameraPermissionState.status.shouldShowRationale,
        onLaunchPermissionRequest = {
            cameraPermissionState.launchPermissionRequest()
        },
        navigateBack = navigateBack,
    )

    if (joinLobbyByQRCodeState.isLoading) {
        ProjectLoadingDialog(
            title = stringResource(R.string.generic_loading_verifying),
        )
    }
}