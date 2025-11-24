package fr.skyle.escapy.ui.screens.about.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import fr.skyle.escapy.R
import fr.skyle.escapy.data.rest.response.GithubContributor
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
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(contributor.avatarUrl)
                .crossfade(true)
                .build(),
            error = defaultPainter,
        )

        Image(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(ProjectTheme.colors.surfaceContainerLow),
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.width(24.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = contributor.login ?: "-",
            style = ProjectTheme.typography.p3,
            color = ProjectTheme.colors.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        val repoUrl = contributor.personalRepoUrl

        if (!repoUrl.isNullOrBlank()) {
            Spacer(modifier = Modifier.width(24.dp))

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
                login = "SkyleKayma"
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
                login = "SkyleKayma",
                personalRepoUrl = "https://github.com"
            ),
            defaultPainter = painterResource(R.drawable.avatar_default)
        )
    }
}