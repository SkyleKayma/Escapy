package fr.skyle.escapy.ui.newroom

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import fr.skyle.escapy.ext.hasNetwork
import fr.skyle.escapy.repo.RoomFirebaseRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.koin.core.KoinComponent
import org.koin.core.inject


class ViewModelNewRoom(val app: Application) : AndroidViewModel(app), KoinComponent {

    private val roomRepo: RoomFirebaseRepository by inject()

    //Success part
    private val success = MutableLiveData<Success>()
    val successObs: LiveData<Success>
        get() = success

    data class Success(val id: String? = null, val type: SUCCESS)
    enum class SUCCESS {
        NEW_ROOM_CREATED
    }

    //Error part
    private val error = MutableLiveData<ERROR>()
    val errorObs: LiveData<ERROR>
        get() = error

    enum class ERROR {
        NO_INTERNET,
        NEW_ROOM_CREATION_ERROR
    }

    fun postNewRoom(creationDate: Long, roomName: String, password: String?, nbPlayerAllowed: Int, duration: Long) {
        if (app.applicationContext.hasNetwork) {
            viewModelScope.launch {
                val room = RoomFirebaseRepository.Room(
                    creationDate,
                    roomName,
                    password,
                    nbPlayerAllowed,
                    duration,
                    0,
                    0,
                    RoomFirebaseRepository.Room.RoomState.NOT_STARTED
                )

                val res = roomRepo.postNewRoom(room)
                try {
                    res.second.await()
                    success.value = Success(res.first, SUCCESS.NEW_ROOM_CREATED)
                } catch (e: Exception) {
                    error.value = ERROR.NEW_ROOM_CREATION_ERROR
                }
            }
        } else {
            error.value = ERROR.NO_INTERNET
        }
    }
}