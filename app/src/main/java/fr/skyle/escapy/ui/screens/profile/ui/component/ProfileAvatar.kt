package fr.skyle.escapy.ui.screens.profile.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.boxCardStyle
import fr.skyle.escapy.ext.iconId
import fr.skyle.escapy.utils.ProjectComponentPreview

@Composable
fun ProfileAvatar(
    avatar: Avatar?,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    AsyncImage(
        modifier = modifier.boxCardStyle(shape = CircleShape),
        model = ImageRequest.Builder(context)
            .data(avatar?.iconId)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        error = painterResource(R.drawable.avatar_default)
    )
}

@ProjectComponentPreview
@Composable
private fun ProfileAvatarPreview() {
    ProjectTheme {
        ProfileAvatar(
            modifier = Modifier.size(100.dp),
            avatar = null
        )
    }
}

@ProjectComponentPreview
@Composable
private fun ProfileAvatarWithAvatarPreview() {
    ProjectTheme {
        ProfileAvatar(
            modifier = Modifier.size(100.dp),
            avatar = Avatar.AVATAR_01
        )
    }
}