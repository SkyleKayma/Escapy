package fr.skyle.escapy.ui.screens.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.button.ProjectButton
import fr.skyle.escapy.designsystem.core.button.ProjectButtonDefaults
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButton
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.core.topAppBar.ProjectTopAppBar
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.ui.screens.home.ui.component.HomeActionCell
import fr.skyle.escapy.utils.AnnotatedData
import fr.skyle.escapy.utils.ProjectScreenPreview
import fr.skyle.escapy.utils.buildAnnotatedString

@Composable
fun HomeScreen(
    homeState: HomeViewModel.HomeState,
    onProfileClicked: () -> Unit
) {
    ProjectScreenStructure(
        modifier = Modifier.fillMaxSize(),
        isPatternDisplayed = true,
        topContent = {
            ProjectTopAppBar(
                trailingContent = {
                    ProjectIconButton(
                        icon = rememberVectorPainter(Icons.Default.Person2),
                        style = ProjectIconButtonDefaults.IconButtonStyle.OUTLINED,
                        tint = ProjectIconButtonDefaults.IconButtonTint.NEUTRAL,
                        onClick = onProfileClicked
                    )
                }
            )
        },
        floatingActionButton = {
            // TODO Add to DesignSystem
            FloatingActionButton(
                modifier = Modifier.size(56.dp),
                containerColor = ProjectTheme.colors.primary,
                onClick = {
                    // TODO
                }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = rememberVectorPainter(Icons.Default.Add),
                    contentDescription = null,
                    tint = ProjectTheme.colors.onPrimary
                )
            }
        }
    ) { innerPadding ->
        HomeScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            userName = homeState.userName,
        )
    }
}

@Composable
private fun HomeScreenContent(
    innerPadding: PaddingValues,
    userName: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = innerPadding.calculateTopPadding())
            .verticalScroll(rememberScrollState())
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
    ) {
        val welcomeTitle = userName?.let {
            buildAnnotatedString(
                fullText = stringResource(R.string.home_welcome_format, userName),
                AnnotatedData(
                    text = userName,
                    spanStyle = SpanStyle(
                        color = ProjectTheme.colors.primary
                    )
                )
            )
        } ?: run {
            AnnotatedString(stringResource(R.string.home_welcome))
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = welcomeTitle,
            style = ProjectTheme.typography.h1,
            color = ProjectTheme.colors.onSurface
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.home_game_in_progress),
            style = ProjectTheme.typography.b1,
            color = ProjectTheme.colors.onSurface
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.home_no_game_in_progress),
            style = ProjectTheme.typography.p2,
            color = ProjectTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.home_join_a_room),
            style = ProjectTheme.typography.b1,
            color = ProjectTheme.colors.onSurface
        )

        Spacer(modifier = Modifier.height(24.dp))

        HomeActionCell(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.home_join_a_room_via_id),
            icon = rememberVectorPainter(Icons.Default.TextFields),
            onClick = {
                // TODO
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "OU",
            style = ProjectTheme.typography.b2,
            color = ProjectTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        HomeActionCell(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.home_join_a_room_via_qrcode),
            icon = rememberVectorPainter(Icons.Default.QrCode),
            onClick = {
                // TODO
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProjectButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.home_history),
            style = ProjectButtonDefaults.ButtonStyle.FILLED,
            tint = ProjectButtonDefaults.ButtonTint.PRIMARY,
            size = ProjectButtonDefaults.ButtonSize.LARGE,
            onClick = {
                // TODO
            },
            trailingContent = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowForward),
                    contentDescription = null
                )
            }
        )

        Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding()))
    }
}

@ProjectScreenPreview
@Composable
private fun HomeScreenPreview() {
    ProjectTheme {
        HomeScreen(
            homeState = HomeViewModel.HomeState(
                userName = "John Doe"
            ),
            onProfileClicked = {}
        )
    }
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    ProjectTheme {
        HomeScreenContent(
            innerPadding = PaddingValues(),
            userName = "John Doe",
            modifier = Modifier.fillMaxSize(),
        )
    }
}