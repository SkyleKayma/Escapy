package fr.skyle.escapy.ui.about

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import fr.skyle.escapy.R
import fr.skyle.escapy.base.AbstractFragment
import fr.skyle.escapy.ext.textTrimmed
import fr.skyle.escapy.ui.main.ActivityMain
import kotlinx.android.synthetic.main.fragment_about.*
import timber.log.Timber


class FragmentAbout : AbstractFragment() {

    override val layoutId: Int
        get() = R.layout.fragment_about

    // -------------------------------------------------
    // Life cycle
    // -------------------------------------------------

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setVersionNumber()
        setRepoListener()
    }

    override fun onResume() {
        super.onResume()

        //Need to toggle each time new fragment is pushed
        (activity as? ActivityMain)?.toggleFabButton()
    }

    // -------------------------------------------------
    // Other methods
    // -------------------------------------------------

    private fun setVersionNumber() {
        var pInfo: PackageInfo? = null
        try {
            pInfo = requireActivity().packageManager.getPackageInfo(requireActivity().packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.e(e)
        }
        textViewAboutVersion.text = getString(R.string.about_version_format, pInfo?.versionName, pInfo?.versionCode)
    }

    private fun setRepoListener() {
        textViewAboutRepo.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(textViewAboutRepo.textTrimmed())))
        }
    }
}