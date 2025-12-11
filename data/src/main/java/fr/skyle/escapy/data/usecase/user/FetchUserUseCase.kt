package fr.skyle.escapy.data.usecase.user

import fr.skyle.escapy.data.repository.user.api.UserRepository
import javax.inject.Inject

interface FetchUserUseCase {

    suspend operator fun invoke(userId: String)
}

class FetchUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : FetchUserUseCase {

    override suspend fun invoke(userId: String) {
        userRepository.fetchUser(userId)
    }
}