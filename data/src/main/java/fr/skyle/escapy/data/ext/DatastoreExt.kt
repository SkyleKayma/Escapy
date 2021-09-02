package fr.skyle.escapy.data.ext

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import fr.skyle.escapy.data.model.PreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException

suspend fun DataStore<Preferences>.saveAppVersionCode(appVersionCode: Int) {
    edit { preferences ->
        preferences[PreferencesKeys.APP_VERSION_CODE] = appVersionCode
    }
}

fun DataStore<Preferences>.readAppVersionCode(): Flow<Int> =
    data.catch { exception ->
        if (exception is IOException) {
            Timber.d("DataStoreRepository ${exception.message.toString()}")
            emit(emptyPreferences())
        } else throw exception
    }.map { preferences ->
        preferences[PreferencesKeys.APP_VERSION_CODE] ?: 0
    }