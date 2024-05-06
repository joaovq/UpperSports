package br.com.joaovq.uppersports.core.ext

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.datastore by preferencesDataStore("upper_sports_preferences")