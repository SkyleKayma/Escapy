package fr.skyle.escapy.data.repository.lobby.api

import fr.skyle.escapy.data.repository.lobby.model.CreateLobbyRequest
import fr.skyle.escapy.data.rest.firebase.LobbyRequestDTO

interface LobbyRemoteDataSource {
    suspend fun createLobby(request: CreateLobbyRequest): String

    suspend fun fetchLobby(lobbyId: String): LobbyRequestDTO?

    suspend fun fetchUserLobbyIds(userId: String): Map<String, Boolean>?

//    suspend fun joinLobby(
//        lobbyId: String,
//        userId: String
//    ): FirebaseResponse<LobbyResponseDTO>
//
//    suspend fun leaveLobby(
//        lobbyId: String,
//        userId: String
//    ): FirebaseResponse<LobbyResponseDTO>
//
//    suspend fun removeParticipant(
//        lobbyId: String,
//        masterId: String,
//        playerId: String
//    ): FirebaseResponse<LobbyResponseDTO>

}