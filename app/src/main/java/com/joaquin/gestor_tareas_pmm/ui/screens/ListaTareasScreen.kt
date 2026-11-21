package com.joaquin.gestor_tareas_pmm.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joaquin.gestor_tareas_pmm.R
import com.joaquin.gestor_tareas_pmm.model.Prioridad
import com.joaquin.gestor_tareas_pmm.model.Tarea
import com.joaquin.gestor_tareas_pmm.ui.theme.Gestor_Tareas_PMMTheme

@Composable
fun TarjetaTarea(tarea: Tarea, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = tarea.titulo, style = MaterialTheme.typography.titleMedium)
            Text(text = "Prioridad: ${tarea.prioridad.etiqueta}")
            Text(text = if (tarea.completada) "Completada" else "Pendiente")
        }
    }
}

@Composable
fun ListaTareasScreen(
    tareas: List<Tarea>,
    onAnadirClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {

        if (tareas.isEmpty()) {
            Text(
                text = "No hay tareas. ¡Añade la primera!",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tareas, key = { it.id }) {
                    TarjetaTarea(tarea = it)
                }
            }
        }

        FloatingActionButton(
            onClick = onAnadirClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_anadir),
                contentDescription = "Añadir tarea"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListaTareasConDatosPreview() {
    Gestor_Tareas_PMMTheme {
        ListaTareasScreen(
            tareas = listOf(
                Tarea(id = 1, titulo = "Estudiar para el examen", prioridad = Prioridad.ALTA),
                Tarea(id = 2, titulo = "Preparar la reunión", prioridad = Prioridad.MEDIA),
                Tarea(id = 3, titulo = "Entregar la memoria", prioridad = Prioridad.BAJA, completada = true)
            ),
            onAnadirClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListaTareasVaciaPreview() {
    Gestor_Tareas_PMMTheme {
        ListaTareasScreen(tareas = emptyList(), onAnadirClick = {})
    }
}