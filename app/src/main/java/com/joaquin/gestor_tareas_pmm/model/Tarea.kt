package com.joaquin.gestor_tareas_pmm.model


enum class Prioridad(val etiqueta: String) {
    BAJA("Baja"),
    MEDIA("Media"),
    ALTA("Alta")
}

data class Tarea(
    val id: Int = 0,
    val titulo: String,
    val descripcion: String = "",
    val prioridad: Prioridad = Prioridad.MEDIA,
    val completada: Boolean = false
)

