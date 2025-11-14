package fr.skyle.escapy.designsystem.core.topAppBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.core.topAppBar.component.ProjectTopAppBarItem
import fr.skyle.escapy.designsystem.theme.ProjectTheme

@Composable
fun ProjectTopAppBar(
    modifier: Modifier = Modifier,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    title: String? = null,
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
    ) {
        ProjectTopAppBarContent(
            modifier = Modifier.fillMaxWidth(),
            title = title,
            leadingContent = leadingContent,
            trailingContent = trailingContent,
        )
    }
}

@Composable
private fun ProjectTopAppBarContent(
    modifier: Modifier = Modifier,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    title: String? = null,
) {
    var animatedTitle by remember {
        mutableStateOf(title ?: "")
    }

    LaunchedEffect(title) {
        animatedTitle = title ?: animatedTitle
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingValues = ProjectTopAppBarDefaults.topAppBarPaddingValues)
            .height(ProjectTopAppBarDefaults.TOP_APP_BAR_HEIGHT_DP.dp),
        horizontalArrangement = Arrangement.spacedBy(ProjectTopAppBarDefaults.TOP_APP_BAR_MIN_SPACING_DP.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingContent?.let {
            ProjectTopAppBarActionRow(
                modifier = Modifier.wrapContentWidth(),
                content = leadingContent
            )
        }

        Box(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier,
                text = animatedTitle,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = ProjectTheme.typography.h1,
                color = ProjectTheme.colors.primary
            )
        }

        trailingContent?.let {
            ProjectTopAppBarActionRow(
                modifier = Modifier.wrapContentWidth(),
                content = trailingContent
            )
        }
    }
}

@Composable
private fun ProjectTopAppBarActionRow(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = ProjectTopAppBarDefaults.TOP_APP_BAR_ACTION_SPACING_DP.dp,
        ),
    ) {
        content()
    }
}

@Preview
@Composable
private fun ProjectTopAppBarPreview() {
    ProjectTheme {
        ProjectTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            title = "This is a very long title that will end up on two lines",
            leadingContent = {
                ProjectTopAppBarItem(
                    icon = rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowBack),
                    style = ProjectIconButtonDefaults.ProjectIconButtonStyle.FILLED,
                    onClick = {},
                )

                ProjectTopAppBarItem(
                    icon = rememberVectorPainter(Icons.Default.Settings),
                    style = ProjectIconButtonDefaults.ProjectIconButtonStyle.FILLED,
                    onClick = {},
                )
            },
            trailingContent = {
                ProjectTopAppBarItem(
                    icon = rememberVectorPainter(Icons.Default.Settings),
                    style = ProjectIconButtonDefaults.ProjectIconButtonStyle.FILLED,
                    onClick = {},
                )

                ProjectTopAppBarItem(
                    icon = rememberVectorPainter(Icons.Default.Settings),
                    style = ProjectIconButtonDefaults.ProjectIconButtonStyle.FILLED,
                    onClick = {},
                )
            }
        )
    }
}