package fr.skyle.escapy.activity

import androidx.fragment.app.Fragment
import fr.skyle.escapy.R
import fr.skyle.escapy.fragment.FragmentMain


class ActivityMain : AbstractActivityFragment() {

    override val layoutId: Int
        get() = R.layout.container_no_toolbar

    override fun getDefaultFragment(): Fragment? {
        return FragmentMain()
    }
}