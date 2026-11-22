package com.joaquin.gestor_tareas_pmm.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface TareasApi {

    @GET("todos")
    suspend fun obtenerTareas(@Query("userId") userId: Int): List<TareaRemota>
}