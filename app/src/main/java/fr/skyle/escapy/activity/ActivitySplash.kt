package fr.skyle.escapy.activity

import android.os.Bundle
import fr.openium.kotlintools.ext.startActivity
import fr.skyle.escapy.R
import fr.skyle.escapy.viewmodel.ViewModelSplash
import org.koin.androidx.viewmodel.ext.android.viewModel


class ActivitySplash : AbstractActivity() {

    private val viewModelSplash by viewModel<ViewModelSplash>()

    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get new data
//        viewModelSplash.getData().subscribe({
//            startActivityMain()
//        }, {
//            Timber.e(it)
//        }).addTo(disposables)

        startActivityMain()
    }

    // --- OTHER ---
    // ---------------------------------------------------

    private fun startActivityMain() {
        startActivity<ActivityMain>()
        finish()
    }
}