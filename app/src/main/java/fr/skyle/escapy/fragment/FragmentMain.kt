package fr.skyle.escapy.fragment

import android.os.Bundle
import fr.skyle.escapy.R
import kotlinx.android.synthetic.main.fragment_main.*


class FragmentMain : AbstractFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_main

    // -------------------------------------------------
    // Life cycle
    // -------------------------------------------------

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListeners()
    }

    // -------------------------------------------------
    // Other methods
    // -------------------------------------------------

    private fun setListeners() {
        linearMainStartGame.setOnClickListener {
            //            startGameActivity()
        }

        linearLayoutMainJoinGame.setOnClickListener {
            //            joinGameActivity()
        }
    }
}