package fr.skyle.escapy.ui.core.snackbar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.core.button.ProjectButton
import fr.skyle.escapy.designsystem.core.button.ProjectButtonDefaults
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.snackbar.state.ProjectSnackbarState

@Composable
fun ProjectSnackbar(
    state: ProjectSnackbarState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        modifier = modifier,
        hostState = state.snackbarHostState
    ) {
        val snackbarData = remember(it) {
            state.currentSnackbarData
        }

        snackbarData?.let {
            ProjectSnackbarContent(
                modifier = Modifier.padding(ProjectSnackbarDefaults.snackbarPadding),
                type = snackbarData.type,
                message = snackbarData.message,
                actionLabel = snackbarData.actionLabel,
                onActionClicked = snackbarData::performAction
            )
        }
    }
}

@Composable
private fun ProjectSnackbarContent(
    message: String,
    type: ProjectSnackbarDefaults.ProjectSnackbarType,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onActionClicked: (() -> Unit)? = null
) {
    Snackbar(
        modifier = modifier
            .border(
                width = ProjectSnackbarDefaults.SNACKBAR_BORDER_WIDTH_DP.dp,
                shape = ProjectSnackbarDefaults.snackbarShape,
                color = ProjectSnackbarDefaults.borderColor
            ),
        shape = ProjectSnackbarDefaults.snackbarShape,
        containerColor = ProjectSnackbarDefaults.containerColor,
        contentColor = ProjectSnackbarDefaults.contentColor,
        action = actionLabel?.let {
            {
                ProjectButton(
                    modifier = Modifier,
                    text = actionLabel,
                    onClick = {
                        onActionClicked?.invoke()
                    },
                    style = ProjectButtonDefaults.ButtonStyle.TEXT,
                    tint = ProjectButtonDefaults.ButtonTint.PRIMARY,
                    size = ProjectButtonDefaults.ButtonSize.SMALL
                )
            }
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val painter = when (type) {
                ProjectSnackbarDefaults.ProjectSnackbarType.SUCCESS ->
                    rememberVectorPainter(Icons.Default.Check)

                ProjectSnackbarDefaults.ProjectSnackbarType.ERROR ->
                    rememberVectorPainter(Icons.Default.Close)
            }

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painter,
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                modifier = Modifier.weight(1f),
                text = message,
                style = ProjectSnackbarDefaults.textStyle
            )
        }
    }
}

@Preview
@Composable
private fun ProjectSnackbarPreview() {
    ProjectTheme {
        ProjectSnackbarContent(
            message = "message",
            type = ProjectSnackbarDefaults.ProjectSnackbarType.SUCCESS
        )
    }
}

@Composable
@Preview
private fun ProjectSnackbarWithActionPreview() {
    ProjectTheme {
        ProjectSnackbarContent(
            message = "message",
            type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR,
            actionLabel = "action",
            onActionClicked = {}
        )
    }
}