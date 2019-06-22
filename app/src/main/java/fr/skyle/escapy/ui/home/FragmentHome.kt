package fr.skyle.escapy.ui.home

import android.os.Bundle
import fr.skyle.escapy.R
import fr.skyle.escapy.base.AbstractFragment
import fr.skyle.escapy.ui.main.ActivityMain


class FragmentHome : AbstractFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_home

    // -------------------------------------------------
    // Life cycle
    // -------------------------------------------------

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        //Need to toggle each time new fragment is pushed
        (activity as? ActivityMain)?.toggleFabButton()
    }
}