package fr.skyle.escapy.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import fr.skyle.escapy.data.repository.DataStoreRepository.PreferencesKeys.APP_VERSION_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException

class DataStoreRepository(private val dataStore: DataStore<Preferences>) {

    suspend fun saveAppVersionId(appVersionId: String) {
        dataStore.edit { preference ->
            preference[APP_VERSION_ID] = appVersionId
        }
    }

    val readAppVersionId: Flow<String> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    Timber.d("DataStoreRepository ${exception.message.toString()}")
                    emit(emptyPreferences())
                } else throw exception
            }
            .map { preference ->
                preference[APP_VERSION_ID] ?: ""
            }

    private object PreferencesKeys {
        val APP_VERSION_ID = stringPreferencesKey("appVersionId")
    }
}