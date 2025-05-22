package com.ebookfrenzy.bmicalculatorapplication

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyView: TextView
    private lateinit var clearButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyView = findViewById(R.id.historyTextView)
        clearButton = findViewById(R.id.clearButton)
        backButton = findViewById(R.id.backButton)

        val sharedPref = getSharedPreferences("BMIRecords", Context.MODE_PRIVATE)

        // Load full history
        val history = sharedPref.getString("history_list", "")

        // Show history or message if empty
        historyView.text = if (history.isNullOrEmpty()) "No history yet." else history

        clearButton.setOnClickListener {
            with(sharedPref.edit()) {
                remove("history_list")
                apply()
            }
            historyView.text = "History cleared."
            Toast.makeText(this, "History has been cleared.", Toast.LENGTH_SHORT).show()
        }

        backButton.setOnClickListener {
            finish()
        }
    }

}



