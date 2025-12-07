package fr.skyle.escapy.data.usecase.user

import fr.skyle.escapy.data.repository.user.api.UserRepository
import javax.inject.Inject

interface FetchCurrentUserUseCase {
    suspend operator fun invoke()
}

class FetchCurrentUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : FetchCurrentUserUseCase {

    override suspend fun invoke() {
        userRepository.fetchCurrentUser().getOrThrow()
    }
}