package fr.skyle.escapy.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.skyle.escapy.ui.main.ActivityMain


abstract class AbstractFragment : Fragment() {

    protected abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onResume() {
        super.onResume()

        activity?.let {
            if (it is ActivityMain) {
                //Need to toggle each time new fragment is pushed
                it.toggleFabButton()
            }
        }
    }
}