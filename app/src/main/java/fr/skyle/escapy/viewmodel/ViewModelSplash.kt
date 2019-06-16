package fr.skyle.escapy.viewmodel

import android.app.Application
import com.google.firebase.database.DatabaseReference


class ViewModelSplash(app: Application) : AbstractViewModel(app) {

    private val ref: DatabaseReference = database.getReference("")

    companion object {
        const val MINIMUM_SECONDS_TO_WAIT = 2L
    }

    //get all data needed on splash
//    fun getData(): Flowable<Pair<Long, Int>> {
//        return Flowable.combineLatest(Flowable.timer(MINIMUM_SECONDS_TO_WAIT, TimeUnit.SECONDS),
//            RxFirebaseDatabase.observeValueEvent(ref.child("")) {
//                2
//            }, BiFunction { timer: Long, int: Int ->
//                timer to int
//            }
//        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//    }
}