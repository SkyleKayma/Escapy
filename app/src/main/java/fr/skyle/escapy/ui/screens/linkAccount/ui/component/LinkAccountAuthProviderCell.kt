package fr.skyle.escapy.ui.screens.linkAccount.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.boxCardStyle
import fr.skyle.escapy.ext.displayTextLong
import fr.skyle.escapy.ext.icon

@Composable
fun LinkAccountAuthProviderCell(
    authProvider: AuthProvider,
    onCellClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .boxCardStyle()
            .clickable(onClick = onCellClicked)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(40.dp)
                .boxCardStyle(
                    elevation = 0.dp,
                    shape = CircleShape,
                    backgroundColor = ProjectTheme.colors.surfaceContainerLow
                )
                .padding(8.dp),
            painter = authProvider.icon,
            contentDescription = null,
            tint = ProjectTheme.colors.onSurface
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = authProvider.displayTextLong,
            style = ProjectTheme.typography.p3,
            color = ProjectTheme.colors.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        Icon(
            modifier = Modifier
                .size(24.dp)
                .boxCardStyle(
                    elevation = 0.dp,
                    shape = CircleShape,
                    backgroundColor = ProjectTheme.colors.surfaceContainerLow
                ),
            painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowRight),
            contentDescription = null,
            tint = ProjectTheme.colors.onSurface
        )
    }
}

private class LinkAccountAuthProviderCellPreviewDataProvider :
    CollectionPreviewParameterProvider<LinkAccountAuthProviderCellPreviewData>(
        collection = buildList {
            AuthProvider.entries.forEach { authProvider ->
                add(
                    LinkAccountAuthProviderCellPreviewData(
                        authProvider = authProvider,
                    )
                )
            }
        }
    )

private data class LinkAccountAuthProviderCellPreviewData(
    val authProvider: AuthProvider
)

@Preview
@Composable
private fun LinkAccountAuthProviderCellPreview(
    @PreviewParameter(LinkAccountAuthProviderCellPreviewDataProvider::class) data: LinkAccountAuthProviderCellPreviewData
) {
    ProjectTheme {
        LinkAccountAuthProviderCell(
            authProvider = data.authProvider,
            onCellClicked = {}
        )
    }
}