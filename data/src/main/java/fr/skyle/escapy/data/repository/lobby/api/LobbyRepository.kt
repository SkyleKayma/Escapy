package fr.skyle.escapy.data.repository.lobby.api

interface LobbyRepository {

    suspend fun createLobby(
        title: String,
        password: String?,
        duration: Long
    ): Result<Unit>

    suspend fun fetchLobby(lobbyId: String): Result<Unit>

    suspend fun fetchLobbiesForCurrentUser(): Result<Unit>

//    suspend fun joinLobby(lobbyId: String): Result<Unit>
//
//    suspend fun leaveLobby(lobbyId: String): Result<Unit>
//
//    suspend fun removeParticipant(lobbyId: String, participantId: String): Result<Unit>
//
//    fun watchLobby(lobbyId: String): Flow<Lobby?>
//
//    fun watchActiveLobbies(): Flow<List<Lobby>>
}