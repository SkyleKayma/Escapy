package fr.skyle.escapy.ui.screens.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.LobbyStatus
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.boxCardStyle
import fr.skyle.escapy.ext.formatDuration
import fr.skyle.escapy.ui.core.chip.ProjectLobbyStatusChip
import fr.skyle.escapy.utils.AnnotatedData
import fr.skyle.escapy.utils.DateFormat
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.buildAnnotatedString
import fr.skyle.escapy.utils.format
import kotlin.time.Duration.Companion.hours

@Composable
fun HomeActiveLobbyCell(
    title: String,
    status: LobbyStatus,
    duration: Long,
    createdAt: Long,
    createdBy: String,
    nbParticipants: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .boxCardStyle()
            .clickable(onClick = onClick),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    style = ProjectTheme.typography.b1,
                    color = ProjectTheme.colors.onSurface
                )

                Spacer(modifier = Modifier.width(12.dp))

                ProjectLobbyStatusChip(
                    modifier = Modifier.wrapContentWidth(),
                    status = status,
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildAnnotatedString(
                    fullText = stringResource(
                        R.string.home_active_lobby_cell_managed_by_format,
                        createdBy
                    ),
                    AnnotatedData(
                        text = createdBy,
                        spanStyle = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = ProjectTheme.colors.primary,
                        )
                    )
                ),
                style = ProjectTheme.typography.p3,
                color = ProjectTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(2.dp))

            val createdAtFormatted = createdAt.format(DateFormat.FULL_DATE) ?: ""
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildAnnotatedString(
                    fullText = stringResource(
                        R.string.home_active_lobby_cell_at_format,
                        createdAtFormatted
                    ),
                    AnnotatedData(
                        text = createdAtFormatted,
                        spanStyle = SpanStyle(
                            fontWeight = FontWeight.Bold,
                        )
                    )
                ),
                style = ProjectTheme.typography.p3,
                color = ProjectTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(2.dp))

            val durationFormatted = formatDuration(duration)
            Text(
                modifier = Modifier.weight(1f),
                text = buildAnnotatedString(
                    fullText = stringResource(
                        R.string.home_active_lobby_cell_duration_format,
                        durationFormatted
                    ),
                    AnnotatedData(
                        text = durationFormatted,
                        spanStyle = SpanStyle(
                            fontWeight = FontWeight.Bold,
                        )
                    )
                ),
                style = ProjectTheme.typography.p3,
                color = ProjectTheme.colors.onSurface
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ProjectTheme.colors.primary)
                .padding(horizontal = 6.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "$nbParticipants participants",
                style = ProjectTheme.typography.b2,
                color = ProjectTheme.colors.onPrimary,
                textAlign = TextAlign.End
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                modifier = Modifier.size(24.dp),
                painter = rememberVectorPainter(Icons.Default.DoubleArrow),
                contentDescription = null,
                tint = ProjectTheme.colors.onPrimary
            )
        }
    }
}

@ProjectComponentPreview
@Composable
private fun HomeActiveLobbyCellPreview() {
    ProjectTheme {
        HomeActiveLobbyCell(
            title = "Escap'Room",
            status = LobbyStatus.NOT_STARTED,
            duration = 1.hours.inWholeMilliseconds,
            createdAt = System.currentTimeMillis(),
            createdBy = "Player_TlBaSwl2o1",
            nbParticipants = 6,
            onClick = {},
        )
    }
}