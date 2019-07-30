package fr.skyle.escapy.di

import com.google.firebase.database.FirebaseDatabase
import fr.skyle.escapy.repo.RoomFirebaseRepository
import fr.skyle.escapy.ui.newroom.ViewModelNewRoom
import fr.skyle.escapy.ui.splash.ViewModelSplash
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object Modules {

    var firebaseModule = module {
        single { FirebaseDatabase.getInstance() }
        single { RoomFirebaseRepository(get()) }
    }

    val serviceModule = module {

    }

    val viewModelModule = module {
        viewModel { ViewModelSplash() }
        viewModel { ViewModelNewRoom() }
    }
}
