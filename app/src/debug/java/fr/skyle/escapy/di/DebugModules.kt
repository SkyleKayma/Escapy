package fr.skyle.escapy.di

import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module


object DebugModules {

    var firebaseModule = module {
        single(override = true) { FirebaseDatabase.getInstance() }
    }

    val serviceModule = module {

    }
}