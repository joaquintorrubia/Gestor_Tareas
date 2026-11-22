package com.joaquin.gestor_tareas_pmm.ui

import com.joaquin.gestor_tareas_pmm.data.remote.TareaRemota

sealed interface EstadoEjemplos {
    data object Inicial : EstadoEjemplos
    data object Cargando : EstadoEjemplos
    data class Exito(val tareas: List<TareaRemota>) : EstadoEjemplos
    data class Error(val mensaje: String) : EstadoEjemplos
}