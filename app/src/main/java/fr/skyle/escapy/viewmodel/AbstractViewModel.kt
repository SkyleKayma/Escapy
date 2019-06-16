package fr.skyle.escapy.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.FirebaseDatabase
import fr.skyle.escapy.CustomApplication
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject


abstract class AbstractViewModel(application: Application) : AndroidViewModel(application),
    KoinComponent {

    protected val database: FirebaseDatabase by inject()

    protected var disposables: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    protected val context: Context
        get() = getApplication<CustomApplication>().applicationContext
}