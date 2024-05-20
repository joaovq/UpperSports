package br.com.joaovq.uppersports.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import br.com.joaovq.uppersports.core.ext.datastore
import br.com.joaovq.uppersports.team.domain.model.Team
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy

class UserLocalDataSource(context: Context) {
    private val userPreferences = context.datastore
    private val isNewUserKey = booleanPreferencesKey("is-new-user")
    private val favTeamsKey = stringPreferencesKey("fav-teams")
    val isNewUser: Flow<Boolean>
        get() = userPreferences.data.map { it[isNewUserKey] ?: true }
    val favTeams: Flow<List<Team>?>
        get() {
            return userPreferences.data.map {
                val type = object : TypeToken<List<Team>>() {}.type
                val json = it[favTeamsKey]
                Gson().fromJson(json, type)
            }
        }

    suspend fun setIsNewUser(value: Boolean) {
        userPreferences.edit {
            it[isNewUserKey] = value
        }
    }

    suspend fun setFavTeams(value: List<Team>) {
        userPreferences.edit {
            val type = object : TypeToken<List<Team>>() {}.type
            it[favTeamsKey] = Gson().toJson(type)
        }
    }
}