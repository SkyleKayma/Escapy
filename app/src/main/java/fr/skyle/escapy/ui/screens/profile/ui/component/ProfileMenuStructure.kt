package fr.skyle.escapy.ui.screens.profile.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.boxCardStyle
import fr.skyle.escapy.ext.displayTextShort
import fr.skyle.escapy.utils.AnnotatedData
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.buildAnnotatedString

class ProfileMenuScope {
    internal val groups = mutableListOf<Group>()

    fun group(content: Group.() -> Unit) {
        groups.add(Group().apply(content))
    }

    class Group {
        internal val items = mutableListOf<@Composable () -> Unit>()

        fun item(content: @Composable () -> Unit) {
            items.add(content)
        }
    }
}

@Composable
fun ProfileMenuStructure(
    title: String,
    modifier: Modifier = Modifier,
    content: ProfileMenuScope.() -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = ProjectTheme.typography.b1,
            color = ProjectTheme.colors.onSurface,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Recreate the scope only when content changes
        val builder = remember(content) { ProfileMenuScope() }
        builder.content()

        builder.groups.forEachIndexed { indexGroup, group ->
            ProfileMenuStructureGroup(
                modifier = Modifier.fillMaxWidth()
            ) {
                group.items.forEachIndexed { indexItem, item ->
                    item()

                    if (indexItem != group.items.lastIndex) {
                        HorizontalDivider(
                            Modifier.fillMaxWidth(),
                            color = ProjectTheme.colors.surfaceContainerLow
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
            .boxCardStyle()
    ) {
        content()
    }
}

@Composable
fun ProfileMenuScope.Group.ProfileMenuStructureItem(
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
            .padding(horizontal = 20.dp, vertical = 16.dp),
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
            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                modifier = Modifier.size(20.dp),
                painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowRight),
                contentDescription = null,
                tint = ProjectTheme.colors.onSurface
            )
        }
    }
}

@ProjectComponentPreview
@Composable
private fun ProfileMenuStructureEmptyPreview() {
    ProjectTheme {
        ProfileMenuStructure(
            title = stringResource(R.string.profile_account)
        ) {}
    }
}

@ProjectComponentPreview
@Composable
private fun ProfileMenuStructureOneEntryPreview() {
    ProjectTheme {
        ProfileMenuStructure(
            title = stringResource(R.string.profile_account)
        ) {
            group {
                item {
                    ProfileMenuStructureItem(
                        title = AnnotatedString(stringResource(R.string.profile_change_email))
                    )
                }
            }
        }
    }
}

@ProjectComponentPreview
@Composable
private fun ProfileMenuStructurePreview() {
    ProjectTheme {
        ProfileMenuStructure(
            title = stringResource(R.string.profile_account)
        ) {
            group {
                item {
                    ProfileMenuStructureItem(
                        title = AnnotatedString(stringResource(R.string.profile_change_email))
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

@ProjectComponentPreview
@Composable
private fun ProfileMenuStructureItemPreview() {
    ProjectTheme {
        ProfileMenuScope.Group().apply {
            ProfileMenuStructureItem(
                title = AnnotatedString(stringResource(R.string.profile_change_password)),
                onCellClicked = {}
            )
        }
    }
}

@ProjectComponentPreview
@Composable
private fun ProfileMenuStructureItemNoActionPreview() {
    ProjectTheme {
        ProfileMenuScope.Group().apply {
            ProfileMenuStructureItem(
                title = AnnotatedString(stringResource(R.string.profile_change_password))
            )
        }
    }
}

@ProjectComponentPreview
@Composable
private fun ProfileMenuStructureItemWithSubtitleAndActionPreview() {
    ProjectTheme {
        ProfileMenuScope.Group().apply {
            ProfileMenuStructureItem(
                title = buildAnnotatedString(
                    fullText = stringResource(
                        R.string.profile_auth_provider_format,
                        AuthProvider.ANONYMOUS.displayTextShort
                    ),
                    AnnotatedData(
                        text = AuthProvider.ANONYMOUS.displayTextShort,
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