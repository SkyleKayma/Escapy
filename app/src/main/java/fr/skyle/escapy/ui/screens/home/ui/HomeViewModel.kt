package fr.skyle.escapy.ui.screens.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.usecase.lobby.WatchCurrentUserActiveLobbiesUseCase
import fr.skyle.escapy.data.usecase.user.WatchCurrentUserUseCase
import fr.skyle.escapy.ui.screens.home.ui.mapper.toLobbyUI
import fr.skyle.escapy.ui.screens.home.ui.model.LobbyUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    watchCurrentUserUseCase: WatchCurrentUserUseCase,
    private val watchCurrentUserActiveLobbiesUseCase: WatchCurrentUserActiveLobbiesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State())
    val state: StateFlow<State> by lazy { _state.asStateFlow() }

    init {
        viewModelScope.launch {
            watchCurrentUserUseCase()
                .collect { user ->
                    _state.update {
                        it.copy(
                            username = user?.username ?: "",
                        )
                    }
                }
        }

        viewModelScope.launch {
            watchCurrentUserActiveLobbiesUseCase()
                .collect { lobbies ->
                    _state.update {
                        it.copy(
                            isActiveLobbiesLoading = false,
                            activeLobbies = lobbies.map { it.toLobbyUI() }
                        )
                    }
                }
        }
    }

    data class State(
        val username: String? = null,
        val isActiveLobbiesLoading: Boolean = true,
        val activeLobbies: List<LobbyUI> = listOf()
    )
}