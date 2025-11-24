package fr.skyle.escapy.ui.core.structure

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.ext.values
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.snackbar.ProjectSnackbar
import fr.skyle.escapy.ui.core.snackbar.state.ProjectSnackbarState
import fr.skyle.escapy.utils.ProjectScreenPreview

@Composable
fun ProjectScreenStructure(
    modifier: Modifier = Modifier,
    isPatternDisplayed: Boolean = false,
    snackbarState: ProjectSnackbarState? = null,
    topContent: (@Composable () -> Unit)? = null,
    bottomContent: (@Composable () -> Unit)? = null,
    floatingActionButton: (@Composable () -> Unit)? = null,
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        containerColor = ProjectTheme.colors.surfaceContainerLow,
        topBar = {
            topContent?.invoke()
        },
        bottomBar = {
            bottomContent?.invoke()
        },
        floatingActionButton = {
            floatingActionButton?.invoke()
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        snackbarHost = {
            snackbarState?.let {
                ProjectSnackbar(
                    state = snackbarState
                )
            }
        },
        content = { paddingValues ->
            if (isPatternDisplayed) {
                ProjectScreenStructurePattern()
            }

            content(paddingValues)
        },
    )
}

@Composable
private fun ProjectScreenStructurePattern() {
    Image(
        modifier = Modifier.width(375.dp),
        painter = painterResource(id = R.drawable.background_image),
        contentDescription = null,
        contentScale = ContentScale.FillWidth
    )
}

private class ProjectScreenStructurePreviewDataProvider :
    CollectionPreviewParameterProvider<ProjectScreenStructurePreviewData>(
        collection = buildList {
            Boolean.values.forEach { isPatternDisplayed ->
                add(
                    ProjectScreenStructurePreviewData(
                        isPatternDisplayed = isPatternDisplayed,
                    )
                )
            }
        }
    )

private data class ProjectScreenStructurePreviewData(
    val isPatternDisplayed: Boolean
)

@ProjectScreenPreview
@Composable
private fun ProjectScreenStructurePreview(
    @PreviewParameter(ProjectScreenStructurePreviewDataProvider::class) data: ProjectScreenStructurePreviewData
) {
    ProjectTheme {
        ProjectScreenStructure(
            isPatternDisplayed = data.isPatternDisplayed,
            content = {}
        )
    }
}

@Preview
@Composable
private fun ProjectScreenStructurePatternPreview() {
    ProjectTheme {
        ProjectScreenStructurePattern()
    }
}