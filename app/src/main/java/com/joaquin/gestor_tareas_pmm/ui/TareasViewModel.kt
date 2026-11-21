package com.joaquin.gestor_tareas_pmm.ui

import androidx.lifecycle.ViewModel
import com.joaquin.gestor_tareas_pmm.model.Prioridad
import com.joaquin.gestor_tareas_pmm.model.Tarea
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TareasViewModel : ViewModel() {

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
}