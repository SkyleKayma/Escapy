package fr.skyle.escapy.ui.screens.lobby.ui

import androidx.compose.runtime.Composable

@Composable
fun LobbyRoute(
    navigateBack: () -> Unit,
) {
    LobbyScreen(
        navigateBack = navigateBack,
    )
}