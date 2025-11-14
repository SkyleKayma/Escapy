package fr.skyle.escapy.ui.screens.login.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.screen.ProjectBackgroundLogoScreen

@Composable
fun LoginScreen() {
    ProjectBackgroundLogoScreen {
        Column(modifier = Modifier.fillMaxSize()) {

        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    ProjectTheme {

    }
}