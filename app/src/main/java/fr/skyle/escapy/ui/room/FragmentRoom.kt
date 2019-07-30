package fr.skyle.escapy.ui.room

import android.os.Bundle
import fr.skyle.escapy.R
import fr.skyle.escapy.base.AbstractFragment


class FragmentRoom : AbstractFragment() {

    private var roomId: String? = null

    override val layoutId: Int
        get() = R.layout.fragment_room

    // -------------------------------------------------
    // Life cycle
    // -------------------------------------------------

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Get roomID
        roomId = arguments?.getString(ROOM_ID)
    }

    // -------------------------------------------------
    // Specific job
    // -------------------------------------------------

    fun getFragment(): FragmentRoom {

    }
}