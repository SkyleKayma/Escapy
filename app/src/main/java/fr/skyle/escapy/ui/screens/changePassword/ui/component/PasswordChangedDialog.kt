package fr.skyle.escapy.ui.screens.changePassword.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.dialog.ProjectDialog
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.structure.ProjectAlertDialogStructure
import fr.skyle.escapy.utils.ProjectComponentPreview

@Composable
fun PasswordChangedDialog(
    onDismissRequest: () -> Unit,
) {
    ProjectDialog(
        onDismissRequest = onDismissRequest,
        content = {
            ProjectAlertDialogStructure(
                title = stringResource(R.string.password_changed_dialog_title),
                buttonText = stringResource(R.string.generic_action_ok),
                onButtonClick = onDismissRequest,
            )
        }
    )
}

@Composable
@ProjectComponentPreview
private fun PasswordChangedDialogPreview() {
    ProjectTheme {
        PasswordChangedDialog(
            onDismissRequest = {},
        )
    }
}