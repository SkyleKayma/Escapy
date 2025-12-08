package fr.skyle.escapy.data.ext

import fr.skyle.escapy.data.vo.Lobby

fun Lobby.isUserInLobby(userId: String): Boolean =
    createdBy == userId || participants.contains(userId)