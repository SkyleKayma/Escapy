package fr.skyle.escapy.base

import fr.skyle.escapy.R
import fr.skyle.escapy.util.OnBackPressedListener


abstract class AbstractActivityBackstack : AbstractActivity() {

    override fun onBackPressed() {
        if (handleFragmentBackPressed) {
            val currentFragment =
                supportFragmentManager.findFragmentById(R.id.container_framelayout)
            if (currentFragment !is OnBackPressedListener || !currentFragment.onBackPressed()) {
                popupBackstackIfNeeded()
            }
        } else {
            super.onBackPressed()
        }
    }

    private fun popupBackstackIfNeeded() {
        super.onBackPressed()


    }
}