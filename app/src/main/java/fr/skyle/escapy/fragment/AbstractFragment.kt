package fr.skyle.escapy.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable


abstract class AbstractFragment : Fragment() {

    protected val disposables: CompositeDisposable = CompositeDisposable()
    protected val rebindDisposables: CompositeDisposable = CompositeDisposable()

    protected abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onStart() {
        super.onStart()

        startSubscription(rebindDisposables)
    }

    override fun onStop() {
        super.onStop()
        rebindDisposables.clear()
    }

    protected open fun startSubscription(onStartDisposables: CompositeDisposable) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    protected fun showMessage(text: Int, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(view!!, text, duration).show()
    }

    protected fun showMessage(text: String, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(view!!, text, duration).show()
    }
}