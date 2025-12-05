package fr.skyle.escapy.data.repository.lobby.api

import fr.skyle.escapy.data.repository.lobby.model.CreateLobbyRequest
import fr.skyle.escapy.data.rest.firebase.LobbyRequestDTO
import fr.skyle.escapy.data.utils.model.FirebaseResponse

interface LobbyRemoteDataSource {
    suspend fun createLobby(request: CreateLobbyRequest): FirebaseResponse<Unit>

    suspend fun fetchLobby(lobbyId: String): FirebaseResponse<LobbyRequestDTO>

    suspend fun fetchLobbiesForCurrentUser(
        currentUserId: String
    ): FirebaseResponse<Map<String, LobbyRequestDTO>>

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