package com.joaquin.gestor_tareas_pmm.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "preferencias")

class PreferenciasRepositorio(private val context: Context) {

    private val CLAVE_SOLO_PENDIENTES = booleanPreferencesKey("solo_pendientes")

    val soloPendientes: Flow<Boolean> = context.dataStore.data.map {
        it[CLAVE_SOLO_PENDIENTES] ?: false
    }

    suspend fun guardarSoloPendientes(valor: Boolean) {
        context.dataStore.edit {
            it[CLAVE_SOLO_PENDIENTES] = valor
        }
    }
}