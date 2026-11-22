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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joaquin.gestor_tareas_pmm.data.remote.TareaRemota
import com.joaquin.gestor_tareas_pmm.ui.EstadoEjemplos
import com.joaquin.gestor_tareas_pmm.ui.theme.Gestor_Tareas_PMMTheme

@Composable
fun EjemplosScreen(
    estado: EstadoEjemplos,
    onCargar: () -> Unit,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Tareas de ejemplo (servidor)", style = MaterialTheme.typography.headlineSmall)
        Text(
            text = "Datos de prueba de JSONPlaceholder. No se guardan en tu lista.",
            style = MaterialTheme.typography.bodyMedium
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = onVolver) {
                Text("Volver")
            }
            Button(
                onClick = onCargar,
                enabled = estado !is EstadoEjemplos.Cargando
            ) {
                Text("Cargar tareas de ejemplo")
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            if (estado is EstadoEjemplos.Inicial) {
                Text(
                    text = "Pulsa el botón para consultar el servidor",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (estado is EstadoEjemplos.Cargando) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (estado is EstadoEjemplos.Exito) {
                if (estado.tareas.isEmpty()) {
                    Text(
                        text = "El servidor no devolvió tareas",
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp)) {
                        items(estado.tareas, key = { it.id }) {
                            FilaTareaRemota(tarea = it)
                            HorizontalDivider()
                        }
                    }
                }
            } else if (estado is EstadoEjemplos.Error) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = estado.mensaje, color = MaterialTheme.colorScheme.error)
                    Button(onClick = onCargar) {
                        Text("Reintentar")
                    }
                }
            }
        }
    }
}

@Composable
fun FilaTareaRemota(tarea: TareaRemota, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = "#${tarea.id}  ${tarea.title}", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = if (tarea.completed) "Completada" else "Pendiente",
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Preview(showBackground = true)
@Composable
fun EjemplosExitoPreview() {
    Gestor_Tareas_PMMTheme {
        EjemplosScreen(
            estado = EstadoEjemplos.Exito(
                listOf(
                    TareaRemota(userId = 1, id = 1, title = "Tarea de ejemplo 1", completed = false),
                    TareaRemota(userId = 1, id = 2, title = "Tarea de ejemplo 2", completed = true)
                )
            ),
            onCargar = {},
            onVolver = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EjemplosErrorPreview() {
    Gestor_Tareas_PMMTheme {
        EjemplosScreen(
            estado = EstadoEjemplos.Error("No se pudo conectar. Comprueba tu conexión a Internet."),
            onCargar = {},
            onVolver = {}
        )
    }
}