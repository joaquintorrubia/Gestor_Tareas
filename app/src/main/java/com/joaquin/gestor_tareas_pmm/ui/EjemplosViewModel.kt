package com.joaquin.gestor_tareas_pmm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaquin.gestor_tareas_pmm.data.remote.ClienteRetrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class EjemplosViewModel : ViewModel() {

    private val _estado = MutableStateFlow<EstadoEjemplos>(EstadoEjemplos.Inicial)
    val estado: StateFlow<EstadoEjemplos> = _estado.asStateFlow()

    fun cargar() {
        if (_estado.value is EstadoEjemplos.Cargando) {
            return
        }
        _estado.value = EstadoEjemplos.Cargando

        viewModelScope.launch {
            try {
                val lista = ClienteRetrofit.api.obtenerTareas(1)
                _estado.value = EstadoEjemplos.Exito(lista)
            } catch (e: IOException) {
                _estado.value = EstadoEjemplos.Error("No se pudo conectar. Comprueba tu conexión a Internet.")
            } catch (e: HttpException) {
                _estado.value = EstadoEjemplos.Error("El servidor respondió con un error (código ${e.code()}).")
            }
        }
    }
}