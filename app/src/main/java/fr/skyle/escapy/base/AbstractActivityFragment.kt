package fr.skyle.escapy.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import fr.skyle.escapy.R


abstract class AbstractActivityFragment : AbstractActivity() {
    protected var fragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addDefaultFragment()
    }

    protected open fun addDefaultFragment() {
        onPreAddFragment()
        val fragmentManager = supportFragmentManager
        fragment = fragmentManager.findFragmentById(containerId)
        if (fragment == null) {
            fragment = getDefaultFragment()
            fragment?.arguments = intent?.extras
            fragmentManager.beginTransaction().replace(containerId, fragment!!).commit()
        }
    }

    protected open fun onPreAddFragment() {}

    protected abstract fun getDefaultFragment(): Fragment?

    // Return view ID that contains fragment
    protected open val containerId: Int = R.id.container_framelayout
}