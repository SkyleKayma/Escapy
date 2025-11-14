package fr.skyle.escapy.ui.screens.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
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
import fr.skyle.escapy.ext.getTextWithHighlightedColorPart
import fr.skyle.escapy.ui.core.screen.ProjectBackgroundLogoScreen
import fr.skyle.escapy.ui.screens.home.ui.component.HomeActionCell

@Composable
fun HomeScreen() {
    ProjectBackgroundLogoScreen {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                ProjectTopAppBar(
                    trailingContent = {
                        ProjectIconButton(
                            icon = rememberVectorPainter(Icons.Default.Person2),
                            style = ProjectIconButtonDefaults.ProjectIconButtonStyle.OUTLINED,
                            tint = ProjectIconButtonDefaults.ProjectIconButtonTint.NEUTRAL,
                            onClick = {
                                // TODO Go to Profile
                            }
                        )
                    }
                )

                HomeScreenContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
                )
            }

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 24.dp, bottom = 24.dp),
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
    }
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val welcomeTitle = stringResource(R.string.home_welcome_format, "John Doe")
        val welcomeTitleAnnotated = getTextWithHighlightedColorPart(
            fullText = welcomeTitle,
            highlightedText = "John Doe",
            highlightedColor = ProjectTheme.colors.primary
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = welcomeTitleAnnotated,
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
            style = ProjectButtonDefaults.ProjectButtonStyle.FILLED,
            tint = ProjectButtonDefaults.ProjectButtonTint.PRIMARY,
            size = ProjectButtonDefaults.ProjectButtonSize.LARGE,
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
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ProjectTheme {
        HomeScreen()
    }
}

@Preview
@Composable
private fun HomeScreenContentPreview() {
    ProjectTheme {
        HomeScreenContent()
    }
}