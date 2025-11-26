package fr.skyle.escapy.designsystem.core.dialog

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import fr.skyle.escapy.designsystem.theme.ProjectTheme

object ProjectDialogDefaults {

    private const val DIALOG_PADDING_DP = 20

    val dialogShape: Shape
        @Composable
        get() = ProjectTheme.shape.large

    val dialogPaddingValues: PaddingValues =
        PaddingValues(DIALOG_PADDING_DP.dp)

    val dialogButtonsOrientation: ButtonsOrientation =
        ButtonsOrientation.HORIZONTAL

    val defaultDialogProperties: DialogProperties
        get() = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = true,
        )

    enum class ButtonsOrientation {
        HORIZONTAL,
        VERTICAL
    }
}