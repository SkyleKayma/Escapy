package fr.skyle.escapy.ui.screens.bottomsheets.editAvatar.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.designsystem.ext.values
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.boxCardStyle
import fr.skyle.escapy.ext.iconId

@Composable
fun EditAvatarItem(
    avatar: Avatar,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    val context = LocalContext.current

    AsyncImage(
        modifier = modifier
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = ProjectTheme.colors.primary,
                        shape = CircleShape
                    )
                } else {
                    Modifier
                }
            )
            .boxCardStyle(shape = CircleShape)
            .clickable(enabled = isEnabled, onClick = onClick),
        model = ImageRequest.Builder(context)
            .data(avatar.iconId)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        error = painterResource(R.drawable.avatar_default)
    )
}

private class EditAvatarItemPreviewDataProvider :
    CollectionPreviewParameterProvider<EditAvatarItemPreviewData>(
        collection = buildList {
            Boolean.values.forEach { isSelected ->
                Boolean.values.forEach { isEnabled ->
                    add(
                        EditAvatarItemPreviewData(
                            isSelected = isSelected,
                            isEnabled = isEnabled,
                        )
                    )
                }
            }
        }
    )

private data class EditAvatarItemPreviewData(
    val isSelected: Boolean,
    val isEnabled: Boolean,
)

@Preview
@Composable
private fun EditAvatarItemPreview(
    @PreviewParameter(EditAvatarItemPreviewDataProvider::class) data: EditAvatarItemPreviewData
) {
    ProjectTheme {
        EditAvatarItem(
            modifier = Modifier.size(100.dp),
            avatar = Avatar.AVATAR_01,
            isSelected = data.isSelected,
            isEnabled = data.isEnabled,
            onClick = {}
        )
    }
}