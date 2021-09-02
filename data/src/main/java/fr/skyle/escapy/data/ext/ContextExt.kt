package fr.skyle.escapy.data.ext

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import fr.skyle.escapy.data.DATASTORE_KEY

private val Context.dataStore by preferencesDataStore(DATASTORE_KEY)