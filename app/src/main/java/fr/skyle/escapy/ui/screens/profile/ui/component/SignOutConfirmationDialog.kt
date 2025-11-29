package fr.skyle.escapy.ui.screens.profile.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.dialog.ProjectDialog
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.structure.ProjectDialogStructure
import fr.skyle.escapy.utils.ProjectComponentPreview

@Composable
fun SignOutConfirmationDialog(
    onSignOutClicked: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    ProjectDialog(
        onDismissRequest = onDismissRequest,
        content = {
            ProjectDialogStructure(
                title = stringResource(R.string.sign_out_confirmation_dialog_title),
                subtitle = stringResource(R.string.sign_out_confirmation_dialog_subtitle),
                leftButtonText = stringResource(R.string.generic_action_no),
                rightButtonText = stringResource(R.string.generic_action_yes),
                onLeftButtonClick = onDismissRequest,
                onRightButtonClick = onSignOutClicked,
            )
        }
    )
}

@Composable
@ProjectComponentPreview
private fun SignOutConfirmationDialogPreview() {
    ProjectTheme {
        SignOutConfirmationDialog(
            onSignOutClicked = {},
            onDismissRequest = {},
        )
    }
}