package fr.skyle.escapy.ui.screens.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.MIN_DELAY_TO_SHOW_LOADER
import fr.skyle.escapy.data.usecase.lobby.EnsureLobbyAccessibleUseCase
import fr.skyle.escapy.data.usecase.lobby.EnsureLobbyAccessibleUseCaseLobbyDoesNotExist
import fr.skyle.escapy.data.usecase.lobby.EnsureLobbyAccessibleUseCaseNotInLobby
import fr.skyle.escapy.data.usecase.lobby.FetchLobbiesForCurrentUserUseCaseImpl
import fr.skyle.escapy.data.usecase.lobby.WatchCurrentUserActiveLobbiesUseCase
import fr.skyle.escapy.data.usecase.user.FetchCurrentUserUseCaseImpl
import fr.skyle.escapy.data.usecase.user.WatchCurrentUserUseCase
import fr.skyle.escapy.ui.screens.home.ui.mapper.toLobbyUI
import fr.skyle.escapy.ui.screens.home.ui.model.LobbyUI
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    watchCurrentUserUseCase: WatchCurrentUserUseCase,
    private val watchCurrentUserActiveLobbiesUseCase: WatchCurrentUserActiveLobbiesUseCase,
    private val fetchCurrentUserUseCaseImpl: FetchCurrentUserUseCaseImpl,
    private val fetchLobbiesForCurrentUserUseCaseImpl: FetchLobbiesForCurrentUserUseCaseImpl,
    private val ensureLobbyAccessibleUseCase: EnsureLobbyAccessibleUseCase,
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
                            activeLobbies = lobbies.map { it.toLobbyUI() }.sortedBy { it.createdAt }
                        )
                    }
                }
        }
    }

    fun fetch() {
        viewModelScope.launch {
            _state.update {
                it.copy(isRefreshing = true)
            }

            try {
                val minDelayJob = async(SupervisorJob()) {
                    delay(MIN_DELAY_TO_SHOW_LOADER)
                }

                val fetchJob = async(SupervisorJob()) {
                    // Fetch user
                    fetchCurrentUserUseCaseImpl()

                    // Fetch lobbies
                    fetchLobbiesForCurrentUserUseCaseImpl()
                }

                awaitAll(minDelayJob, fetchJob)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e)
                _state.update {
                    it.copy(event = HomeEvent.Error(e.message))
                }
            } finally {
                _state.update {
                    it.copy(isRefreshing = false)
                }
            }
        }
    }

    fun ensureLobbyIsAccessible(lobbyId: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isChecking = true)
            }

            try {
                ensureLobbyAccessibleUseCase(lobbyId)

                _state.update {
                    it.copy(event = HomeEvent.HasAccessToLobby(lobbyId))
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: EnsureLobbyAccessibleUseCaseLobbyDoesNotExist) {
                Timber.e(e)
                _state.update {
                    it.copy(event = HomeEvent.LobbyNotExisting)
                }
            } catch (e: EnsureLobbyAccessibleUseCaseNotInLobby) {
                Timber.e(e)
                _state.update {
                    it.copy(event = HomeEvent.NotInLobby)
                }
            } catch (e: Exception) {
                Timber.e(e)
                _state.update {
                    it.copy(event = HomeEvent.Error(e.message))
                }
            } finally {
                _state.update {
                    it.copy(isChecking = false)
                }
            }
        }
    }

    fun eventDelivered() {
        _state.update {
            it.copy(event = null)
        }
    }

    data class State(
        val username: String? = null,
        val isRefreshing: Boolean = false,
        val isChecking: Boolean = false,
        val activeLobbies: List<LobbyUI> = emptyList(),
        val event: HomeEvent? = null
    )

    sealed interface HomeEvent {
        data class Error(val message: String?) : HomeEvent
        data object LobbyNotExisting : HomeEvent
        data object NotInLobby : HomeEvent
        data class HasAccessToLobby(val lobbyId: String) : HomeEvent
    }
}