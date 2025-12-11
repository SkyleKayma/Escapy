package fr.skyle.escapy.data.usecase.user

import fr.skyle.escapy.data.repository.user.api.UserRepository
import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface WatchUserUseCase {

    suspend operator fun invoke(userId: String): Flow<User?>
}

class WatchUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : WatchUserUseCase {

    override suspend fun invoke(userId: String): Flow<User?> =
        userRepository.watchUser(userId).map { user ->
            user
        }
}