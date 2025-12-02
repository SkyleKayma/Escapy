package fr.skyle.escapy.data.usecase.user

import fr.skyle.escapy.data.repository.user.api.UserRepository
import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface WatchCurrentUserUseCase {
    suspend operator fun invoke(): Flow<User?>
}

class WatchCurrentUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : WatchCurrentUserUseCase {

    override suspend fun invoke(): Flow<User?> =
        userRepository.watchCurrentUser().map { user ->
            user
        }
}