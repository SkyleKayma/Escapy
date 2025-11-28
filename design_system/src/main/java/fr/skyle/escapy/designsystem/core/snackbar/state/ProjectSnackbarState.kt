package fr.skyle.escapy.designsystem.core.snackbar.state

import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import fr.skyle.escapy.designsystem.core.snackbar.ProjectSnackbarDefaults
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

data class ProjectSnackbarData(
    private val snackbarData: SnackbarData,
    val type: ProjectSnackbarDefaults.ProjectSnackbarType,
) {
    val message: String
        get() = snackbarData.visuals.message

    val actionLabel: String?
        get() = snackbarData.visuals.actionLabel

    fun performAction() = snackbarData.performAction()
}

@Stable
class ProjectSnackbarState(
    val snackbarHostState: SnackbarHostState
) {
    private val mutex = Mutex()

    private var currentSnackbarType by mutableStateOf<ProjectSnackbarDefaults.ProjectSnackbarType?>(
        value = null
    )

    val currentSnackbarData: ProjectSnackbarData?
        get() {
            return ProjectSnackbarData(
                snackbarData = snackbarHostState.currentSnackbarData ?: return null,
                type = currentSnackbarType ?: return null
            )
        }

    suspend fun showSnackbar(
        message: String,
        type: ProjectSnackbarDefaults.ProjectSnackbarType,
        actionLabel: String? = null,
    ): SnackbarResult {
        mutex.withLock {
            try {
                currentSnackbarType = type
                return snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel
                )
            } finally {
                currentSnackbarType = null
            }
        }
    }
}

@Composable
fun rememberProjectSnackbarState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
): ProjectSnackbarState {
    return remember {
        ProjectSnackbarState(snackbarHostState = snackbarHostState)
    }
}

