package br.com.joaovq.uppersports.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import br.com.joaovq.uppersports.core.ext.datastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserLocalDataSource(context: Context) {
    private val userPreferences = context.datastore
    private val isNewUserKey = booleanPreferencesKey("is-new-user")
    val isNewUser: Flow<Boolean>
        get() = userPreferences.data.map { it[isNewUserKey] ?: true }

    suspend fun setIsNewUser(value: Boolean) {
        userPreferences.edit {
            it[isNewUserKey] = value
        }
    }
}