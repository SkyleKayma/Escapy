package fr.skyle.escapy.ui.core.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.data.enums.LobbyStatus
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.color
import fr.skyle.escapy.ext.text
import fr.skyle.escapy.ext.textColor

@Composable
fun ProjectLobbyStatusChip(
    status: LobbyStatus,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(ProjectTheme.shape.extraSmall)
            .background(status.color)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = status.text,
            color = status.textColor,
            style = ProjectTheme.typography.badge
        )
    }
}

private class ProjectLobbyStatusChipPreviewDataProvider :
    CollectionPreviewParameterProvider<ProjectLobbyStatusChipPreviewData>(
        collection = buildList {
            LobbyStatus.entries.forEach { lobbyStatus ->
                add(
                    ProjectLobbyStatusChipPreviewData(
                        lobbyStatus = lobbyStatus,
                    )
                )
            }
        }
    )

private data class ProjectLobbyStatusChipPreviewData(
    val lobbyStatus: LobbyStatus
)

@Preview
@Composable
private fun ProjectLobbyStatusChipPreview(
    @PreviewParameter(ProjectLobbyStatusChipPreviewDataProvider::class) data: ProjectLobbyStatusChipPreviewData
) {
    ProjectTheme {
        ProjectLobbyStatusChip(
            status = data.lobbyStatus,
        )
    }
}