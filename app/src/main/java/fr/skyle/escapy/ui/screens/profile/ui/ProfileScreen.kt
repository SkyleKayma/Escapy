package fr.skyle.escapy.ui.screens.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.BuildConfig
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.core.topAppBar.ProjectTopAppBar
import fr.skyle.escapy.designsystem.core.topAppBar.component.ProjectTopAppBarItem
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.ui.screens.profile.ui.component.ProfileAccountStructure
import fr.skyle.escapy.ui.screens.profile.ui.component.ProfileAvatar
import fr.skyle.escapy.ui.screens.profile.ui.component.ProfileGeneralStructure
import fr.skyle.escapy.utils.DateFormat
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.ProjectScreenPreview
import fr.skyle.escapy.utils.format

@Composable
fun ProfileScreen(
    profileState: ProfileViewModel.ProfileState,
    onBackButtonClicked: () -> Unit,
    onLinkAccountClicked: () -> Unit,
    onChangeEmailClicked: () -> Unit,
    onChangePasswordClicked: () -> Unit,
    onNotificationsClicked: () -> Unit,
    onAboutAppClicked: () -> Unit,
    onSignOutClicked: () -> Unit
) {
    ProjectScreenStructure(
        modifier = Modifier.fillMaxSize(),
        isPatternDisplayed = true,
        topContent = {
            ProjectTopAppBar(
                modifier = Modifier.fillMaxWidth(),
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
            username = profileState.username,
            email = profileState.email,
            createdAt = profileState.createdAt,
            avatar = profileState.avatar,
            authProvider = profileState.authProvider,
            onLinkAccountClicked = onLinkAccountClicked,
            onChangeEmailClicked = onChangeEmailClicked,
            onChangePasswordClicked = onChangePasswordClicked,
            onNotificationsClicked = onNotificationsClicked,
            onAboutAppClicked = onAboutAppClicked,
            onSignOutClicked = onSignOutClicked,
        )
    }
}

@Composable
private fun ProfileScreenContent(
    innerPadding: PaddingValues,
    username: String?,
    email: String?,
    createdAt: Long?,
    avatar: Avatar?,
    authProvider: AuthProvider,
    onLinkAccountClicked: () -> Unit,
    onChangeEmailClicked: () -> Unit,
    onChangePasswordClicked: () -> Unit,
    onNotificationsClicked: () -> Unit,
    onAboutAppClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = innerPadding.calculateTopPadding())
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileAvatar(
                modifier = Modifier.size(100.dp),
                avatar = avatar
            )

            Spacer(modifier = Modifier.width(24.dp))

            Column(modifier = Modifier.wrapContentWidth()) {
                Text(
                    modifier = Modifier.wrapContentWidth(),
                    text = username ?: "-",
                    style = ProjectTheme.typography.b2,
                    color = ProjectTheme.colors.primary,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                email?.let { email ->
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        modifier = Modifier.wrapContentWidth(),
                        text = email,
                        style = ProjectTheme.typography.p3,
                        color = ProjectTheme.colors.onSurface,
                        textAlign = TextAlign.Center,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

                createdAt?.format(DateFormat.LONG_DATE)?.let { date ->
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        modifier = Modifier.wrapContentWidth(),
                        text = stringResource(R.string.profile_created_at_format, date),
                        style = ProjectTheme.typography.p3,
                        color = ProjectTheme.colors.onSurface,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ProfileAccountStructure(
            modifier = Modifier.fillMaxWidth(),
            authProvider = authProvider,
            onLinkAccountClicked = onLinkAccountClicked,
            onChangeEmailClicked = onChangeEmailClicked,
            onChangePasswordClicked = onChangePasswordClicked,
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProfileGeneralStructure(
            modifier = Modifier.fillMaxWidth(),
            onNotificationsClicked = onNotificationsClicked,
            onAboutAppClicked = onAboutAppClicked,
            onSignOutClicked = onSignOutClicked,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(
                R.string.profile_version_format,
                BuildConfig.VERSION_NAME,
                BuildConfig.VERSION_CODE.toString()
            ),
            style = ProjectTheme.typography.p3,
            color = ProjectTheme.colors.grey500,
            textAlign = TextAlign.End
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
                username = "John Doe",
                avatar = Avatar.AVATAR_01,
            ),
            onBackButtonClicked = {},
            onLinkAccountClicked = {},
            onChangeEmailClicked = {},
            onChangePasswordClicked = {},
            onNotificationsClicked = {},
            onAboutAppClicked = {},
            onSignOutClicked = {},
        )
    }
}

@ProjectComponentPreview
@Composable
private fun ProfileScreenContentPreview() {
    ProjectTheme {
        ProfileScreenContent(
            innerPadding = PaddingValues(),
            username = "John Doe",
            email = "john.doe@test.fr",
            createdAt = System.currentTimeMillis(),
            avatar = null,
            authProvider = AuthProvider.ANONYMOUS,
            onLinkAccountClicked = {},
            onChangeEmailClicked = {},
            onChangePasswordClicked = {},
            onNotificationsClicked = {},
            onAboutAppClicked = {},
            onSignOutClicked = {},
        )
    }
}