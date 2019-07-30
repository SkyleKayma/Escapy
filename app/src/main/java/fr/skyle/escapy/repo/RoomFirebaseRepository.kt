package fr.skyle.escapy.repo

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RoomFirebaseRepository(database: FirebaseDatabase) {

    private val node = "rooms"

    val ref: DatabaseReference = database.reference

    fun queryAllRoom() {

    }

    fun postNewRoom(room: Room): Pair<String?, Task<Void>> {
        val push = ref.child(node).push()
        return push.key to push.setValue(room)
    }

    data class Room(
        var creationDate: Long,
        var roomName: String,
        var password: String?,
        var nbPlayerAllowed: Int,
        var duration: Long,
        var nbPlayersIn: Int,
        var adjustedTime: Long,
        var state: RoomState
    ) {
        enum class RoomState {
            NOT_STARTED,
            STARTED,
            PAUSED,
            ENDED_WITH_LOSE,
            ENDED_WITH_WIN
        }
    }
}