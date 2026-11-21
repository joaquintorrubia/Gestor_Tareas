package com.joaquin.gestor_tareas_pmm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joaquin.gestor_tareas_pmm.ui.TareasViewModel
import com.joaquin.gestor_tareas_pmm.ui.screens.ListaTareasScreen
import com.joaquin.gestor_tareas_pmm.ui.theme.Gestor_Tareas_PMMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: TareasViewModel = viewModel()
            val tareas by viewModel.tareas.collectAsState()

            Gestor_Tareas_PMMTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListaTareasScreen(
                        tareas = tareas,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}