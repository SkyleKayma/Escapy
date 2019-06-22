package fr.skyle.escapy.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import fr.skyle.escapy.R
import fr.skyle.escapy.util.OnBackPressedListener
import kotlinx.android.synthetic.main.toolbar.*


abstract class AbstractActivity : AppCompatActivity() {

    protected open val handleFragmentBackPressed: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        toolbar?.let {
            setSupportActionBar(toolbar)
        }

        setHomeAsUp(showHomeAsUp)
    }

    protected fun setHomeAsUp(enabled: Boolean = true) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(enabled)
            setHomeButtonEnabled(enabled)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onArrowPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    open fun onArrowPressed() {
        onBackPressed()
    }

    override fun onBackPressed() {
        if (handleFragmentBackPressed) {
            val currentFragment =
                supportFragmentManager.findFragmentById(R.id.container_framelayout)
            if (currentFragment !is OnBackPressedListener || !currentFragment.onBackPressed()) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

    // If true, back arrow is shown
    protected open val showHomeAsUp: Boolean = false

    // Return layout associated to this activity
    protected open val layoutId: Int = R.layout.container_toolbar
}