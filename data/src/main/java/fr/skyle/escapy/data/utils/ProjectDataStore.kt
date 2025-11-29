package fr.skyle.escapy.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import fr.skyle.escapy.data.utils.ProjectDataStore.Companion.DATASTORE_BASE_NAME
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_BASE_NAME)

class ProjectDataStore @Inject constructor(@ApplicationContext context: Context) {

    private val _dataStore = context.dataStore

    suspend fun clear() {
        _dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    // --- Access token

    suspend fun setCurrentUserId(userId: String?) {
        _dataStore.edit { prefs ->
            userId?.let {
                prefs[KEY_CURRENT_USER_ID] = userId
            } ?: prefs.remove(KEY_CURRENT_USER_ID)
        }
    }

    suspend fun getCurrentUserId(): String? =
        _dataStore.data.map { prefs ->
            prefs[KEY_CURRENT_USER_ID]
        }.firstOrNull()

    companion object {
        const val DATASTORE_BASE_NAME = "escapy"

        private val KEY_CURRENT_USER_ID =
            stringPreferencesKey("KEY_CURRENT_USER_ID")
    }
}