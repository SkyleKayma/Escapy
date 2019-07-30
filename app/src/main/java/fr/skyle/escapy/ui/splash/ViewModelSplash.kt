package fr.skyle.escapy.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


class ViewModelSplash : ViewModel() {

    val data = liveData {
        delay(MINIMUM_SECONDS_TO_WAIT)
        emit(Unit)
    }

    companion object {
        val MINIMUM_SECONDS_TO_WAIT = TimeUnit.SECONDS.toMillis(2L)
    }
}