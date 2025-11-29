package fr.skyle.escapy.data.usecase.user

import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.repository.user.api.UserRepository
import kotlinx.coroutines.CancellationException
import timber.log.Timber
import javax.inject.Inject

interface UpdateAvatarUseCase {
    suspend operator fun invoke(
        avatar: Avatar
    ): UpdateAvatarUseCaseResponse
}

class UpdateAvatarUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val fetchCurrentUserUseCase: FetchCurrentUserUseCase
) : UpdateAvatarUseCase {

    override suspend fun invoke(
        avatar: Avatar
    ): UpdateAvatarUseCaseResponse {
        return try {
            // Update
            userRepository.updateAvatar(avatar).getOrThrow()

            // Fetch
            val fetchCurrentUserUseCaseResponse = fetchCurrentUserUseCase()
            if (fetchCurrentUserUseCaseResponse is FetchCurrentUserUseCaseResponse.Error) {
                return UpdateAvatarUseCaseResponse.Error(fetchCurrentUserUseCaseResponse.message)
            }

            UpdateAvatarUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.e(e)
            UpdateAvatarUseCaseResponse.Error(e.message)
        }
    }
}

sealed interface UpdateAvatarUseCaseResponse {
    data object Success : UpdateAvatarUseCaseResponse
    data class Error(val message: String?) : UpdateAvatarUseCaseResponse
}