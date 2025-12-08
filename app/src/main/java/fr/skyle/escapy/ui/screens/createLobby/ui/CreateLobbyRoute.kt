package fr.skyle.escapy.ui.screens.createLobby.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.snackbar.ProjectSnackbarDefaults
import fr.skyle.escapy.designsystem.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.designsystem.core.timePicker.ProjectTimePickerDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLobbyRoute(
    navigateToLobby: (lobbyId: String) -> Unit,
    navigateBack: () -> Unit,
    createLobbyViewModel: CreateLobbyViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val createLobbyState by createLobbyViewModel.state.collectAsStateWithLifecycle()

    val snackbarState = rememberProjectSnackbarState()

    var isTimePickerVisible by remember { mutableStateOf(false) }

    LaunchedEffect(createLobbyState.event) {
        createLobbyState.event?.let { event ->
            when (event) {
                is CreateLobbyViewModel.CreateLobbyEvent.Error -> {
                    snackbarState.showSnackbar(
                        message = context.getString(
                            R.string.generic_error_format,
                            event.message ?: "-"
                        ),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }

                CreateLobbyViewModel.CreateLobbyEvent.InvalidFields -> {
                    snackbarState.showSnackbar(
                        message = context.getString(R.string.generic_error_invalid_fields),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.NEUTRAL
                    )
                }

                is CreateLobbyViewModel.CreateLobbyEvent.Success -> {
                    navigateToLobby(event.lobbyId)
                }
            }

            createLobbyViewModel.eventDelivered()
        }
    }

    CreateLobbyScreen(
        snackbarState = snackbarState,
        state = createLobbyState,
        onTitleChange = createLobbyViewModel::setTitle,
        onShowTimePicker = {
            isTimePickerVisible = true
        },
        onCreateLobby = createLobbyViewModel::createLobby,
        navigateBack = navigateBack,
    )

    if (isTimePickerVisible) {
        val initialHours = (createLobbyState.duration / 3600000).toInt()
        val initialMinutes = ((createLobbyState.duration % 3600000) / 60000).toInt()

        val timePickerState = rememberTimePickerState(
            initialHour = initialHours,
            initialMinute = initialMinutes,
            is24Hour = true
        )

        ProjectTimePickerDialog(
            state = timePickerState,
            confirmText = stringResource(R.string.generic_action_confirm),
            dismissText = stringResource(R.string.generic_action_cancel),
            onDismissRequest = {
                isTimePickerVisible = false
            },
            onDismissButtonClicked = {
                isTimePickerVisible = false
            },
            onConfirmButtonClicked = {
                val millis =
                    timePickerState.hour * 3600000L +
                            timePickerState.minute * 60000L

                createLobbyViewModel.setDuration(millis)
            }
        )
    }
}