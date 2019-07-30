package fr.skyle.escapy.ui.splash

import android.os.Bundle
import androidx.lifecycle.observe
import fr.skyle.escapy.R
import fr.skyle.escapy.base.AbstractActivity
import fr.skyle.escapy.ext.startActivity
import fr.skyle.escapy.ui.main.ActivityMain
import org.koin.androidx.viewmodel.ext.android.viewModel


class ActivitySplash : AbstractActivity() {

    private val viewModelSplash by viewModel<ViewModelSplash>()

    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelSplash.data.observe(this) {
            startActivityMain()
        }
    }

    // --- OTHER ---
    // ---------------------------------------------------

    private fun startActivityMain() {
        startActivity<ActivityMain>()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}