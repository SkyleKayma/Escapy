package fr.skyle.escapy.ui.screens.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.MIN_FETCH_DELAY
import fr.skyle.escapy.data.usecase.lobby.FetchLobbiesForCurrentUserUseCaseImpl
import fr.skyle.escapy.data.usecase.lobby.WatchCurrentUserActiveLobbiesUseCase
import fr.skyle.escapy.data.usecase.user.FetchCurrentUserUseCaseImpl
import fr.skyle.escapy.data.usecase.user.WatchCurrentUserUseCase
import fr.skyle.escapy.ui.screens.home.ui.mapper.toLobbyUI
import fr.skyle.escapy.ui.screens.home.ui.model.LobbyUI
import kotlinx.coroutines.CancellationException
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
    private val fetchLobbiesForCurrentUserUseCaseImpl: FetchLobbiesForCurrentUserUseCaseImpl
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
                    Timber.d("0 ${lobbies.joinToString(";")}")
                    _state.update {
                        it.copy(
                            activeLobbies = lobbies.map { it.toLobbyUI() }
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
                val minDelayJob = async { delay(MIN_FETCH_DELAY) }

                val fetchJob = async {
                    // Fetch user
                    fetchCurrentUserUseCaseImpl()

                    // Fetch lobbies
                    fetchLobbiesForCurrentUserUseCaseImpl()
                }

                listOf(minDelayJob, fetchJob).awaitAll()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e)
                _state.update {
                    it.copy(event = HomeEvent.FetchError)
                }
            } finally {
                _state.update {
                    it.copy(isRefreshing = false)
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
        val activeLobbies: List<LobbyUI> = emptyList(),
        val event: HomeEvent? = null
    )

    sealed interface HomeEvent {
        object FetchError : HomeEvent
    }
}