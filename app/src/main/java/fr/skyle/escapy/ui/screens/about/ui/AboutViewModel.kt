package fr.skyle.escapy.ui.screens.about.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.MIN_DELAY_BEFORE_SHOWING_SCREEN_LOADER
import fr.skyle.escapy.data.repository.github.api.GithubRepository
import fr.skyle.escapy.ui.screens.about.ui.mapper.toGithubContributorUI
import fr.skyle.escapy.ui.screens.about.ui.model.GithubContributorUI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State())
    val state: StateFlow<State> by lazy { _state.asStateFlow() }

    init {
        fetchContributors()
    }

    fun fetchContributors() {
        viewModelScope.launch {
            // Start a delayed job to show the loading state only if api call is slow
            val showLoadingJob = launch {
                delay(MIN_DELAY_BEFORE_SHOWING_SCREEN_LOADER)
                _state.update {
                    it.copy(isContributorsLoading = true)
                }
            }

            try {
                val contributorsUI = githubRepository.getGithubContributors()
                    .getOrThrow()
                    .sortedByDescending { it.nbContributions }
                    .map { it.toGithubContributorUI() }

                showLoadingJob.cancel()

                _state.update {
                    it.copy(contributors = contributorsUI)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                _state.update {
                    it.copy(isContributorsLoading = false)
                }
            }
        }
    }

    data class State(
        val isContributorsLoading: Boolean = true,
        val contributors: List<GithubContributorUI> = emptyList(),
    )
}