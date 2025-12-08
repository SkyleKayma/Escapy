package fr.skyle.escapy.data.repository.lobby.api

import fr.skyle.escapy.data.repository.lobby.model.CreateLobbyRequest
import fr.skyle.escapy.data.rest.firebase.LobbyRequestDTO
import fr.skyle.escapy.data.utils.model.FirebaseResponse

interface LobbyRemoteDataSource {
    suspend fun createLobby(request: CreateLobbyRequest): FirebaseResponse<String>

    suspend fun fetchLobby(lobbyId: String): FirebaseResponse<LobbyRequestDTO>

    suspend fun fetchLobbies(
        lobbyIds: List<String>
    ): FirebaseResponse<Map<String, LobbyRequestDTO>>

    suspend fun fetchUserLobbyIds(userId: String): FirebaseResponse<Map<String, Boolean>>

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