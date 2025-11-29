package fr.skyle.escapy.ui.screens.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.usecase.user.WatchCurrentUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    watchCurrentUserUseCase: WatchCurrentUserUseCase
) : ViewModel() {

    private val _homeState = MutableStateFlow<HomeState>(HomeState())
    val homeState: StateFlow<HomeState> by lazy { _homeState.asStateFlow() }

    init {
        viewModelScope.launch {
            watchCurrentUserUseCase()
                .map { it.user }
                .filterNotNull()
                .collectLatest { user ->
                    _homeState.update {
                        it.copy(username = user.username)
                    }
                }
        }
    }

    data class HomeState(
        val username: String? = null
    )
}