package fr.skyle.escapy.ui.main

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nightonke.boommenu.BoomButtons.HamButton
import com.nightonke.boommenu.BoomButtons.OnBMClickListener
import com.nightonke.boommenu.Util
import fr.skyle.escapy.R
import fr.skyle.escapy.base.AbstractActivityBackstack
import fr.skyle.escapy.ext.goneWithAnimation
import fr.skyle.escapy.ext.hideWithAnimation
import fr.skyle.escapy.ext.showWithAnimation
import fr.skyle.escapy.ui.about.FragmentAbout
import fr.skyle.escapy.ui.home.FragmentHome
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean


class ActivityMain : AbstractActivityBackstack() {

    private var fabCanBeClicked: AtomicBoolean = AtomicBoolean(true)

    override val layoutId: Int
        get() = R.layout.activity_main

    // Life cycle
    // -------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Set bottom bar as action bar
        setSupportActionBar(bottomAppBarMain)

        setMenu()
        setListeners()

        startFragment(FragmentHome(), false, false)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        //Need to toggle each time new fragment is popped
        toggleFabButton()
    }

    // Useful methods
    // -------------------------------------------------

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

    // Other methods
    // -------------------------------------------------

    private fun setMenu() {
        for (i in 0 until boomMenuButtonMain.piecePlaceEnum.pieceNumber()) {
            var builder: HamButton.Builder? = null
            when (i) {
                0 -> {
                    builder = getBuilder(
                        R.drawable.ic_new_game,
                        R.color.yellow,
                        R.color.yellow,
                        R.color.yellow_light,
                        "Créer une partie",
                        "Allez-y ! Montrez la voie",
                        OnBMClickListener {
                            goToHistory()
                            boomMenuButtonMain.reboomImmediately()
                        })
                }
                1 -> {
                    builder = getBuilder(
                        R.drawable.ic_join_game,
                        R.color.green,
                        R.color.green,
                        R.color.green_light,
                        "Rejoindre une partie",
                        "Laissez-vous guider durant l'aventure",
                        OnBMClickListener {
                            goToSettings()
                            boomMenuButtonMain.reboomImmediately()
                        })
                }
                2 -> {
                    builder = getBuilder(
                        R.drawable.ic_history,
                        R.color.grey_light,
                        R.color.grey_light,
                        R.color.grey,
                        "Historique",
                        "Finissez-vous souvent dans les temps ?",
                        OnBMClickListener {
                            goToAbout()
                            boomMenuButtonMain.reboomImmediately()
                        })
                }
            }
            if (builder != null) {
                boomMenuButtonMain.addBuilder(builder)
            }
        }
    }

    private fun getBuilder(
        imageId: Int,
        pieceColorRes: Int,
        normalColorRes: Int,
        highlightedColorRes: Int,
        normalText: String,
        subNormalText: String,
        listener: OnBMClickListener
    ): HamButton.Builder {
        return HamButton.Builder()
            .normalImageDrawable(applicationContext.getDrawable(imageId))
            .rotateImage(false)
            .imagePadding(Rect(Util.dp2px(20f), Util.dp2px(20f), Util.dp2px(20f), Util.dp2px(20f)))
            .pieceColorRes(pieceColorRes)
            .normalColorRes(normalColorRes)
            .highlightedColorRes(highlightedColorRes)
            .normalText(normalText)
            .normalTextColorRes(R.color.blue_grey_dark)
            .subNormalText(subNormalText)
            .subNormalTextColorRes(R.color.blue_grey_dark)
            .typeface(ResourcesCompat.getFont(applicationContext, R.font.exo2_regular))
            .subTypeface(ResourcesCompat.getFont(applicationContext, R.font.exo2_regular))
            .listener(listener)
    }

    private fun setListeners() {
//        linearMainStartGame.setOnClickListener {
        //            startGameActivity()
//        }

//        linearLayoutMainJoinGame.setOnClickListener {
        //            joinGameActivity()
//        }

        imageViewAbout.setOnClickListener {
            goToAbout()
        }
    }

    private fun goToHistory() {

    }

    private fun goToSettings() {

    }

    private fun goToAbout() {
        startFragment(FragmentAbout(), true, true)
    }

    fun toggleFabButton() {
        Timber.d("Backstack count = ${supportFragmentManager.backStackEntryCount}")
        if (supportFragmentManager.backStackEntryCount > 0) {
            hideIcons()

            fabMain.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
                override fun onHidden(fab: FloatingActionButton?) {
                    super.onHidden(fab)

                    bottomAppBarMain.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    fabMain.setImageResource(R.drawable.ic_arrow_left)

                    fabMain.setOnClickListener {
                        if (fabCanBeClicked.get()) {
                            fabCanBeClicked.set(false)
                            onBackPressed()
                        }
                    }

                    //This is needed to prevent multi clicks
                    fabMain.postDelayed({
                        fabCanBeClicked.set(true)
                    }, 250)

                    fab?.show()
                }
            })
        } else {
            fabMain.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
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
            })
        }
    }

    private fun hideIcons() {
        if (imageViewAbout.visibility != View.GONE
            || imageViewSettings.visibility != View.GONE
            || boomMenuButtonMain.visibility != View.INVISIBLE
        ) {
            imageViewAbout.goneWithAnimation()
            imageViewSettings.goneWithAnimation()
            boomMenuButtonMain.hideWithAnimation()
        }
    }

    private fun showIcons() {
        if (imageViewAbout.visibility != View.VISIBLE
            || imageViewSettings.visibility != View.VISIBLE
            || boomMenuButtonMain.visibility != View.VISIBLE
        ) {
            imageViewAbout.showWithAnimation()
            imageViewSettings.showWithAnimation()
            boomMenuButtonMain.showWithAnimation()
        }
    }
}