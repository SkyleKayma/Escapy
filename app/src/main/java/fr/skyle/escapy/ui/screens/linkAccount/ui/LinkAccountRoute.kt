package fr.skyle.escapy.ui.screens.linkAccount.ui

import androidx.compose.runtime.Composable
import fr.skyle.escapy.data.enums.AuthProvider

@Composable
fun LinkAccountRoute(
    navigateToLinkAccountWithEmail: () -> Unit,
    navigateBack: () -> Unit,
) {
    LinkAccountScreen(
        onAuthProviderClicked = { authProvider ->
            when (authProvider) {
                AuthProvider.EMAIL -> {
                    navigateToLinkAccountWithEmail()
                }

                AuthProvider.GOOGLE -> {
                    // TODO
                }

                AuthProvider.ANONYMOUS -> Unit
            }
        },
        navigateBack = navigateBack,
    )
}