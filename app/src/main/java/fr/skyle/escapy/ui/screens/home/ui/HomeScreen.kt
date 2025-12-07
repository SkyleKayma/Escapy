package fr.skyle.escapy.ui.screens.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.LobbyStatus
import fr.skyle.escapy.designsystem.core.button.ProjectButton
import fr.skyle.escapy.designsystem.core.button.ProjectButtonDefaults
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButton
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.core.snackbar.state.ProjectSnackbarState
import fr.skyle.escapy.designsystem.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.designsystem.core.topAppBar.ProjectTopAppBar
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.ui.screens.home.ui.component.HomeActionCell
import fr.skyle.escapy.ui.screens.home.ui.component.HomeActiveLobbyCell
import fr.skyle.escapy.ui.screens.home.ui.model.LobbyUI
import fr.skyle.escapy.utils.AnnotatedData
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.ProjectScreenPreview
import fr.skyle.escapy.utils.buildAnnotatedString
import kotlin.time.Duration.Companion.hours

@Composable
fun HomeScreen(
    snackbarState: ProjectSnackbarState,
    state: HomeViewModel.State,
    onProfileClicked: () -> Unit,
    onCreateLobby: () -> Unit,
    onRefresh: () -> Unit,
    onHomeActiveLobbyClicked: (lobbyId: String) -> Unit,
) {
    ProjectScreenStructure(
        modifier = Modifier.fillMaxSize(),
        isPatternDisplayed = true,
        snackbarState = snackbarState,
        topContent = {
            ProjectTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                trailingContent = {
                    ProjectIconButton(
                        icon = rememberVectorPainter(Icons.Default.Person2),
                        style = ProjectIconButtonDefaults.Style.OUTLINED,
                        tint = ProjectIconButtonDefaults.Tint.NEUTRAL,
                        size = ProjectIconButtonDefaults.Size.LARGE,
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
                onClick = onCreateLobby
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
            username = state.username,
            isRefreshing = state.isRefreshing,
            activeLobbies = state.activeLobbies,
            onRefresh = onRefresh,
            onHomeActiveLobbyClicked = onHomeActiveLobbyClicked,
        )
    }
}

@Composable
private fun HomeScreenContent(
    innerPadding: PaddingValues,
    username: String?,
    isRefreshing: Boolean,
    activeLobbies: List<LobbyUI>,
    onRefresh: () -> Unit,
    onHomeActiveLobbyClicked: (lobbyId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    PullToRefreshBox(
        modifier = modifier.padding(top = innerPadding.calculateTopPadding()),
        isRefreshing = isRefreshing,
        state = rememberPullToRefreshState(),
        onRefresh = onRefresh,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
        ) {
            val welcomeTitle = username?.let {
                buildAnnotatedString(
                    fullText = stringResource(R.string.home_welcome_format, username),
                    AnnotatedData(
                        text = username,
                        spanStyle = SpanStyle(
                            color = ProjectTheme.colors.primary
                        )
                    )
                )
            } ?: AnnotatedString(stringResource(R.string.home_welcome))

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
            if (activeLobbies.isEmpty()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.home_no_game_in_progress),
                    style = ProjectTheme.typography.p2,
                    color = ProjectTheme.colors.onSurface,
                    textAlign = TextAlign.Center
                )
            } else {
                Column(modifier = Modifier.fillMaxWidth()) {
                    activeLobbies.forEachIndexed { index, activeLobby ->
                        HomeActiveLobbyCell(
                            modifier = Modifier.fillMaxWidth(),
                            title = activeLobby.title,
                            status = activeLobby.status,
                            duration = activeLobby.duration,
                            createdAt = activeLobby.createdAt,
                            createdByName = activeLobby.createdByName,
                            nbParticipants = activeLobby.nbParticipants,
                            onClick = {
                                onHomeActiveLobbyClicked(activeLobby.uid)
                            }
                        )

                        if (index != activeLobbies.lastIndex) {
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }

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
                style = ProjectButtonDefaults.Style.FILLED,
                tint = ProjectButtonDefaults.Tint.PRIMARY,
                size = ProjectButtonDefaults.Size.LARGE,
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
}

@ProjectScreenPreview
@Composable
private fun HomeScreenPreview() {
    ProjectTheme {
        HomeScreen(
            snackbarState = rememberProjectSnackbarState(),
            state = HomeViewModel.State(
                username = "John Doe"
            ),
            onProfileClicked = {},
            onCreateLobby = {},
            onRefresh = {},
            onHomeActiveLobbyClicked = {},
        )
    }
}

@ProjectComponentPreview
@Composable
private fun HomeScreenContentPreview() {
    ProjectTheme {
        HomeScreenContent(
            innerPadding = PaddingValues(),
            username = "John Doe",
            isRefreshing = false,
            activeLobbies = emptyList(),
            onRefresh = {},
            onHomeActiveLobbyClicked = {}
        )
    }
}

@ProjectComponentPreview
@Composable
private fun HomeScreenContentWithLobbiesPreview() {
    ProjectTheme {
        HomeScreenContent(
            innerPadding = PaddingValues(),
            username = "John Doe",
            isRefreshing = false,
            activeLobbies = listOf(
                LobbyUI(
                    uid = "1",
                    title = "Lobby 1",
                    status = LobbyStatus.NOT_STARTED,
                    duration = 1.hours.inWholeMilliseconds,
                    createdAt = System.currentTimeMillis(),
                    createdByName = "John Doe",
                    nbParticipants = 2
                ),
                LobbyUI(
                    uid = "2",
                    title = "Lobby 2",
                    status = LobbyStatus.IN_PROGRESS,
                    duration = 1.hours.inWholeMilliseconds,
                    createdAt = System.currentTimeMillis(),
                    createdByName = "John Doe",
                    nbParticipants = 4
                )
            ),
            onRefresh = {},
            onHomeActiveLobbyClicked = {}
        )
    }
}