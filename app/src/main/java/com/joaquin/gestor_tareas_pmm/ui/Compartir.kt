package com.joaquin.gestor_tareas_pmm.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.joaquin.gestor_tareas_pmm.model.Tarea

fun compartirTarea(context: Context, tarea: Tarea) {

    var texto = "Tarea: ${tarea.titulo}\n"
    if (tarea.descripcion.isNotBlank()) {
        texto += "Descripción: ${tarea.descripcion}\n"
    }
    texto += "Prioridad: ${tarea.prioridad.etiqueta}"

    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, texto)

    val selector = Intent.createChooser(intent, "Compartir tarea")

    try {
        context.startActivity(selector)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No hay aplicaciones para compartir", Toast.LENGTH_SHORT).show()
    }
}