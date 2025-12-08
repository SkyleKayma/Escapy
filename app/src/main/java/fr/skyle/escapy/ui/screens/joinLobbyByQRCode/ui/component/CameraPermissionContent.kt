package fr.skyle.escapy.ui.screens.joinLobbyByQRCode.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.button.ProjectButton
import fr.skyle.escapy.designsystem.core.button.ProjectButtonDefaults
import fr.skyle.escapy.designsystem.ext.values
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.launchSettingsActivity
import fr.skyle.escapy.utils.ProjectComponentPreview

@Composable
fun CameraPermissionContent(
    modifier: Modifier = Modifier,
    shouldShowCameraPermissionRationale: Boolean,
    onLaunchPermissionRequest: () -> Unit
) {
    val context = LocalContext.current

    val description = if (shouldShowCameraPermissionRationale) {
        stringResource(id = R.string.permission_camera_refused_enable_in_settings)
    } else {
        stringResource(id = R.string.permission_camera_required)
    }

    val buttonText = if (shouldShowCameraPermissionRationale) {
        stringResource(id = R.string.permission_camera_settings)
    } else {
        stringResource(id = R.string.permission_camera_authorize)
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description,
            style = ProjectTheme.typography.p1,
            color = ProjectTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProjectButton(
            modifier = Modifier.wrapContentWidth(),
            onClick = {
                if (shouldShowCameraPermissionRationale) {
                    context.launchSettingsActivity()
                } else {
                    onLaunchPermissionRequest()
                }
            },
            text = buttonText,
            style = ProjectButtonDefaults.Style.FILLED,
            tint = ProjectButtonDefaults.Tint.PRIMARY,
            size = ProjectButtonDefaults.Size.LARGE,
        )
    }
}

private class CameraPermissionContentPreviewDataProvider :
    CollectionPreviewParameterProvider<CameraPermissionContentPreviewData>(
        collection = buildList {
            Boolean.values.forEach { shouldShowCameraPermissionRationale ->
                add(
                    CameraPermissionContentPreviewData(
                        shouldShowCameraPermissionRationale = shouldShowCameraPermissionRationale,
                    )
                )
            }
        }
    )

private data class CameraPermissionContentPreviewData(
    val shouldShowCameraPermissionRationale: Boolean
)

@ProjectComponentPreview
@Composable
private fun CameraPermissionContentPreview(
    @PreviewParameter(CameraPermissionContentPreviewDataProvider::class) data: CameraPermissionContentPreviewData
) {
    ProjectTheme {
        CameraPermissionContent(
            shouldShowCameraPermissionRationale = data.shouldShowCameraPermissionRationale,
            onLaunchPermissionRequest = {}
        )
    }
}