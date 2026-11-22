package com.joaquin.gestor_tareas_pmm.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun TarjetaTarea(
    tarea: Tarea,
    onCompletadaChange: () -> Unit,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = tarea.completada,
                onCheckedChange = { onCompletadaChange() }
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = tarea.titulo, style = MaterialTheme.typography.titleMedium)
                Text(text = "Prioridad: ${tarea.prioridad.etiqueta}")
                Text(text = if (tarea.completada) "Completada" else "Pendiente")
            }
            IconButton(onClick = onEditar) {
                Icon(
                    painter = painterResource(R.drawable.ic_editar),
                    contentDescription = "Editar tarea"
                )
            }
            IconButton(onClick = onEliminar) {
                Icon(
                    painter = painterResource(R.drawable.ic_eliminar),
                    contentDescription = "Eliminar tarea"
                )
            }
        }
    }
}

@Composable
fun ListaTareasScreen(
    tareas: List<Tarea>,
    soloPendientes: Boolean,
    onSoloPendientesChange: (Boolean) -> Unit,
    onAnadirClick: () -> Unit,
    onCompletadaChange: (Tarea) -> Unit,
    onEditar: (Tarea) -> Unit,
    onEliminar: (Tarea) -> Unit,
    onEjemplosClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    var tareaAEliminar by remember { mutableStateOf<Tarea?>(null) }

    Box(modifier = modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mis tareas",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onEjemplosClick) {
                    Text("Tareas de ejemplo")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Mostrar solo pendientes", modifier = Modifier.weight(1f))
                Switch(checked = soloPendientes, onCheckedChange = onSoloPendientesChange)
            }

            Box(modifier = Modifier.weight(1f)) {
                if (tareas.isEmpty()) {
                    Text(
                        text = if (soloPendientes) "No hay tareas pendientes" else "No hay tareas. ¡Añade la primera!",
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
                            val tarea = it
                            TarjetaTarea(
                                tarea = tarea,
                                onCompletadaChange = { onCompletadaChange(tarea) },
                                onEditar = { onEditar(tarea) },
                                onEliminar = { tareaAEliminar = tarea }
                            )
                        }
                    }
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

        val tarea = tareaAEliminar
        if (tarea != null) {
            AlertDialog(
                onDismissRequest = { tareaAEliminar = null },
                title = { Text("Eliminar tarea") },
                text = { Text("¿Seguro que quieres eliminar \"${tarea.titulo}\"?") },
                confirmButton = {
                    TextButton(onClick = {
                        onEliminar(tarea)
                        tareaAEliminar = null
                    }) { Text("Eliminar") }
                },
                dismissButton = {
                    TextButton(onClick = { tareaAEliminar = null }) { Text("Cancelar") }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TarjetaTareaPreview() {
    Gestor_Tareas_PMMTheme {
        TarjetaTarea(
            tarea = Tarea(id = 1, titulo = "Estudiar para el examen", prioridad = Prioridad.ALTA),
            onCompletadaChange = {},
            onEditar = {},
            onEliminar = {}
        )
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
            soloPendientes = false,
            onSoloPendientesChange = {},
            onAnadirClick = {},
            onCompletadaChange = {},
            onEditar = {},
            onEliminar = {},
            onEjemplosClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListaTareasVaciaPreview() {
    Gestor_Tareas_PMMTheme {
        ListaTareasScreen(
            tareas = emptyList(),
            soloPendientes = false,
            onSoloPendientesChange = {},
            onAnadirClick = {},
            onCompletadaChange = {},
            onEditar = {},
            onEliminar = {},
            onEjemplosClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListaTareasFiltroSinPendientesPreview() {
    Gestor_Tareas_PMMTheme {
        ListaTareasScreen(
            tareas = emptyList(),
            soloPendientes = true,
            onSoloPendientesChange = {},
            onAnadirClick = {},
            onCompletadaChange = {},
            onEditar = {},
            onEliminar = {},
            onEjemplosClick = {}
        )
    }
}