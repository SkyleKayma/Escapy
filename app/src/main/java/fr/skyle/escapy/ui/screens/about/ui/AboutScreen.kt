package fr.skyle.escapy.ui.screens.about.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.core.topAppBar.ProjectTopAppBar
import fr.skyle.escapy.designsystem.core.topAppBar.component.ProjectTopAppBarItem
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.ui.screens.about.ui.component.AboutContributorCell
import fr.skyle.escapy.ui.screens.about.ui.component.AboutProjectCard
import fr.skyle.escapy.ui.screens.about.ui.model.GithubContributorUI
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.ProjectScreenPreview

private const val KEY_VIEW_TYPE_PROJECT_CARD = "ProjectCard"
private const val KEY_VIEW_TYPE_CONTRIBUTORS_LOADER = "ContributorsLoader"
private const val KEY_VIEW_TYPE_CONTRIBUTORS_TITLE = "ContributorsTitle"

@Composable
fun AboutScreen(
    state: AboutViewModel.State,
    navigateBack: () -> Unit,
) {
    ProjectScreenStructure(
        modifier = Modifier.fillMaxSize(),
        isPatternDisplayed = true,
        topContent = {
            ProjectTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.about_screen_title),
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
        AboutScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            isContributorsLoading = state.isContributorsLoading,
            contributors = state.contributors
        )
    }
}

@Composable
private fun AboutScreenContent(
    innerPadding: PaddingValues,
    isContributorsLoading: Boolean,
    contributors: List<GithubContributorUI>,
    modifier: Modifier = Modifier,
) {
    val defaultContributorPainter = painterResource(R.drawable.avatar_default)

    LazyColumn(
        modifier = modifier
            .padding(top = innerPadding.calculateTopPadding()),
        contentPadding = PaddingValues(
            start = 24.dp,
            top = 24.dp,
            end = 24.dp,
            bottom = 24.dp + innerPadding.calculateBottomPadding()
        ),
    ) {
        item(key = KEY_VIEW_TYPE_PROJECT_CARD) {
            AboutProjectCard(
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        if (isContributorsLoading) {
            item(key = KEY_VIEW_TYPE_CONTRIBUTORS_LOADER) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // TODO Add to DesignSystem
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center),
                        color = ProjectTheme.colors.primary,
                        trackColor = ProjectTheme.colors.surfaceContainerHigh,
                        strokeWidth = 3.dp
                    )
                }
            }
        }

        if (contributors.isNotEmpty() && !isContributorsLoading) {
            item(key = KEY_VIEW_TYPE_CONTRIBUTORS_TITLE) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.about_contributors),
                    style = ProjectTheme.typography.b1,
                    color = ProjectTheme.colors.onSurface,
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            itemsIndexed(
                items = contributors,
                key = { _, contributor -> contributor.id }
            ) { index, contributor ->
                AboutContributorCell(
                    modifier = Modifier.fillMaxWidth(),
                    username = contributor.username,
                    avatarUrl = contributor.avatarUrl,
                    personalRepoUrl = contributor.personalRepoUrl,
                    defaultPainter = defaultContributorPainter
                )

                if (index != contributors.lastIndex) {
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@ProjectScreenPreview
@Composable
private fun AboutScreenPreview() {
    ProjectTheme {
        AboutScreen(
            state = AboutViewModel.State(),
            navigateBack = {},
        )
    }
}

@ProjectComponentPreview
@Composable
private fun AboutScreenContentPreview() {
    ProjectTheme {
        AboutScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = PaddingValues(),
            isContributorsLoading = false,
            contributors = listOf(
                GithubContributorUI(
                    id = 1,
                    username = "SkyleKayma",
                ),
                GithubContributorUI(
                    id = 2,
                    username = "SkyleKayma",
                    personalRepoUrl = ""
                )
            ),
        )
    }
}

@ProjectComponentPreview
@Composable
private fun AboutScreenContentContributorsLoadingPreview() {
    ProjectTheme {
        AboutScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = PaddingValues(),
            isContributorsLoading = true,
            contributors = listOf(
                GithubContributorUI(
                    id = 1,
                    username = "SkyleKayma",
                    personalRepoUrl = "https://github.com"
                )
            ),
        )
    }
}