package fr.skyle.escapy.data.usecase.user

import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.repository.user.api.UserRepository
import javax.inject.Inject

interface UpdateRemoteAvatarUseCase {

    suspend operator fun invoke(avatar: Avatar)
}

class UpdateRemoteRemoteAvatarUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val fetchCurrentUserUseCase: FetchCurrentUserUseCase
) : UpdateRemoteAvatarUseCase {

    override suspend fun invoke(avatar: Avatar) {
        // Update
        userRepository.updateRemoteAvatar(avatar)

        // Fetch after update
        fetchCurrentUserUseCase()
    }
}