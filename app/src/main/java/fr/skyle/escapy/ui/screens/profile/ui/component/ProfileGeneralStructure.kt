package fr.skyle.escapy.ui.screens.profile.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.navigateToDataPrivacy
import fr.skyle.escapy.ext.navigateToOpenium
import fr.skyle.escapy.utils.AnnotatedData
import fr.skyle.escapy.utils.buildAnnotatedString

@Composable
fun ProfileGeneralStructure(
    onNotificationsClicked: () -> Unit,
    onAboutAppClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    ProfileMenuStructure(
        modifier = modifier,
        title = stringResource(R.string.profile_general)
    ) {
        group {
            item {
                ProfileMenuStructureItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = AnnotatedString(stringResource(R.string.profile_notifications)),
                    onCellClicked = onNotificationsClicked
                )
            }

            item {
                ProfileMenuStructureItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = AnnotatedString(stringResource(R.string.profile_openium)),
                    onCellClicked = context::navigateToOpenium
                )
            }

            item {
                ProfileMenuStructureItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = AnnotatedString(stringResource(R.string.profile_privacy_policy)),
                    onCellClicked = context::navigateToDataPrivacy
                )
            }

            item {
                ProfileMenuStructureItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = AnnotatedString(stringResource(R.string.profile_about_app)),
                    onCellClicked = onAboutAppClicked
                )
            }

            item {
                ProfileMenuStructureItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = buildAnnotatedString(
                        fullText = stringResource(R.string.profile_sign_out),
                        AnnotatedData(
                            text = stringResource(R.string.profile_sign_out),
                            spanStyle = SpanStyle(
                                color = ProjectTheme.colors.error,
                            )
                        )
                    ),
                    onCellClicked = onSignOutClicked
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileGeneralStructurePreview() {
    ProjectTheme {
        ProfileGeneralStructure(
            onNotificationsClicked = {},
            onAboutAppClicked = {},
            onSignOutClicked = {},
        )
    }
}