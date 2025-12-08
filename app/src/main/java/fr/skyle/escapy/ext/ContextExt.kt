package fr.skyle.escapy.ext

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.net.toUri
import fr.skyle.escapy.R

fun Context.navigateToLink(link: String) {
    startActivity(Intent.createChooser(Intent(Intent.ACTION_VIEW, link.toUri()), null))
}

fun Context.navigateToProjectRepository() {
    navigateToLink(getString(R.string.github_project_repository_url))
}

fun Context.navigateToOpenium() {
    navigateToLink(getString(R.string.openium_url))
}

fun Context.navigateToDataPrivacy() {
    navigateToLink(getString(R.string.data_privacy_url))
}

fun Context.launchSettingsActivity() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri: Uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}