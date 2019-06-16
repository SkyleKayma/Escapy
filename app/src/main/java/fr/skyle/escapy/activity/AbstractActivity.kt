package fr.skyle.escapy.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import fr.skyle.escapy.R
import fr.skyle.escapy.fragment.OnBackPressedListener
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.toolbar.*


abstract class AbstractActivity : AppCompatActivity() {

    protected val disposables: CompositeDisposable = CompositeDisposable()
    protected var rebindDisposables: CompositeDisposable = CompositeDisposable()

    protected open val handleFragmentBackPressed: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        toolbar?.let {
            setSupportActionBar(toolbar)
        }

        setHomeAsUp(showHomeAsUp)
    }

    override fun onStart() {
        super.onStart()
        startDisposable(rebindDisposables)
    }

    override fun onStop() {
        super.onStop()
        rebindDisposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    protected fun setHomeAsUp(enabled: Boolean = true) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(enabled)
            setHomeButtonEnabled(enabled)
        }
    }

    open fun startDisposable(onStartDisposables: CompositeDisposable) {}

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

    protected fun showMessage(text: Int, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(window.decorView.rootView, text, duration).show()
    }

    protected fun showMessage(text: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(window.decorView.rootView, text, duration).show()
    }

    // If true, back arrow is shown
    protected open val showHomeAsUp: Boolean = false

    // Return layout associated to this activity
    protected open val layoutId: Int = R.layout.container_toolbar
}