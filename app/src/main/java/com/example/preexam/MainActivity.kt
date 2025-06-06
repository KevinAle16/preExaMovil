package com.example.preexam

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var txtNombre: EditText
    private lateinit var btnEntrar: Button
    private lateinit var btnRegresar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        iniciarComponentes()
        eventosClic()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @SuppressLint("SetTextI18n")
    fun iniciarComponentes() {
        txtNombre = findViewById(R.id.txtNombre) as EditText
        btnEntrar = findViewById(R.id.btnEntrar) as Button
        btnRegresar = findViewById(R.id.btnRegresar) as Button
    }
    @SuppressLint("SetTextI18n")
    fun eventosClic() {
        btnEntrar.setOnClickListener(View.OnClickListener {
            if (txtNombre.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "Debe ingresar el nombre del trabajador", Toast.LENGTH_SHORT).show()
                txtNombre.requestFocus()
            } else {
                val intent = Intent(this, ReciboNominaActivity::class.java)
                intent.putExtra("nombre", txtNombre.text.toString())
                intent.putExtra("recibo", ReciboNomina().generarFolio()) // folio aleatorio
                startActivity(intent)
            }
        })

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