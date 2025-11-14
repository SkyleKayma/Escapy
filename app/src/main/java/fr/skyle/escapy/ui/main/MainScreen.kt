package fr.skyle.escapy.ui.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.main.navigation.ProjectNavHost

@Composable
fun MainScreen() {
    val navHostController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ProjectTheme.colors.surfaceContainerLow,
        contentWindowInsets = WindowInsets(0),
    ) { innerPadding ->
        ProjectNavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            navHostController = navHostController
        )
    }
}