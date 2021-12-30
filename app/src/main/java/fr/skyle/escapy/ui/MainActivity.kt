package fr.skyle.escapy.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.skyle.escapy.NAVIGATION_LOGIN
import fr.skyle.escapy.NAVIGATION_NEWS
import fr.skyle.escapy.ui.login.LoginScreen
import fr.skyle.escapy.ui.home.HomeScreen
import fr.skyle.escapy.ui.theme.EscapyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EscapyTheme {
                Text(
                    text = "Test"
                )

                NavHost(navController = navController, startDestination = NAVIGATION_LOGIN) {
                    composable(NAVIGATION_LOGIN) {
                        LoginScreen()
                    }
                    composable(NAVIGATION_NEWS) {
                        HomeScreen()
                    }
                }
            }
        }
    }
}