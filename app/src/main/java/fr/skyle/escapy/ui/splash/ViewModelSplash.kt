package fr.skyle.escapy.ui.splash

import com.google.firebase.database.DatabaseReference
import fr.skyle.escapy.base.AbstractViewModel
import java.util.concurrent.TimeUnit


class ViewModelSplash : AbstractViewModel() {

    private val ref: DatabaseReference = database.getReference("")

//    private val data = MutableLiveData<>()
//    val dataObs: LiveData<Pair<Long, Int>>
//        get() = data
//
//    private val error = MutableLiveData<ERROR>()
//    val errorObs: LiveData<ERROR>
//        get() = error
//
//    enum class ERROR {
//        CANT_GET_DATA
//    }

    init {
//        viewModelScope.launch {
//            val delay = async { delay(MINIMUM_SECONDS_TO_WAIT) }
//            val task =
//                async { FirebaseDatabase.getInstance().getReference("").setValue("test").await() }
//            awaitAll(delay, task)
//        }
    }

//    fun query() {
//        FirebaseDatabase.getInstance().getReference("")
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onCancelled(p0: DatabaseError) {
//                    error.value = ERROR.CANT_GET_DATA
//                }
//
//                override fun onDataChange(p0: DataSnapshot) {
//                    booleanMLD.value = 2L to 2
//                }
//            })
//    }
//
//    fun queryAllGame() = gameRepo.queryAllGame()

    companion object {
        val MINIMUM_SECONDS_TO_WAIT = TimeUnit.SECONDS.toMillis(2L)
    }
}