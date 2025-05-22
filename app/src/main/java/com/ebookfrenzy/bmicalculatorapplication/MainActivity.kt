package com.ebookfrenzy.bmicalculatorapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
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
    private lateinit var viewHistoryButton: Button


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
        viewHistoryButton = findViewById(R.id.button3)

        val units1 = arrayOf("kg", "lbs")
        val units2 = arrayOf("m", "cm")

        val adapter1 = ArrayAdapter(this, android.R.layout.simple_spinner_item, units1)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, units2)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        weightUnit.adapter = adapter1
        heightUnit.adapter = adapter2

        // Calculate Button
        calculateButton.setOnClickListener {
            calculateBMI()
        }

        saveButton.setOnClickListener {
            saveRecord()
        }

        viewHistoryButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

    }

    //Button click function
    fun calculateBMI() {
        val weightInput = inputWeight.text.toString()
        val heightInput = inputHeight.text.toString()
        val unitOfWeight = weightUnit.selectedItem.toString()
        val unitOfHeight = heightUnit.selectedItem.toString()

        if (weightInput.isEmpty() || heightInput.isEmpty()) {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show()
            return
        }

        var weightValue = weightInput.toDouble()
        var heightValue = heightInput.toDouble()

        //Convert to standard unit: kg and m
        if (unitOfWeight == "lb") weightValue /= 2.205  // convert pounds to kg
        if (unitOfHeight == "cm") heightValue /= 100    // convert cm to m

        val bmi = weightValue / (heightValue * heightValue)
        val bmiFormatted = String.format("%.2f", bmi)
        bmiDisplay.text = bmiFormatted
        categoryDisplay.text = getBMICategory(bmi)

        // Optional: Clear input
        inputWeight.text.clear()
        inputHeight.text.clear()
    }

    fun getBMICategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 24.9 -> "Normal weight"
            bmi < 29.9 -> "Overweight"
            else -> "Obesity"
        }
    }

    fun saveRecord() {
        val bmiValue = bmiDisplay.text.toString()
        val category = categoryDisplay.text.toString()
        val sharedPref = getSharedPreferences("BMIRecords", Context.MODE_PRIVATE)

        // Retrieve current history
        val currentHistory = sharedPref.getString("history_list", "")

        // Append new entry
        val newEntry = "BMI: $bmiValue - Category: $category\n"
        val updatedHistory = currentHistory + newEntry

        // Save updated history
        with(sharedPref.edit()) {
            putString("history_list", updatedHistory)
            apply()
        }

        Toast.makeText(this, "BMI record saved!", Toast.LENGTH_SHORT).show()
    }
}