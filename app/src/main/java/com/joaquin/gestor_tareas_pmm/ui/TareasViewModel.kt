package com.joaquin.gestor_tareas_pmm.ui

import androidx.lifecycle.ViewModel
import com.joaquin.gestor_tareas_pmm.model.Prioridad
import com.joaquin.gestor_tareas_pmm.model.Tarea
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TareasViewModel : ViewModel() {

    private var siguienteId = 4

    private val _tareas = MutableStateFlow(
        listOf(
            Tarea(
                id = 1,
                titulo = "Estudiar para el examen de PMDM",
                descripcion = "Repasar Room, DataStore y Retrofit",
                prioridad = Prioridad.ALTA
            ),
            Tarea(
                id = 2,
                titulo = "Preparar la reunión de equipo",
                descripcion = "Revisar el orden del día y los informes",
                prioridad = Prioridad.MEDIA
            ),
            Tarea(
                id = 3,
                titulo = "Entregar la memoria del proyecto",
                descripcion = "Subir el PDF a la plataforma",
                prioridad = Prioridad.BAJA,
                completada = true
            )
        )
    )
    val tareas: StateFlow<List<Tarea>> = _tareas.asStateFlow()

    fun agregarTarea(tarea: Tarea) {
        val nueva = tarea.copy(id = siguienteId)
        siguienteId++
        _tareas.value = _tareas.value + nueva
    }

    fun actualizarTarea(tarea: Tarea) {
        _tareas.value = _tareas.value.map {
            if (it.id == tarea.id) tarea else it
        }
    }

    fun cambiarCompletada(tarea: Tarea) {
        _tareas.value = _tareas.value.map {
            if (it.id == tarea.id) it.copy(completada = !it.completada) else it
        }
    }

    fun eliminarTarea(tarea: Tarea) {
        _tareas.value = _tareas.value.filter { it.id != tarea.id }
    }
}