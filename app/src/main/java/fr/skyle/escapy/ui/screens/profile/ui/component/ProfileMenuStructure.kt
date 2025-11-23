package fr.skyle.escapy.ui.screens.profile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.displayText
import fr.skyle.escapy.utils.AnnotatedData
import fr.skyle.escapy.utils.buildAnnotatedString

class ProfileMenuBuilder {
    internal val groups = mutableListOf<Group>()

    fun group(content: Group.() -> Unit) {
        groups += Group().apply(content)
    }

    class Group {
        internal val items = mutableListOf<@Composable () -> Unit>()

        fun item(content: @Composable () -> Unit) {
            items += content
        }
    }
}

@Composable
fun ProfileMenuStructure(
    title: String,
    modifier: Modifier = Modifier,
    content: ProfileMenuBuilder.() -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = ProjectTheme.typography.b1,
            color = ProjectTheme.colors.onSurface,
        )

        Spacer(modifier = Modifier.height(8.dp))

        val builder = remember { ProfileMenuBuilder() }
        builder.groups.clear()                      // ensure rebuild safety
        builder.content()                           // ⬅️ execute your DSL

        builder.groups.forEachIndexed { indexGroup, group ->
            ProfileMenuStructureGroup(
                modifier = Modifier.fillMaxWidth()
            ) {
                group.items.forEachIndexed { indexItem, item ->
                    item()

                    if (indexItem != group.items.lastIndex) {
                        HorizontalDivider(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            color = ProjectTheme.colors.grey500
                        )
                    }
                }
            }

            if (indexGroup != builder.groups.lastIndex) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun ProfileMenuStructureGroup(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(ProjectTheme.colors.surfaceContainerHigh)
    ) {
        content()
    }
}

@Composable
fun ProfileMenuBuilder.Group.ProfileMenuStructureItem(
    title: AnnotatedString,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onCellClicked: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .clickable(
                enabled = onCellClicked != null
            ) {
                onCellClicked?.invoke()
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = ProjectTheme.typography.p2,
                color = ProjectTheme.colors.onSurface
            )

            subtitle?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = subtitle,
                    style = ProjectTheme.typography.p3,
                    color = ProjectTheme.colors.grey500
                )
            }
        }

        onCellClicked?.let {
            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                modifier = Modifier.size(20.dp),
                painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowRight),
                contentDescription = null,
                tint = ProjectTheme.colors.onSurface
            )
        }
    }
}

@Preview
@Composable
private fun ProfileMenuStructureEmptyPreview() {
    ProjectTheme {
        ProfileMenuStructure(
            title = "Compte"
        ) {}
    }
}

@Preview
@Composable
private fun ProfileMenuStructureOneEntryPreview() {
    ProjectTheme {
        ProfileMenuStructure(
            title = "Compte"
        ) {
            group {
                item {
                    ProfileMenuStructureItem(
                        title = AnnotatedString(stringResource(R.string.profile_edit_profile))
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfileMenuStructurePreview() {
    ProjectTheme {
        ProfileMenuStructure(
            title = "Compte"
        ) {
            group {
                item {
                    ProfileMenuStructureItem(
                        title = AnnotatedString(stringResource(R.string.profile_edit_profile))
                    )
                }

                item {
                    ProfileMenuStructureItem(
                        title = AnnotatedString(stringResource(R.string.profile_change_password))
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfileMenuStructureItemPreview() {
    ProjectTheme {
        ProfileMenuBuilder.Group().apply {
            ProfileMenuStructureItem(
                title = AnnotatedString(stringResource(R.string.profile_change_password)),
                onCellClicked = {}
            )
        }
    }
}

@Preview
@Composable
private fun ProfileMenuStructureItemNoActionPreview() {
    ProjectTheme {
        ProfileMenuBuilder.Group().apply {
            ProfileMenuStructureItem(
                title = AnnotatedString(stringResource(R.string.profile_change_password))
            )
        }
    }
}

@Preview
@Composable
private fun ProfileMenuStructureItemWithSubtitleAndActionPreview() {
    ProjectTheme {
        ProfileMenuBuilder.Group().apply {
            ProfileMenuStructureItem(
                title = buildAnnotatedString(
                    fullText = stringResource(
                        R.string.profile_auth_provider,
                        AuthProvider.ANONYMOUS.displayText
                    ),
                    AnnotatedData(
                        text = AuthProvider.ANONYMOUS.displayText,
                        spanStyle = SpanStyle(
                            color = ProjectTheme.colors.warning,
                        )
                    )
                ),
                subtitle = stringResource(R.string.profile_warning_guest),
                onCellClicked = {}
            )
        }
    }
}