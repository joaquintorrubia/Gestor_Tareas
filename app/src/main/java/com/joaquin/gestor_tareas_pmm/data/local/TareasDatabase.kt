package com.joaquin.gestor_tareas_pmm.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.joaquin.gestor_tareas_pmm.model.Tarea

@Database(entities = [Tarea::class], version = 1, exportSchema = false)
abstract class TareasDatabase : RoomDatabase() {

    abstract fun tareaDao(): TareaDao

    companion object {
        @Volatile
        private var instancia: TareasDatabase? = null

        fun obtener(context: Context): TareasDatabase {
            return instancia ?: synchronized(this) {
                val existente = instancia
                if (existente != null) {
                    existente
                } else {
                    val nueva = Room.databaseBuilder(
                        context.applicationContext,
                        TareasDatabase::class.java,
                        "tareas.db"
                    ).build()
                    instancia = nueva
                    nueva
                }
            }
        }
    }
}