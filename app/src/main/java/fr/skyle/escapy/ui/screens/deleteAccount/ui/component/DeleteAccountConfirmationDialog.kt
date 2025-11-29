package fr.skyle.escapy.ui.screens.deleteAccount.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.dialog.ProjectDialog
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.structure.ProjectDialogStructure
import fr.skyle.escapy.utils.ProjectComponentPreview

@Composable
fun DeleteAccountConfirmationDialog(
    onDeleteAccountClicked: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    ProjectDialog(
        onDismissRequest = onDismissRequest,
        content = {
            ProjectDialogStructure(
                title = stringResource(R.string.delete_account_confirmation_dialog_title),
                subtitle = stringResource(R.string.delete_account_confirmation_dialog_subtitle),
                leftButtonText = stringResource(R.string.generic_action_cancel),
                rightButtonText = stringResource(R.string.generic_action_delete),
                onLeftButtonClick = onDismissRequest,
                onRightButtonClick = onDeleteAccountClicked,
            )
        }
    )
}

@Composable
@ProjectComponentPreview
private fun DeleteAccountConfirmationDialogPreview() {
    ProjectTheme {
        DeleteAccountConfirmationDialog(
            onDeleteAccountClicked = {},
            onDismissRequest = {},
        )
    }
}