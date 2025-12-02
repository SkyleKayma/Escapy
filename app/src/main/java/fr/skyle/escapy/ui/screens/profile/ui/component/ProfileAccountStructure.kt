package fr.skyle.escapy.ui.screens.profile.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.displayTextShort
import fr.skyle.escapy.utils.AnnotatedData
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.buildAnnotatedString

@Composable
fun ProfileAccountStructure(
    authProvider: AuthProvider,
    onLinkAccountClicked: (() -> Unit),
    onChangeEmailClicked: (() -> Unit),
    onChangePasswordClicked: (() -> Unit),
    modifier: Modifier = Modifier
) {
    ProfileMenuStructure(
        modifier = modifier,
        title = stringResource(R.string.profile_account)
    ) {
        if (authProvider == AuthProvider.ANONYMOUS) {
            group {
                item {
                    ProfileMenuStructureItem(
                        modifier = Modifier.fillMaxWidth(),
                        title = buildAnnotatedString(
                            fullText = stringResource(
                                R.string.profile_auth_provider_format,
                                authProvider.displayTextShort
                            ),
                            AnnotatedData(
                                text = authProvider.displayTextShort,
                                spanStyle = SpanStyle(
                                    color = ProjectTheme.colors.warning,
                                )
                            )
                        ),
                        subtitle = stringResource(R.string.profile_warning_guest),
                        onCellClicked = onLinkAccountClicked
                    )
                }
            }
        }

        if (authProvider == AuthProvider.EMAIL) {
            group {
                item {
                    ProfileMenuStructureItem(
                        modifier = Modifier.fillMaxWidth(),
                        title = AnnotatedString(stringResource(R.string.profile_change_email)),
                        onCellClicked = onChangeEmailClicked
                    )
                }

                item {
                    ProfileMenuStructureItem(
                        modifier = Modifier.fillMaxWidth(),
                        title = AnnotatedString(stringResource(R.string.profile_change_password)),
                        onCellClicked = onChangePasswordClicked
                    )
                }
            }
        }
    }
}

private class ProfileAccountStructurePreviewDataProvider :
    CollectionPreviewParameterProvider<ProfileAccountStructurePreviewData>(
        collection = buildList {
            AuthProvider.entries.forEach { authProvider ->
                add(
                    ProfileAccountStructurePreviewData(
                        authProvider = authProvider,
                    )
                )
            }
        }
    )

private data class ProfileAccountStructurePreviewData(
    val authProvider: AuthProvider,
)

@ProjectComponentPreview
@Composable
private fun ProfileAccountStructurePreview(
    @PreviewParameter(ProfileAccountStructurePreviewDataProvider::class) data: ProfileAccountStructurePreviewData
) {
    ProjectTheme {
        ProfileAccountStructure(
            authProvider = data.authProvider,
            onLinkAccountClicked = {},
            onChangeEmailClicked = {},
            onChangePasswordClicked = {}
        )
    }
}