package com.joaquin.gestor_tareas_pmm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joaquin.gestor_tareas_pmm.ui.EjemplosViewModel
import com.joaquin.gestor_tareas_pmm.ui.Pantalla
import com.joaquin.gestor_tareas_pmm.ui.TareasViewModel
import com.joaquin.gestor_tareas_pmm.ui.screens.EjemplosScreen
import com.joaquin.gestor_tareas_pmm.ui.screens.FormularioTareaScreen
import com.joaquin.gestor_tareas_pmm.ui.screens.ListaTareasScreen
import com.joaquin.gestor_tareas_pmm.ui.theme.Gestor_Tareas_PMMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: TareasViewModel = viewModel()
            val tareas by viewModel.tareas.collectAsState()
            val soloPendientes by viewModel.soloPendientes.collectAsState()
            val ejemplosViewModel: EjemplosViewModel = viewModel()
            val estadoEjemplos by ejemplosViewModel.estado.collectAsState()
            val tareasVisibles = if (soloPendientes) tareas.filter { !it.completada } else tareas
            var pantallaActual by rememberSaveable { mutableStateOf(Pantalla.LISTA) }
            var tareaEditandoId by rememberSaveable { mutableStateOf<Int?>(null) }

            BackHandler(enabled = pantallaActual != Pantalla.LISTA) {
                pantallaActual = Pantalla.LISTA
            }

            Gestor_Tareas_PMMTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    if (pantallaActual == Pantalla.LISTA) {
                        ListaTareasScreen(
                            tareas = tareasVisibles,
                            soloPendientes = soloPendientes,
                            onSoloPendientesChange = { viewModel.cambiarSoloPendientes(it) },
                            onAnadirClick = {
                                tareaEditandoId = null
                                pantallaActual = Pantalla.FORMULARIO
                            },
                            onCompletadaChange = { viewModel.cambiarCompletada(it) },
                            onEditar = {
                                tareaEditandoId = it.id
                                pantallaActual = Pantalla.FORMULARIO
                            },
                            onEliminar = { viewModel.eliminarTarea(it) },
                            onEjemplosClick = { pantallaActual = Pantalla.EJEMPLOS },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else if (pantallaActual == Pantalla.FORMULARIO) {
                        val tareaAEditar = tareas.find { it.id == tareaEditandoId }
                        FormularioTareaScreen(
                            tareaInicial = tareaAEditar,
                            onGuardar = {
                                if (tareaEditandoId == null) {
                                    viewModel.agregarTarea(it)
                                } else {
                                    viewModel.actualizarTarea(it)
                                }
                                pantallaActual = Pantalla.LISTA
                            },
                            onCancelar = { pantallaActual = Pantalla.LISTA },
                            modifier = Modifier.padding(innerPadding)
                        )
                    } else if (pantallaActual == Pantalla.EJEMPLOS) {
                        EjemplosScreen(
                            estado = estadoEjemplos,
                            onCargar = { ejemplosViewModel.cargar() },
                            onVolver = { pantallaActual = Pantalla.LISTA },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}