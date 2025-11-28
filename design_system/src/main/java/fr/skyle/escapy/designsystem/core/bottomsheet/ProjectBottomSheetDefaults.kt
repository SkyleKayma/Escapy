package fr.skyle.escapy.designsystem.core.bottomsheet

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.theme.ExtraLargeRadiusToken

object ProjectBottomSheetDefaults {
    const val BOTTOM_SHEET_HORIZONTAL_PADDING_DP = 24
    const val BOTTOM_SHEET_VERTICAL_PADDING_DP = 20

    val bottomSheetShape: Shape =
        RoundedCornerShape(
            topStart = ExtraLargeRadiusToken.value.dp,
            topEnd = ExtraLargeRadiusToken.value.dp,
        )

    val bottomSheetPaddingValues: PaddingValues =
        PaddingValues(
            horizontal = BOTTOM_SHEET_HORIZONTAL_PADDING_DP.dp,
            vertical = BOTTOM_SHEET_VERTICAL_PADDING_DP.dp
        )
}