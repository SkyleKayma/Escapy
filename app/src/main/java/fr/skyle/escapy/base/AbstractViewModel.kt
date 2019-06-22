package fr.skyle.escapy.base

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import org.koin.core.KoinComponent
import org.koin.core.inject


abstract class AbstractViewModel : ViewModel(), KoinComponent {

    protected val database: FirebaseDatabase by inject()
}