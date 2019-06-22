package fr.skyle.escapy.ext

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Openium on 20/03/2018.
 */

fun getSharedPreferences(context: Context, contextMode: Int): SharedPreferences {
    return context.applicationContext.getSharedPreferences("settings", contextMode)
}

fun SharedPreferences.editBoolean(key: String, value: Boolean) {
    this.edit().putBoolean(key, value).apply()
}

fun SharedPreferences.editInt(key: String, value: Int) {
    this.edit().putInt(key, value).apply()
}

fun SharedPreferences.editLong(key: String, value: Long) {
    this.edit().putLong(key, value).apply()
}

fun SharedPreferences.editFloat(key: String, value: Float) {
    this.edit().putFloat(key, value).apply()
}

fun SharedPreferences.editString(key: String, value: String) {
    this.edit().putString(key, value).apply()
}

fun SharedPreferences.editStringSet(key: String, value: Set<String>) {
    this.edit().putStringSet(key, value).apply()
}

fun SharedPreferences.remove(key: String) {
    this.edit().remove(key).apply()
}

fun Context.getBooleanPreference(
    key: String,
    default: Boolean = false,
    contextMode: Int = Context.MODE_PRIVATE
): Boolean {
    return getSharedPreferences(this, contextMode).getBoolean(key, default)
}

fun Context.setBooleanPreference(
    key: String,
    value: Boolean,
    contextMode: Int = Context.MODE_PRIVATE
) {
    getSharedPreferences(this, contextMode).editBoolean(key, value)
}

fun Context.getIntPreference(
    key: String,
    default: Int,
    contextMode: Int = Context.MODE_PRIVATE
): Int {
    return getSharedPreferences(this, contextMode).getInt(key, default)
}

fun Context.setIntPreference(key: String, value: Int, contextMode: Int = Context.MODE_PRIVATE) {
    getSharedPreferences(this, contextMode).editInt(key, value)
}

fun Context.getLongPreference(
    key: String,
    default: Long,
    contextMode: Int = Context.MODE_PRIVATE
): Long {
    return getSharedPreferences(this, contextMode).getLong(key, default)
}

fun Context.setLongPreference(key: String, value: Long, contextMode: Int = Context.MODE_PRIVATE) {
    getSharedPreferences(this, contextMode).editLong(key, value)
}

fun Context.getFloatPreference(
    key: String,
    default: Float,
    contextMode: Int = Context.MODE_PRIVATE
): Float {
    return getSharedPreferences(this, contextMode).getFloat(key, default)
}

fun Context.setFloatPreference(key: String, value: Float, contextMode: Int = Context.MODE_PRIVATE) {
    getSharedPreferences(this, contextMode).editFloat(key, value)
}

fun Context.getStringPreference(
    key: String,
    default: String = "",
    contextMode: Int = Context.MODE_PRIVATE
): String? {
    return getSharedPreferences(this, contextMode).getString(key, default)
}

fun Context.setStringPreference(
    key: String,
    value: String,
    contextMode: Int = Context.MODE_PRIVATE
) {
    return getSharedPreferences(this, contextMode).editString(key, value)
}

fun Context.getStringSetPreference(
    key: String,
    default: Set<String> = HashSet(),
    contextMode: Int = Context.MODE_PRIVATE
): Set<String>? {
    return getSharedPreferences(this, contextMode).getStringSet(key, default)
}

fun Context.setStringSetPreference(
    key: String,
    value: Set<String>,
    contextMode: Int = Context.MODE_PRIVATE
) {
    getSharedPreferences(this, contextMode).editStringSet(key, value)
}

fun Context.removePreference(key: String, contextMode: Int = Context.MODE_PRIVATE) {
    getSharedPreferences(this, contextMode).remove(key)
}