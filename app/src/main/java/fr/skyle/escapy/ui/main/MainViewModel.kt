package fr.skyle.escapy.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.repository.user.api.UserRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    fun isUserLoggedIn(): Boolean =
        userRepository.isUserLoggedIn()
}