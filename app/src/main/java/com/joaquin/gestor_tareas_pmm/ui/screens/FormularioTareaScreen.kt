package com.joaquin.gestor_tareas_pmm.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joaquin.gestor_tareas_pmm.model.Prioridad
import com.joaquin.gestor_tareas_pmm.model.Tarea
import com.joaquin.gestor_tareas_pmm.ui.theme.Gestor_Tareas_PMMTheme

fun validarTitulo(titulo: String): String? {
    val limpio = titulo.trim()
    if (limpio.isEmpty()) {
        return "El título es obligatorio"
    } else if (limpio.length < 3) {
        return "El título debe tener al menos 3 caracteres"
    } else {
        return null
    }
}

@Composable
fun FormularioTareaScreen(
    tareaInicial: Tarea?,
    onGuardar: (Tarea) -> Unit,
    onCancelar: () -> Unit,
    modifier: Modifier = Modifier
) {

    var titulo by rememberSaveable(tareaInicial?.id) { mutableStateOf(tareaInicial?.titulo ?: "") }
    var descripcion by rememberSaveable(tareaInicial?.id) { mutableStateOf(tareaInicial?.descripcion ?: "") }
    var prioridad by rememberSaveable(tareaInicial?.id) { mutableStateOf(tareaInicial?.prioridad ?: Prioridad.MEDIA) }
    var errorTitulo by rememberSaveable(tareaInicial?.id) { mutableStateOf<String?>(null) }

    var menuAbierto by remember { mutableStateOf(false) }
    var guardando by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = if (tareaInicial == null) "Nueva tarea" else "Editar tarea",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = titulo,
            onValueChange = {
                titulo = it
                errorTitulo = null
            },
            label = { Text("Título") },
            isError = errorTitulo != null,
            supportingText = {
                val error = errorTitulo
                if (error != null) {
                    Text(error)
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción (opcional)") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        Box {
            OutlinedButton(
                onClick = { menuAbierto = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Prioridad: ${prioridad.etiqueta}")
            }
            DropdownMenu(
                expanded = menuAbierto,
                onDismissRequest = { menuAbierto = false }
            ) {
                for (opcion in Prioridad.entries) {
                    DropdownMenuItem(
                        text = { Text(opcion.etiqueta) },
                        onClick = {
                            prioridad = opcion
                            menuAbierto = false
                        }
                    )
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = onCancelar) {
                Text("Cancelar")
            }
            Button(
                enabled = !guardando,
                onClick = {
                    if (!guardando) {
                        val error = validarTitulo(titulo)
                        if (error != null) {
                            errorTitulo = error
                        } else {
                            guardando = true
                            onGuardar(
                                Tarea(
                                    id = tareaInicial?.id ?: 0,
                                    titulo = titulo.trim(),
                                    descripcion = descripcion.trim(),
                                    prioridad = prioridad,
                                    completada = tareaInicial?.completada ?: false
                                )
                            )
                        }
                    }
                }
            ) {
                Text("Guardar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormularioTareaPreview() {
    Gestor_Tareas_PMMTheme {
        FormularioTareaScreen(tareaInicial = null, onGuardar = {}, onCancelar = {})
    }
}