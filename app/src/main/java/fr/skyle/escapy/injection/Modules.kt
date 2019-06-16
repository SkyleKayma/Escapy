package fr.skyle.escapy.injection

import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module


object Modules {

    var firebaseModule = module {
        single { FirebaseDatabase.getInstance() }
    }

    val serviceModule = module {

    }
}
