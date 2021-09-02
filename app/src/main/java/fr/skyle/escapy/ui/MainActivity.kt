package fr.skyle.escapy.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.skyle.escapy.NAVIGATION_HOME
import fr.skyle.escapy.ui.news.NewsScreen
import fr.skyle.escapy.ui.theme.EscapyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EscapyTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = NAVIGATION_HOME) {
                    composable(NAVIGATION_HOME) {
                        NewsScreen()
                    }
                }
            }
        }
    }
}