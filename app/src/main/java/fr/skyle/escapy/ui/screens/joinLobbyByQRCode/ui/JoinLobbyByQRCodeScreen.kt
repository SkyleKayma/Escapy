package fr.skyle.escapy.ui.screens.joinLobbyByQRCode.ui

import androidx.camera.core.ImageAnalysis
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.core.snackbar.state.ProjectSnackbarState
import fr.skyle.escapy.designsystem.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.designsystem.core.topAppBar.ProjectTopAppBar
import fr.skyle.escapy.designsystem.core.topAppBar.component.ProjectTopAppBarItem
import fr.skyle.escapy.designsystem.ext.values
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.ui.screens.joinLobbyByQRCode.ui.component.CameraContent
import fr.skyle.escapy.ui.screens.joinLobbyByQRCode.ui.component.CameraPermissionContent
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.ProjectScreenPreview

@Composable
fun JoinLobbyByQRCodeScreen(
    snackbarState: ProjectSnackbarState,
    state: JoinLobbyByQRCodeViewModel.State,
    imageAnalysis: ImageAnalysis?,
    isCameraPermissionGranted: Boolean,
    shouldShowCameraPermissionRationale: Boolean,
    onLaunchPermissionRequest: () -> Unit,
    navigateBack: () -> Unit,
) {
    ProjectScreenStructure(
        modifier = Modifier.fillMaxSize(),
        isPatternDisplayed = true,
        snackbarState = snackbarState,
        topContent = {
            ProjectTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.join_lobby_by_qrcode_screen_title),
                leadingContent = {
                    ProjectTopAppBarItem(
                        icon = rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowBack),
                        style = ProjectIconButtonDefaults.Style.FILLED,
                        onClick = navigateBack,
                    )
                },
            )
        }
    ) { innerPadding ->
        JoinLobbyByQRCodeScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            isCameraPermissionGranted = isCameraPermissionGranted,
            shouldShowCameraPermissionRationale = shouldShowCameraPermissionRationale,
            imageAnalysis = imageAnalysis,
            isFlashOn = state.isFlashOn,
            onLaunchPermissionRequest = onLaunchPermissionRequest,
        )
    }
}

@Composable
private fun JoinLobbyByQRCodeScreenContent(
    innerPadding: PaddingValues,
    isCameraPermissionGranted: Boolean,
    shouldShowCameraPermissionRationale: Boolean,
    imageAnalysis: ImageAnalysis?,
    isFlashOn: Boolean,
    onLaunchPermissionRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(top = innerPadding.calculateTopPadding())
            .padding(24.dp),
    ) {
        if (isCameraPermissionGranted) {
            CameraContent(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(ProjectTheme.shape.large),
                imageAnalysis = imageAnalysis,
                isFlashOn = isFlashOn
            )
        } else {
            CameraPermissionContent(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                shouldShowCameraPermissionRationale = shouldShowCameraPermissionRationale,
                onLaunchPermissionRequest = onLaunchPermissionRequest,
            )
        }
    }
}

@ProjectScreenPreview
@Composable
private fun JoinLobbyByQRCodeScreenPreview() {
    ProjectTheme {
        JoinLobbyByQRCodeScreen(
            snackbarState = rememberProjectSnackbarState(),
            state = JoinLobbyByQRCodeViewModel.State(),
            imageAnalysis = null,
            isCameraPermissionGranted = true,
            shouldShowCameraPermissionRationale = false,
            onLaunchPermissionRequest = {},
            navigateBack = {},
        )
    }
}

private class JoinLobbyByQRCodeScreenContentPreviewDataProvider :
    CollectionPreviewParameterProvider<JoinLobbyByQRCodeScreenContentPreviewData>(
        collection = buildList {
            Boolean.values.forEach { isCameraPermissionGranted ->
                Boolean.values.forEach { shouldShowCameraPermissionRationale ->
                    Boolean.values.forEach { isFlashOn ->
                        add(
                            JoinLobbyByQRCodeScreenContentPreviewData(
                                isCameraPermissionGranted = isCameraPermissionGranted,
                                shouldShowCameraPermissionRationale = shouldShowCameraPermissionRationale,
                                isFlashOn = isFlashOn,
                            )
                        )
                    }
                }
            }
        }
    )

private data class JoinLobbyByQRCodeScreenContentPreviewData(
    val isCameraPermissionGranted: Boolean,
    val shouldShowCameraPermissionRationale: Boolean,
    val isFlashOn: Boolean,
)

@ProjectComponentPreview
@Composable
private fun JoinLobbyByQRCodeScreenContentPreview(
    @PreviewParameter(JoinLobbyByQRCodeScreenContentPreviewDataProvider::class) data: JoinLobbyByQRCodeScreenContentPreviewData
) {
    ProjectTheme {
        JoinLobbyByQRCodeScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = PaddingValues(),
            isCameraPermissionGranted = data.isCameraPermissionGranted,
            shouldShowCameraPermissionRationale = data.shouldShowCameraPermissionRationale,
            imageAnalysis = null,
            isFlashOn = data.isFlashOn,
            onLaunchPermissionRequest = {},
        )
    }
}