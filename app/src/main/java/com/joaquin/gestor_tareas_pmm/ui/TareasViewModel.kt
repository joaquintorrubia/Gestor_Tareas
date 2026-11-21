package com.joaquin.gestor_tareas_pmm.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.joaquin.gestor_tareas_pmm.data.local.TareasDatabase
import com.joaquin.gestor_tareas_pmm.data.prefs.PreferenciasRepositorio
import com.joaquin.gestor_tareas_pmm.model.Tarea
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TareasViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = TareasDatabase.obtener(application).tareaDao()
    private val preferencias = PreferenciasRepositorio(application)

    val tareas: StateFlow<List<Tarea>> = dao.obtenerTodas().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    val soloPendientes: StateFlow<Boolean> = preferencias.soloPendientes.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        false
    )

    fun agregarTarea(tarea: Tarea) {
        viewModelScope.launch {
            dao.insertar(tarea)
        }
    }

    fun actualizarTarea(tarea: Tarea) {
        viewModelScope.launch {
            dao.actualizar(tarea)
        }
    }

    fun cambiarCompletada(tarea: Tarea) {
        viewModelScope.launch {
            dao.actualizar(tarea.copy(completada = !tarea.completada))
        }
    }

    fun eliminarTarea(tarea: Tarea) {
        viewModelScope.launch {
            dao.eliminar(tarea)
        }
    }

    fun cambiarSoloPendientes(valor: Boolean) {
        viewModelScope.launch {
            preferencias.guardarSoloPendientes(valor)
        }
    }
}