package fr.skyle.escapy.ui.screens.about.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AboutRoute(
    navigateBack: () -> Unit,
    aboutViewModel: AboutViewModel = hiltViewModel()
) {
    val aboutState by aboutViewModel.state.collectAsStateWithLifecycle()

    AboutScreen(
        state = aboutState,
        navigateBack = navigateBack,
    )
}