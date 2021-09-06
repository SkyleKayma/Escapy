package fr.skyle.escapy.ui.login

import androidx.lifecycle.ViewModel
import fr.skyle.escapy.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow


class LoginScreenViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    val eventFlow = MutableStateFlow<Event?>(null)


    sealed class Event {
        object LoginSuccess : Event()
        object NetworkIssue : Event()
        object OtherIssue : Event()
    }
}