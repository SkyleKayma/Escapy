package fr.skyle.escapy.designsystem.core.timePicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.core.button.ProjectButton
import fr.skyle.escapy.designsystem.core.button.ProjectButtonDefaults
import fr.skyle.escapy.designsystem.core.dialog.ProjectDialog
import fr.skyle.escapy.designsystem.core.timePicker.ProjectTimePickerDialogDefaults.Colors.Companion.toTimePickerColors
import fr.skyle.escapy.designsystem.theme.ProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectTimePickerDialog(
    state: TimePickerState,
    confirmText: String,
    dismissText: String,
    onDismissRequest: () -> Unit,
    onConfirmButtonClicked: () -> Unit,
    colors: ProjectTimePickerDialogDefaults.Colors = ProjectTimePickerDialogDefaults.Colors.default(),
    onDismissButtonClicked: (() -> Unit)? = null,
) {
    val timePickerColors = colors.toTimePickerColors()

    ProjectDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TimePicker(
                modifier = Modifier.fillMaxWidth(),
                state = state,
                colors = timePickerColors,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                ProjectButton(
                    modifier = Modifier.wrapContentWidth(),
                    text = dismissText,
                    onClick = {
                        onDismissButtonClicked?.invoke()
                        onDismissRequest()
                    },
                    size = ProjectButtonDefaults.Size.MEDIUM,
                    style = ProjectButtonDefaults.Style.TEXT,
                    tint = ProjectButtonDefaults.Tint.SECONDARY,
                )

                Spacer(modifier = Modifier.width(4.dp))

                ProjectButton(
                    modifier = Modifier.wrapContentWidth(),
                    text = confirmText,
                    onClick = {
                        onConfirmButtonClicked()
                        onDismissRequest()
                    },
                    size = ProjectButtonDefaults.Size.MEDIUM,
                    style = ProjectButtonDefaults.Style.FILLED,
                    tint = ProjectButtonDefaults.Tint.PRIMARY,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ProjectTimePickerDialogPreview() {
    ProjectTheme {
        ProjectTimePickerDialog(
            state = rememberTimePickerState(),
            confirmText = "Confirm",
            dismissText = "Dismiss",
            onDismissRequest = {},
            onConfirmButtonClicked = {},
            onDismissButtonClicked = {},
        )
    }
}