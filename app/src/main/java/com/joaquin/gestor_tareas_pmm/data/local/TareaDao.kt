package com.joaquin.gestor_tareas_pmm.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.joaquin.gestor_tareas_pmm.model.Tarea
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {

    // Las más nuevas arriba (id más alto primero)
    @Query("SELECT * FROM tareas ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<Tarea>>

    @Insert
    suspend fun insertar(tarea: Tarea)

    @Update
    suspend fun actualizar(tarea: Tarea)

    @Delete
    suspend fun eliminar(tarea: Tarea)
}