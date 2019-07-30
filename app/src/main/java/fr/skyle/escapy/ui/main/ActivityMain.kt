package fr.skyle.escapy.ui.main

import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nightonke.boommenu.BoomButtons.OnBMClickListener
import fr.skyle.escapy.R
import fr.skyle.escapy.base.AbstractActivityBackstack
import fr.skyle.escapy.ext.goneWithAnimation
import fr.skyle.escapy.ext.hideWithAnimation
import fr.skyle.escapy.ext.showWithAnimation
import fr.skyle.escapy.ui.about.FragmentAbout
import fr.skyle.escapy.ui.home.FragmentHome
import fr.skyle.escapy.ui.newroom.FragmentNewRoom
import fr.skyle.escapy.util.BoomMenuBuilderUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.atomic.AtomicBoolean


class ActivityMain : AbstractActivityBackstack() {

    private var isFabClickable: AtomicBoolean = AtomicBoolean(true)

    override val layoutId: Int
        get() = R.layout.activity_main

    private val onHide = object : FloatingActionButton.OnVisibilityChangedListener() {
        override fun onHidden(fab: FloatingActionButton?) {
            super.onHidden(fab)

            bottomAppBarMain.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            fabMain.setImageResource(R.drawable.ic_arrow_left)

            fabMain.setOnClickListener {
                if (isFabClickable.get()) {
                    isFabClickable.set(false)
                    onBackPressed()
                }
            }

            //This is needed to prevent multi clicks
            fabMain.postDelayed({
                isFabClickable.set(true)
            }, 250)

            fab?.show()
        }
    }

    private val onShow = object : FloatingActionButton.OnVisibilityChangedListener() {
        override fun onHidden(fab: FloatingActionButton?) {
            super.onHidden(fab)

            bottomAppBarMain.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            fabMain.setImageDrawable(null)
            fabMain.setOnClickListener {
                boomMenuButtonMain.boom()
            }
            showIcons()
            fab?.show()
        }
    }

    // Life cycle
    // -------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set bottom bar as action bar
        setSupportActionBar(bottomAppBarMain)

        //Set all Listeners
        setListeners()

        //Set boom menu display
        setMenu()

        //Set Home fragment with no back stack (As first fragment so)
        startFragment(FragmentHome(), addToBackStack = false, withAnimation = false)
    }

    // Overridden methods
    // -------------------------------------------------

    override fun onBackPressed() {
        super.onBackPressed()

        //Need to toggle each time new fragment is popped
        toggleFabButton()
    }

    // Other methods
    // -------------------------------------------------

    private fun setListeners() {
        imageViewAbout.setOnClickListener {
            goToAbout()
        }
    }

    private fun setMenu() {
        for (i in 0 until boomMenuButtonMain.piecePlaceEnum.pieceNumber()) {
            when (i) {
                0 -> {
                    BoomMenuBuilderUtils.getBuilder(applicationContext,
                        R.drawable.ic_new_game,
                        R.color.green,
                        R.color.green,
                        R.color.green_light,
                        R.string.main_create_room_title,
                        R.string.main_create_room_subtitle,
                        OnBMClickListener {
                            goToNewRoom()
                            boomMenuButtonMain.reboomImmediately()
                        })
                }
                1 -> {
                    BoomMenuBuilderUtils.getBuilder(applicationContext,
                        R.drawable.ic_join_game,
                        R.color.yellow,
                        R.color.yellow,
                        R.color.yellow_light,
                        R.string.main_join_room_title,
                        R.string.main_join_room_subtitle,
                        OnBMClickListener {
                            goToJoinRoom()
                            boomMenuButtonMain.reboomImmediately()
                        })
                }
                2 -> {
                    BoomMenuBuilderUtils.getBuilder(applicationContext,
                        R.drawable.ic_history,
                        R.color.grey_light,
                        R.color.grey_light,
                        R.color.grey,
                        R.string.main_history_title,
                        R.string.main_history_subtitle,
                        OnBMClickListener {
                            goToHistory()
                            boomMenuButtonMain.reboomImmediately()
                        })
                }
                else -> null
            }?.let {
                boomMenuButtonMain.addBuilder(it)
            }
        }
    }

    fun startFragment(childFragment: Fragment, addToBackStack: Boolean, withAnimation: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()

        if (withAnimation) {
            transaction.setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        }

        transaction.add(R.id.frameLayoutContent, childFragment)

        if (addToBackStack) {
            transaction.addToBackStack(childFragment.javaClass.name)
        }

        transaction.commit()
    }

    fun toggleFabButton() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            hideIcons()
            fabMain.hide(onHide)
        } else {
            fabMain.hide(onShow)
        }
    }

    private fun hideIcons() {
        if (!imageViewAbout.isGone || !imageViewSettings.isGone || !boomMenuButtonMain.isInvisible) {
            imageViewAbout.goneWithAnimation()
            imageViewSettings.goneWithAnimation()
            boomMenuButtonMain.hideWithAnimation()
        }
    }

    private fun showIcons() {
        if (!imageViewAbout.isVisible || !imageViewSettings.isVisible || !boomMenuButtonMain.isVisible) {
            imageViewAbout.showWithAnimation()
            imageViewSettings.showWithAnimation()
            boomMenuButtonMain.showWithAnimation()
        }
    }

    private fun goToNewRoom() {
        startFragment(FragmentNewRoom(), addToBackStack = true, withAnimation = true)
    }

    private fun goToJoinRoom() {
//        startFragment(FragmentJoinRoom(), addToBackStack = true, withAnimation = true)
    }

    private fun goToHistory() {
//        startFragment(FragmentHistory(), addToBackStack = true, withAnimation = true)
    }

    private fun goToAbout() {
        startFragment(FragmentAbout(), addToBackStack = true, withAnimation = true)
    }
}