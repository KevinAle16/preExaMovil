package com.example.preexam

import java.io.Serializable

class ReciboNomina : Serializable {

    var numRecibo: Int = 0
    var nombre: String = ""
    var horarioTabNormal: Float = 0.0f
    var horarioTabExtras: Float = 0.0f
    var puesto: Int = 0

    private val pagoBase: Float = 200f

    // Genera un número aleatorio entre 1000 y 9999
    fun generarFolio(): Int {
        return (1000..9999).random()
    }

    // Calcula el subtotal con incremento por puesto y doble pago por horas extras
    fun calcularSubtotal(): Float {
        val multiplicador = when (puesto) {
            1 -> 1.2f  // +20%
            2 -> 1.5f  // +50%
            3 -> 2.0f  // +100%
            else -> 1.0f
        }

        val pagoHora = pagoBase * multiplicador

        val pagoNormal = horarioTabNormal * pagoHora
        val pagoExtras = horarioTabExtras * pagoHora * 2

        return pagoNormal + pagoExtras
    }

    // Impuesto fijo del 16%
    fun calcularImpuesto(): Float {
        return calcularSubtotal() * 0.16f
    }

    // Total a pagar = Subtotal - Impuesto
    fun calcularTotal(): Float {
        return calcularSubtotal() - calcularImpuesto()
    }
}
