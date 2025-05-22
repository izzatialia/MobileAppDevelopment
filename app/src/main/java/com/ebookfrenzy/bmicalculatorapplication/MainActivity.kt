package com.ebookfrenzy.bmicalculatorapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var inputWeight: EditText
    private lateinit var inputHeight: EditText
    private lateinit var weightUnit: Spinner
    private lateinit var heightUnit: Spinner
    private lateinit var bmiDisplay: TextView
    private lateinit var categoryDisplay: TextView
    private lateinit var calculateButton: Button
    private lateinit var saveButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inputWeight = findViewById(R.id.weightTextbox)
        inputHeight = findViewById(R.id.heightTextbox)
        weightUnit = findViewById(R.id.weightSelection)
        heightUnit = findViewById(R.id.heightSelection)
        bmiDisplay = findViewById(R.id.bmiDisplay)
        categoryDisplay = findViewById(R.id.bmiCategory)
        calculateButton = findViewById(R.id.button1)
        saveButton = findViewById(R.id.button2)

        val units1 = arrayOf("kg", "lbs")
        val units2 = arrayOf("m", "cm")



    }
}