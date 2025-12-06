package fr.skyle.escapy.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.LobbyStatus
import fr.skyle.escapy.designsystem.theme.ProjectTheme

val LobbyStatus.text: String
    @Composable
    get() = when (this) {
        LobbyStatus.NOT_STARTED ->
            stringResource(R.string.lobby_status_not_started)

        LobbyStatus.IN_PROGRESS ->
            stringResource(R.string.lobby_status_in_progress)

        LobbyStatus.FINISHED ->
            stringResource(R.string.lobby_status_finished)
    }

val LobbyStatus.color: Color
    @Composable
    get() = when (this) {
        LobbyStatus.NOT_STARTED ->
            ProjectTheme.colors.grey500

        LobbyStatus.IN_PROGRESS ->
            ProjectTheme.colors.primary

        LobbyStatus.FINISHED ->
            ProjectTheme.colors.success
    }

val LobbyStatus.textColor: Color
    @Composable
    get() = when (this) {
        LobbyStatus.NOT_STARTED ->
            ProjectTheme.colors.onSurface

        LobbyStatus.IN_PROGRESS ->
            ProjectTheme.colors.onPrimary

        LobbyStatus.FINISHED ->
            ProjectTheme.colors.onPrimary
    }