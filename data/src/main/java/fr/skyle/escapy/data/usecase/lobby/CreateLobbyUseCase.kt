package fr.skyle.escapy.data.usecase.lobby

import fr.skyle.escapy.data.repository.lobby.api.LobbyRepository
import kotlinx.coroutines.CancellationException
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

interface CreateLobbyUseCase {
    suspend operator fun invoke(
        title: String,
        duration: Long
    ): CreateLobbyUseCaseResponse
}

class CreateLobbyUseCaseImpl @Inject constructor(
    private val lobbyRepository: LobbyRepository
) : CreateLobbyUseCase {

    override suspend fun invoke(
        title: String,
        duration: Long
    ): CreateLobbyUseCaseResponse {
        return try {
            val password = Random.nextInt(1000, 10000).toString()

            lobbyRepository.createLobby(
                title = title,
                password = password,
                duration = duration
            ).getOrThrow()

            CreateLobbyUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.e(e)
            CreateLobbyUseCaseResponse.Error(e.message)
        }
    }
}

sealed interface CreateLobbyUseCaseResponse {
    data object Success : CreateLobbyUseCaseResponse
    data class Error(val message: String?) : CreateLobbyUseCaseResponse
}