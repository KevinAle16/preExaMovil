package com.example.preexam

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ReciboNominaActivity : AppCompatActivity() {

    private lateinit var lblNumRecibo: TextView
    private lateinit var lblNombre: TextView
    private lateinit var txtHorasNormales: EditText
    private lateinit var txtHorasExtras: EditText

    private lateinit var rbPuesto1: RadioButton
    private lateinit var rbPuesto2: RadioButton
    private lateinit var rbPuesto3: RadioButton

    private lateinit var lblSubtotal: TextView
    private lateinit var lblImpuesto: TextView
    private lateinit var lblTotal: TextView

    private lateinit var btnCalcular: Button
    private lateinit var btnLimpiar: Button
    private lateinit var btnRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recibo_nomina)
        iniciarComponentes()
        eventosClic()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun iniciarComponentes() {
        lblNumRecibo = findViewById(R.id.lblNumRecibo)
        lblNombre = findViewById(R.id.lblNombre)
        txtHorasNormales = findViewById(R.id.txtHorasNormales)
        txtHorasExtras = findViewById(R.id.txtHorasExtras)

        rbPuesto1 = findViewById(R.id.rbPuesto1)
        rbPuesto2 = findViewById(R.id.rbPuesto2)
        rbPuesto3 = findViewById(R.id.rbPuesto3)

        lblSubtotal = findViewById(R.id.lblSubtotal)
        lblImpuesto = findViewById(R.id.lblImpuesto)
        lblTotal = findViewById(R.id.lblTotal)

        btnCalcular = findViewById(R.id.btnCalcular)
        btnLimpiar = findViewById(R.id.btnLimpiar)
        btnRegresar = findViewById(R.id.btnRegresar)

        val strNombre = intent.getStringExtra("nombre").toString()
        lblNombre.text = getString(R.string.nombre_empleado) + " " + strNombre

        val folio = intent.getIntExtra("recibo", 0)
        lblNumRecibo.text = getString(R.string.num_recibo) + " " + folio
    }

    fun eventosClic() {
        btnCalcular.setOnClickListener {
            val recibo = ReciboNomina()

            if (txtHorasNormales.text.toString().isEmpty() || txtHorasExtras.text.toString().isEmpty()) {
                Toast.makeText(this, "Falta capturar horas trabajadas", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    recibo.nombre = lblNombre.text.toString().replace("Nombre: ", "")
                    recibo.horarioTabNormal = txtHorasNormales.text.toString().toFloat()
                    recibo.horarioTabExtras = txtHorasExtras.text.toString().toFloat()

                    recibo.puesto = when {
                        rbPuesto1.isChecked -> 1
                        rbPuesto2.isChecked -> 2
                        rbPuesto3.isChecked -> 3
                        else -> 0
                    }
                    recibo.numRecibo = intent.getIntExtra("recibo", 0)


                    lblSubtotal.text = getString(R.string.subtotal, recibo.calcularSubtotal())
                    lblImpuesto.text = getString(R.string.impuesto, recibo.calcularImpuesto())
                    lblTotal.text = getString(R.string.total, recibo.calcularTotal())
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Formato de horas incorrecto", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnLimpiar.setOnClickListener {
            txtHorasNormales.setText("")
            txtHorasExtras.setText("")
            rbPuesto1.isChecked = true

            lblSubtotal.text = getString(R.string.subtotal_default)
            lblImpuesto.text = getString(R.string.impuesto_default)
            lblTotal.text = getString(R.string.total_default)
        }

        btnRegresar.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle(getString(R.string.app_name))
            dialog.setMessage(getString(R.string.confirmar_cierre))
            dialog.setPositiveButton(getString(R.string.aceptar)) { _, _ ->
                finish()
            }
            dialog.setNegativeButton(getString(R.string.cancelar)) { _, _ ->
                Toast.makeText(this, getString(R.string.cancelado), Toast.LENGTH_SHORT).show()
            }
            dialog.show()
        }
    }
    }

