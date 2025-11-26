package fr.skyle.escapy.ui.screens.changeEmail.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.dialog.ProjectDialog
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.structure.ProjectAlertDialogStructure
import fr.skyle.escapy.utils.ProjectComponentPreview

@Composable
fun EmailVerificationSentDialog(
    onDismissRequest: () -> Unit,
) {
    ProjectDialog(
        onDismissRequest = onDismissRequest,
        content = {
            ProjectAlertDialogStructure(
                title = stringResource(R.string.email_verification_sent_dialog_title),
                subtitle = stringResource(R.string.email_verification_sent_dialog_subtitle),
                buttonText = stringResource(R.string.generic_action_ok),
                onButtonClick = onDismissRequest,
            )
        }
    )
}

@Composable
@ProjectComponentPreview
private fun EmailVerificationSentDialogPreview() {
    ProjectTheme {
        EmailVerificationSentDialog(
            onDismissRequest = {},
        )
    }
}