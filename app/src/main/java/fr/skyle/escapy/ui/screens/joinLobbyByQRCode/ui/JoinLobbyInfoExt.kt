package fr.skyle.escapy.ui.screens.joinLobbyByQRCode.ui

import fr.skyle.escapy.JOIN_LOBBY_QRCODE_REGEX
import fr.skyle.escapy.ui.screens.joinLobbyByQRCode.ui.model.JoinLobbyQRCodeInfo

fun String.toJoinLobbyQRCodeInfo(): JoinLobbyQRCodeInfo? {
    val regex = JOIN_LOBBY_QRCODE_REGEX.toRegex()
    val match = regex.matchEntire(this) ?: return null

    val (lobbyId, password) = match.destructured
    return JoinLobbyQRCodeInfo(
        lobbyId = lobbyId,
        password = password
    )
}