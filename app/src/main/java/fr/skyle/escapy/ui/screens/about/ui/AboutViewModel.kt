package fr.skyle.escapy.ui.screens.about.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.repository.github.api.GithubRepository
import fr.skyle.escapy.data.rest.response.GithubContributorResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class AboutViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val _aboutState = MutableStateFlow<AboutState>(AboutState())
    val aboutState: StateFlow<AboutState> = _aboutState.asStateFlow()

    init {
        fetchContributors()
    }

    fun fetchContributors() {
        viewModelScope.launch {
            // Start a delayed job to show the loading state only if api call is slow
            val showLoadingJob = launch {
                delay(300.milliseconds)
                _aboutState.update {
                    it.copy(isContributorsLoading = true)
                }
            }

            try {
                val contributors = githubRepository.getGithubContributors().getOrThrow() ?: listOf()

                showLoadingJob.cancel()

                _aboutState.update {
                    it.copy(contributors = contributors.sortedByDescending { it.contributions })
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e)
            } finally {
                _aboutState.update {
                    it.copy(isContributorsLoading = false)
                }
            }
        }
    }

    data class AboutState(
        val isContributorsLoading: Boolean = true,
        val contributors: List<GithubContributorResponse> = listOf(),
    )
}