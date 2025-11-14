package fr.skyle.escapy.designsystem.core.topAppBar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

object ProjectTopAppBarDefaults {
    const val TOP_APP_BAR_ACTION_SPACING_DP = 8
    const val TOP_APP_BAR_MIN_SPACING_DP = 8
    const val TOP_APP_BAR_ACTION_HORIZONTAL_PADDING_DP = 20
    const val TOP_APP_BAR_ACTION_VERTICAL_PADDING_DP = 8
    const val TOP_APP_BAR_HEIGHT_DP = 40

    val topAppBarPaddingValues: PaddingValues = PaddingValues(
        horizontal = TOP_APP_BAR_ACTION_HORIZONTAL_PADDING_DP.dp,
        vertical = TOP_APP_BAR_ACTION_VERTICAL_PADDING_DP.dp
    )
}