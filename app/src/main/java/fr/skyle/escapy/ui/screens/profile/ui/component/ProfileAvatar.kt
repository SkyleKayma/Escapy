package fr.skyle.escapy.ui.screens.profile.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.boxCardStyle
import fr.skyle.escapy.ext.icon

@Composable
fun ProfileAvatar(
    avatar: Avatar?,
    modifier: Modifier = Modifier,
) {
    val avatarPainter = avatar?.icon
    val fallbackPainter = rememberVectorPainter(Icons.Default.Person)

    val painter = avatarPainter ?: fallbackPainter
    val isFallback = avatarPainter == null

    Image(
        modifier = modifier
            .boxCardStyle(shape = CircleShape)
            .padding(20.dp),
        painter = painter,
        contentDescription = null,
        contentScale = if (isFallback) {
            ContentScale.Fit
        } else {
            ContentScale.Crop
        },
        colorFilter = if (isFallback) {
            ColorFilter.tint(ProjectTheme.colors.black)
        } else {
            null
        },
    )
}

@Preview
@Composable
private fun ProfileAvatarPreview() {
    ProjectTheme {
        ProfileAvatar(
            modifier = Modifier.size(100.dp),
            avatar = null
        )
    }
}

@Preview
@Composable
private fun ProfileAvatarWithAvatarPreview() {
    ProjectTheme {
        ProfileAvatar(
            modifier = Modifier.size(100.dp),
            avatar = Avatar.AVATAR_01
        )
    }
}