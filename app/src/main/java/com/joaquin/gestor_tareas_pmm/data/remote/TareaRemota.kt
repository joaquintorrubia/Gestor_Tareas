package com.joaquin.gestor_tareas_pmm.data.remote

data class TareaRemota(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)