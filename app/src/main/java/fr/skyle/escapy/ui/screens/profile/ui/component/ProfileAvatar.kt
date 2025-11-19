package fr.skyle.escapy.ui.screens.profile.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.icon

@Composable
fun ProfileAvatar(
    avatar: Avatar?,
    modifier: Modifier = Modifier,
) {
    val avatarPainter =
        avatar?.icon ?: rememberVectorPainter(Icons.Default.Person)

    Image(
        modifier = modifier
            .shadow(elevation = 4.dp, shape = CircleShape)
            .clip(CircleShape)
            .background(ProjectTheme.colors.surfaceContainerHigh)
            .padding(20.dp),
        painter = avatarPainter,
        contentDescription = null,
        contentScale = ContentScale.Crop
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