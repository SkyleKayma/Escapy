package fr.skyle.escapy.ui.screens.deleteAccount.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.usecase.firebaseAuth.DeleteAccountFromAnonymousProviderUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.DeleteAccountFromAnonymousProviderUseCaseResponse
import fr.skyle.escapy.data.usecase.firebaseAuth.DeleteAccountFromEmailProviderUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.DeleteAccountFromEmailProviderUseCaseResponse
import fr.skyle.escapy.data.utils.FirebaseAuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val firebaseAuthManager: FirebaseAuthManager,
    private val deleteAccountFromAnonymousProviderUseCase: DeleteAccountFromAnonymousProviderUseCase,
    private val deleteAccountFromEmailProviderUseCase: DeleteAccountFromEmailProviderUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State())
    val state: StateFlow<State> by lazy { _state.asStateFlow() }

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    authProvider = firebaseAuthManager.state.firstOrNull()?.user?.authProvider
                        ?: AuthProvider.ANONYMOUS
                )
            }
        }
    }

    fun deleteAccountFromEmailProvider(password: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isButtonLoading = true)
            }

            when (val response = deleteAccountFromEmailProviderUseCase(password)) {
                is DeleteAccountFromEmailProviderUseCaseResponse.Error -> {
                    _state.update {
                        it.copy(event = DeleteAccountEvent.Error(response.message))
                    }
                }

                DeleteAccountFromEmailProviderUseCaseResponse.InvalidCurrentPassword -> {
                    _state.update {
                        it.copy(event = DeleteAccountEvent.InvalidCurrentPassword)
                    }
                }

                DeleteAccountFromEmailProviderUseCaseResponse.Success -> {
                    _state.update {
                        it.copy(event = DeleteAccountEvent.Success)
                    }
                }
            }

            _state.update {
                it.copy(isButtonLoading = false)
            }
        }
    }

    fun deleteAccountFromAnonymousProvider() {
        viewModelScope.launch {
            _state.update {
                it.copy(isButtonLoading = true)
            }

            when (val response = deleteAccountFromAnonymousProviderUseCase()) {
                is DeleteAccountFromAnonymousProviderUseCaseResponse.Error -> {
                    _state.update {
                        it.copy(event = DeleteAccountEvent.Error(response.message))
                    }
                }

                DeleteAccountFromAnonymousProviderUseCaseResponse.Success -> {
                    _state.update {
                        it.copy(event = DeleteAccountEvent.Success)
                    }
                }
            }

            _state.update {
                it.copy(isButtonLoading = false)
            }
        }
    }

    fun eventDelivered() {
        _state.update {
            it.copy(event = null)
        }
    }

    data class State(
        val authProvider: AuthProvider = AuthProvider.ANONYMOUS,
        val isButtonLoading: Boolean = false,
        val event: DeleteAccountEvent? = null,
    )

    sealed interface DeleteAccountEvent {
        data object Success : DeleteAccountEvent
        data object InvalidCurrentPassword : DeleteAccountEvent
        data class Error(val message: String?) : DeleteAccountEvent
    }
}