package fr.skyle.escapy.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import fr.skyle.escapy.ui.login.state.LoginScreenLoaded
import org.koin.androidx.compose.getViewModel


@Composable
fun LoginScreen(
    model: LoginScreenViewModel = getViewModel()
) {
    val eventState by model.eventFlow.collectAsState()

    LoginScreenLoaded()
}