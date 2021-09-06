package fr.skyle.escapy.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.skyle.escapy.data.repository.NewsRepository
import fr.skyle.escapy.data.vo.News
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class NewsScreenViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val stateFlow = MutableStateFlow<State>(State.Loading)

    val newsFlow: Flow<List<News>> by lazy {
        newsRepository.watchNews().onStart {
            viewModelScope.launch {
                try {
//                    newsRepository.fetchNews()
                    stateFlow.emit(State.Loaded)
                } catch (e: CancellationException) {
                    throw e
                } catch (e: Exception) {
                    Timber.e(e, "Error listening for news")
                    stateFlow.emit(State.Error(getErrorEventFromException(e)))
                }
            }
        }
    }

    private fun getErrorEventFromException(exception: Exception): ErrorEvent =
        if (exception is SocketTimeoutException || exception is UnknownHostException) {
            ErrorEvent.NetworkIssue
        } else ErrorEvent.OtherIssue

    sealed class State {
        object Loading : State()
        object Loaded : State()
        data class Error(val errorEvent: ErrorEvent) : State()
    }

    sealed class ErrorEvent {
        object NetworkIssue : ErrorEvent()
        object OtherIssue : ErrorEvent()
    }
}