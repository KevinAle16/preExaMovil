package com.example.preexam

import java.io.Serializable

class ReciboNomina : Serializable {

    var numRecibo: Int = 0
    var nombre: String = ""
    var horarioTabNormal: Float = 0.0f
    var horarioTabExtras: Float = 0.0f
    var puesto: Int = 0
    private val pagoBase: Float = 200f

    fun generarFolio(): Int {
        return (1000..9999).random()
    }

    fun calcularSubtotal(): Float {
        val multiplicador = when (puesto) {
            1 -> 1.2f
            2 -> 1.5f
            3 -> 2.0f
            else -> 1.0f
        }

        val pagoHora = pagoBase * multiplicador
        val pagoNormal = horarioTabNormal * pagoHora
        val pagoExtras = horarioTabExtras * pagoHora * 2
        return pagoNormal + pagoExtras
    }

    fun calcularImpuesto(): Float {
        return calcularSubtotal() * 0.16f
    }

    fun calcularTotal(): Float {
        return calcularSubtotal() - calcularImpuesto()
    }
}
