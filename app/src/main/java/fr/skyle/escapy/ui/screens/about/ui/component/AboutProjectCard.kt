package fr.skyle.escapy.ui.screens.about.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.BuildConfig
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButton
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.boxCardStyle
import fr.skyle.escapy.ext.navigateToProjectRepository
import fr.skyle.escapy.utils.ProjectComponentPreview

@Composable
fun AboutProjectCard(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .boxCardStyle()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(60.dp)
                .boxCardStyle(backgroundColor = ProjectTheme.colors.surfaceContainerLow)
                .padding(16.dp),
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null,
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.app_name),
                style = ProjectTheme.typography.b1,
                color = ProjectTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(
                    R.string.profile_version_format,
                    BuildConfig.VERSION_NAME,
                    BuildConfig.VERSION_CODE.toString()
                ),
                style = ProjectTheme.typography.p3,
                color = ProjectTheme.colors.onSurface
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        ProjectIconButton(
            icon = painterResource(R.drawable.ic_github),
            style = ProjectIconButtonDefaults.IconButtonStyle.FILLED,
            tint = ProjectIconButtonDefaults.IconButtonTint.NEUTRAL,
            onClick = context::navigateToProjectRepository
        )
    }
}

@ProjectComponentPreview
@Composable
private fun AboutProjectCardPreview() {
    ProjectTheme {
        AboutProjectCard()
    }
}