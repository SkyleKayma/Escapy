package fr.skyle.escapy.ui.screens.about.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import fr.skyle.escapy.R
import fr.skyle.escapy.data.vo.GithubContributor
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButton
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.boxCardStyle
import fr.skyle.escapy.ext.navigateToLink

@Composable
fun AboutContributorCell(
    contributor: GithubContributor,
    defaultPainter: Painter,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .boxCardStyle()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(40.dp)
                .boxCardStyle(
                    elevation = 0.dp,
                    shape = CircleShape,
                    backgroundColor = ProjectTheme.colors.surfaceContainerLow
                ),
            model = ImageRequest.Builder(context)
                .data(contributor.avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            error = defaultPainter
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = contributor.username,
            style = ProjectTheme.typography.p3,
            color = ProjectTheme.colors.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        val repoUrl = contributor.personalRepoUrl

        if (!repoUrl.isNullOrBlank()) {
            Spacer(modifier = Modifier.width(12.dp))

            ProjectIconButton(
                icon = painterResource(R.drawable.ic_github),
                style = ProjectIconButtonDefaults.IconButtonStyle.FILLED,
                tint = ProjectIconButtonDefaults.IconButtonTint.NEUTRAL,
                onClick = {
                    context.navigateToLink(repoUrl)
                }
            )
        }
    }
}

@Preview
@Composable
private fun AboutContributorCellPreview() {
    ProjectTheme {
        AboutContributorCell(
            contributor = GithubContributor(
                id = 1,
                username = "SkyleKayma"
            ),
            defaultPainter = painterResource(R.drawable.avatar_default)
        )
    }
}

@Preview
@Composable
private fun AboutContributorCellWithGithubUrlPreview() {
    ProjectTheme {
        AboutContributorCell(
            contributor = GithubContributor(
                id = 1,
                username = "SkyleKayma",
                personalRepoUrl = "https://github.com"
            ),
            defaultPainter = painterResource(R.drawable.avatar_default)
        )
    }
}