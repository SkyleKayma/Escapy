package fr.skyle.escapy.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.skyle.escapy.NAVIGATION_HOME
import fr.skyle.escapy.R
import fr.skyle.escapy.ui.home.NewsScreen
import fr.skyle.escapy.ui.theme.EscapyTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EscapyTheme {
                Text(
                    text = "Test"
                )

//                val navController = rememberNavController()
//
//                NavHost(navController = navController, startDestination = NAVIGATION_HOME) {
//                    composable(NAVIGATION_HOME) {
//                        NewsScreen()
//                    }
//                }
            }
        }
    }
}