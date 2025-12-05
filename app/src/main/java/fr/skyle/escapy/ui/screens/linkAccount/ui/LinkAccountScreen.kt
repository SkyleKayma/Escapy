package fr.skyle.escapy.ui.screens.linkAccount.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.core.topAppBar.ProjectTopAppBar
import fr.skyle.escapy.designsystem.core.topAppBar.component.ProjectTopAppBarItem
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.ui.screens.linkAccount.ui.component.LinkAccountAuthProviderCell
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.ProjectScreenPreview

@Composable
fun LinkAccountScreen(
    onAuthProviderClicked: (AuthProvider) -> Unit,
    navigateBack: () -> Unit,
) {
    ProjectScreenStructure(
        modifier = Modifier.fillMaxSize(),
        isPatternDisplayed = true,
        topContent = {
            ProjectTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.link_account_screen_title),
                leadingContent = {
                    ProjectTopAppBarItem(
                        icon = rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowBack),
                        style = ProjectIconButtonDefaults.Style.FILLED,
                        onClick = navigateBack,
                    )
                },
            )
        }
    ) { innerPadding ->
        LinkAccountScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            onAuthProviderClicked = onAuthProviderClicked
        )
    }
}

@Composable
private fun LinkAccountScreenContent(
    innerPadding: PaddingValues,
    onAuthProviderClicked: (AuthProvider) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = innerPadding.calculateTopPadding())
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.link_account_why_to_link),
            style = ProjectTheme.typography.p2,
            color = ProjectTheme.colors.onSurface,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.link_account_select_link_type),
            style = ProjectTheme.typography.b2,
            color = ProjectTheme.colors.primary,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AuthProvider.entries
                .filter { it != AuthProvider.ANONYMOUS }
                .forEach { authProvider ->
                    LinkAccountAuthProviderCell(
                        modifier = Modifier.fillMaxWidth(),
                        authProvider = authProvider,
                        onCellClicked = {
                            onAuthProviderClicked(authProvider)
                        }
                    )
                }
        }
    }
}

@ProjectScreenPreview
@Composable
private fun LinkAccountScreenPreview() {
    ProjectTheme {
        LinkAccountScreen(
            onAuthProviderClicked = {},
            navigateBack = {},
        )
    }
}

@ProjectComponentPreview
@Composable
private fun LinkAccountScreenContentPreview() {
    ProjectTheme {
        LinkAccountScreenContent(
            innerPadding = PaddingValues(),
            onAuthProviderClicked = {},
        )
    }
}