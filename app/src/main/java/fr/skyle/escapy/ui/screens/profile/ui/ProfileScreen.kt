package fr.skyle.escapy.ui.screens.profile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.core.topAppBar.ProjectTopAppBar
import fr.skyle.escapy.designsystem.core.topAppBar.component.ProjectTopAppBarItem
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.displayText
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.ui.screens.profile.ui.component.ProfileAvatar
import fr.skyle.escapy.ui.screens.profile.ui.component.ProfileMenuStructure
import fr.skyle.escapy.ui.screens.profile.ui.component.ProfileMenuStructureData
import fr.skyle.escapy.ui.screens.profile.ui.component.ProfileMenuStructureGroupData
import fr.skyle.escapy.ui.screens.profile.ui.component.ProfileMenuStructureItemData
import fr.skyle.escapy.utils.AnnotatedData
import fr.skyle.escapy.utils.ProjectScreenPreview
import fr.skyle.escapy.utils.buildAnnotatedString

@Composable
fun ProfileScreen(
    profileState: ProfileViewModel.ProfileState,
    onBackButtonClicked: () -> Unit
) {
    ProjectScreenStructure(
        modifier = Modifier.fillMaxSize(),
        isPatternDisplayed = true,
        topContent = {
            ProjectTopAppBar(
                title = stringResource(R.string.profile_title),
                leadingContent = {
                    ProjectTopAppBarItem(
                        icon = rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowBack),
                        style = ProjectIconButtonDefaults.IconButtonStyle.FILLED,
                        onClick = onBackButtonClicked,
                    )
                },
            )
        }
    ) { innerPadding ->
        ProfileScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            username = profileState.userName,
            avatar = profileState.avatar,
            authProvider = profileState.authProvider
        )
    }
}

@Composable
private fun ProfileScreenContent(
    innerPadding: PaddingValues,
    username: String?,
    avatar: Avatar?,
    authProvider: AuthProvider,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = innerPadding.calculateTopPadding())
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp)
    ) {
        ProfileAvatar(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally),
            avatar = avatar
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = username ?: "-",
            style = ProjectTheme.typography.p2,
            color = ProjectTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        val accountMenuData = ProfileMenuStructureData(
            title = stringResource(R.string.profile_account),
            groups = listOf(
                ProfileMenuStructureGroupData(
                    items = listOf(
                        ProfileMenuStructureItemData(
                            title = buildAnnotatedString(
                                fullText = stringResource(
                                    R.string.profile_auth_provider,
                                    authProvider.displayText
                                ),
                                AnnotatedData(
                                    text = authProvider.displayText,
                                    spanStyle = SpanStyle(
                                        color = ProjectTheme.colors.warning,
                                    )
                                )
                            ),
                            subtitle = stringResource(R.string.profile_warning_guest),
                            onCellClicked = {
                                // TODO
                            }
                        )
                    )
                ),
                ProfileMenuStructureGroupData(
                    items = listOf(
                        ProfileMenuStructureItemData(
                            title = AnnotatedString(stringResource(R.string.profile_edit_profile)),
                            onCellClicked = {
                                // TODO
                            }
                        ),
                        ProfileMenuStructureItemData(
                            title = AnnotatedString(stringResource(R.string.profile_change_password)),
                            onCellClicked = {
                                // TODO
                            }
                        )
                    )
                )
            )
        )

        ProfileMenuStructure(
            modifier = Modifier.fillMaxWidth(),
            data = accountMenuData
        )

        Spacer(modifier = Modifier.height(24.dp))

        val generalMenuData = ProfileMenuStructureData(
            title = stringResource(R.string.profile_general),
            groups = listOf(
                ProfileMenuStructureGroupData(
                    items = listOf(
                        ProfileMenuStructureItemData(
                            title = AnnotatedString(stringResource(R.string.profile_notifications)),
                            onCellClicked = {
                                // TODO
                            }
                        ),
                        ProfileMenuStructureItemData(
                            title = AnnotatedString(stringResource(R.string.profile_openium)),
                            onCellClicked = {
                                // TODO
                            }
                        ),
                        ProfileMenuStructureItemData(
                            title = AnnotatedString(stringResource(R.string.profile_privacy_policy)),
                            onCellClicked = {
                                // TODO
                            }
                        ),
                        ProfileMenuStructureItemData(
                            title = AnnotatedString(stringResource(R.string.profile_about_app)),
                            onCellClicked = {
                                // TODO
                            }
                        ),
                        ProfileMenuStructureItemData(
                            title = buildAnnotatedString(
                                fullText = stringResource(R.string.profile_sign_out),
                                AnnotatedData(
                                    text = stringResource(R.string.profile_sign_out),
                                    spanStyle = SpanStyle(
                                        color = ProjectTheme.colors.error,
                                    )
                                )
                            ),
                            onCellClicked = {
                                // TODO
                            }
                        )
                    )
                )
            )
        )

        ProfileMenuStructure(
            modifier = Modifier.fillMaxWidth(),
            data = generalMenuData
        )

        Spacer(modifier = Modifier.padding(innerPadding.calculateBottomPadding()))
    }
}

@ProjectScreenPreview
@Composable
private fun ProfileScreenPreview() {
    ProjectTheme {
        ProfileScreen(
            profileState = ProfileViewModel.ProfileState(
                userName = "John Doe",
                avatar = Avatar.AVATAR_01,
                authProvider = AuthProvider.ANONYMOUS
            ),
            onBackButtonClicked = {}
        )
    }
}

@Preview
@Composable
private fun ProfileScreenContentPreview() {
    ProjectTheme {
        ProfileScreenContent(
            innerPadding = PaddingValues(),
            username = "John Doe",
            avatar = null,
            authProvider = AuthProvider.ANONYMOUS
        )
    }
}