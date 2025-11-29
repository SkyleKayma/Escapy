package fr.skyle.escapy.ui.screens.deleteAccount.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import fr.skyle.escapy.data.usecase.DeleteAccountFromAnonymousProviderUseCase
import fr.skyle.escapy.data.usecase.DeleteAccountFromAnonymousProviderUseCaseResponse
import fr.skyle.escapy.data.usecase.DeleteAccountFromEmailProviderUseCase
import fr.skyle.escapy.data.usecase.DeleteAccountFromEmailProviderUseCaseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    authRepository: AuthRepository,
    private val deleteAccountFromAnonymousProviderUseCase: DeleteAccountFromAnonymousProviderUseCase,
    private val deleteAccountFromEmailProviderUseCase: DeleteAccountFromEmailProviderUseCase,
) : ViewModel() {

    private val _deleteAccountState = MutableStateFlow<DeleteAccountState>(DeleteAccountState())
    val deleteAccountState: StateFlow<DeleteAccountState> by lazy { _deleteAccountState.asStateFlow() }

    init {
        viewModelScope.launch {
            _deleteAccountState.update {
                it.copy(
                    authProvider = authRepository.getAccountAuthProvider()
                )
            }
        }
    }

    fun deleteAccountFromEmailProvider(password: String) {
        viewModelScope.launch {
            _deleteAccountState.update {
                it.copy(
                    isButtonLoading = true
                )
            }

            when (val response = deleteAccountFromEmailProviderUseCase(password)) {
                is DeleteAccountFromEmailProviderUseCaseResponse.Error -> {
                    _deleteAccountState.update {
                        it.copy(
                            event = DeleteAccountEvent.Error(response.exception.message)
                        )
                    }
                }

                DeleteAccountFromEmailProviderUseCaseResponse.ErrorInvalidFields -> {
                    _deleteAccountState.update {
                        it.copy(
                            event = DeleteAccountEvent.InvalidPassword
                        )
                    }
                }

                DeleteAccountFromEmailProviderUseCaseResponse.Success -> {
                    _deleteAccountState.update {
                        it.copy(
                            event = DeleteAccountEvent.Success
                        )
                    }
                }
            }

            _deleteAccountState.update {
                it.copy(
                    isButtonLoading = false
                )
            }
        }
    }

    fun deleteAccountFromAnonymousProvider() {
        viewModelScope.launch {
            _deleteAccountState.update {
                it.copy(
                    isButtonLoading = true
                )
            }

            when (val response = deleteAccountFromAnonymousProviderUseCase()) {
                is DeleteAccountFromAnonymousProviderUseCaseResponse.Error -> {
                    _deleteAccountState.update {
                        it.copy(
                            event = DeleteAccountEvent.Error(response.exception.message)
                        )
                    }
                }

                DeleteAccountFromAnonymousProviderUseCaseResponse.InvalidFields -> {
                    _deleteAccountState.update {
                        it.copy(
                            event = DeleteAccountEvent.InvalidPassword
                        )
                    }
                }

                DeleteAccountFromAnonymousProviderUseCaseResponse.Success -> {
                    _deleteAccountState.update {
                        it.copy(
                            event = DeleteAccountEvent.Success
                        )
                    }
                }
            }

            _deleteAccountState.update {
                it.copy(
                    isButtonLoading = false
                )
            }
        }
    }

    fun eventDelivered() {
        viewModelScope.launch {
            _deleteAccountState.update {
                it.copy(event = null)
            }
        }
    }

    data class DeleteAccountState(
        val authProvider: AuthProvider = AuthProvider.ANONYMOUS,
        val isButtonLoading: Boolean = false,
        val event: DeleteAccountEvent? = null,
    )

    sealed interface DeleteAccountEvent {
        data object Success : DeleteAccountEvent
        data object InvalidPassword : DeleteAccountEvent
        data class Error(val message: String?) : DeleteAccountEvent
    }
}