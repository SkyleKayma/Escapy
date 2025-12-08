package fr.skyle.escapy.ui.screens.lobby.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.core.topAppBar.ProjectTopAppBar
import fr.skyle.escapy.designsystem.core.topAppBar.component.ProjectTopAppBarItem
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.ProjectScreenPreview

@Composable
fun LobbyScreen(
    navigateBack: () -> Unit,
) {
    ProjectScreenStructure(
        modifier = Modifier.fillMaxSize(),
        isPatternDisplayed = true,
        topContent = {
            ProjectTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.lobby_screen_title),
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
        LobbyScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
        )
    }
}

@Composable
private fun LobbyScreenContent(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = innerPadding.calculateTopPadding())
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
    ) {

    }
}

@ProjectScreenPreview
@Composable
private fun LobbyScreenPreview() {
    ProjectTheme {
        LobbyScreen(
            navigateBack = {},
        )
    }
}

@ProjectComponentPreview
@Composable
private fun LobbyScreenContentPreview() {
    ProjectTheme {
        LobbyScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = PaddingValues(),
        )
    }
}