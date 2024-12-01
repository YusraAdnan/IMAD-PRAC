package com.example.healthtracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healthtracker.DetailedViewActivity
import com.example.healthtracker.R

class MainActivity : AppCompatActivity() {
    
    // Parallel arrays to track user inputs
    private val waterIntake = IntArray(7) { 0 }
    private val exerciseMinutes = IntArray(7) { 0 }
    private val sleepHours = IntArray(7) { 0 }
    private var currentDay = 0 // Tracks which day the user is inputting data for

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val waterInput = findViewById<EditText>(R.id.waterInput)
        val exerciseInput = findViewById<EditText>(R.id.exerciseInput)
        val sleepInput = findViewById<EditText>(R.id.sleepInput)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val clearButton = findViewById<Button>(R.id.clearButton)
        val viewDetailsButton = findViewById<Button>(R.id.viewDetailsButton)

        saveButton.setOnClickListener {
            // Save inputs in the corresponding arrays for the current day
            waterIntake[currentDay] = waterInput.text.toString().toIntOrNull() ?: 0
            exerciseMinutes[currentDay] = exerciseInput.text.toString().toIntOrNull() ?: 0
            sleepHours[currentDay] = sleepInput.text.toString().toIntOrNull() ?: 0

            Toast.makeText(this, "Details Saved for Day ${currentDay + 1}", Toast.LENGTH_SHORT).show()

            // Move to the next day (looping back after Day 7)
            currentDay = (currentDay + 1) % 7
            
        }

        clearButton.setOnClickListener {
            clearFields(waterInput, exerciseInput, sleepInput)
        }

        viewDetailsButton.setOnClickListener {
            // Navigate to DetailsActivity with the tracked data
            val intent = Intent(this, DetailedViewActivity::class.java)
            intent.putExtra("waterIntake", waterIntake)
            intent.putExtra("exerciseMinutes", exerciseMinutes)
            intent.putExtra("sleepHours", sleepHours)
            startActivity(intent)
        }
    }

    private fun clearFields(waterInput: EditText, exerciseInput: EditText, sleepInput: EditText) {
        waterInput.text.clear()
        exerciseInput.text.clear()
        sleepInput.text.clear()
    }
}
