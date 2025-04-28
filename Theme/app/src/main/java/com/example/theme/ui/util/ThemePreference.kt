package com.example.themeselector.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("theme_pref")

object ThemePreference {
    private val THEME_KEY = stringPreferencesKey("theme_key")

    suspend fun saveTheme(context: Context, theme: AppTheme) {
        context.dataStore.edit { preferences ->
            preferences[THEME_KEY] = theme.name
        }
    }

    suspend fun getTheme(context: Context): AppTheme {
        val themeName = context.dataStore.data
            .map { it[THEME_KEY] ?: AppTheme.BLUE.name }
            .first()
        return AppTheme.valueOf(themeName)
    }
}
