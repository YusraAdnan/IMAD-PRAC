package com.example.healthtracker

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    // Parallel arrays to track user inputs
    private val waterIntake = IntArray(7) { 0 }
    private val exerciseMinutes = IntArray(7) { 0 }
    private val sleepHours = IntArray(7) { 0 }
    private val dates = arrayOfNulls<String>(7) // Store selected dates
    private var currentDay = 0 // Tracks which day the user is inputting data for

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateTextView = findViewById<TextView>(R.id.dateTextView)
        val waterInput = findViewById<EditText>(R.id.waterInput)
        val exerciseInput = findViewById<EditText>(R.id.exerciseInput)
        val sleepInput = findViewById<EditText>(R.id.sleepInput)
        val pickDateButton = findViewById<Button>(R.id.pickDateButton)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val clearButton = findViewById<Button>(R.id.clearButton)
        val viewDetailsButton = findViewById<Button>(R.id.viewDetailsButton)

        // Set the current date as default
        val calendar = Calendar.getInstance()
        updateDateInView(dateTextView, calendar)

        pickDateButton.setOnClickListener {
            // Show DatePickerDialog to select a date
            DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    // Update the date in the TextView
                    calendar.set(year, month, dayOfMonth)
                    updateDateInView(dateTextView, calendar)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        saveButton.setOnClickListener {
            // Save user inputs for the selected day
            waterIntake[currentDay] = waterInput.text.toString().toIntOrNull() ?: 0
            exerciseMinutes[currentDay] = exerciseInput.text.toString().toIntOrNull() ?: 0
            sleepHours[currentDay] = sleepInput.text.toString().toIntOrNull() ?: 0
            dates[currentDay] = dateTextView.text.toString() // Save the selected date

            Toast.makeText(this, "Details Saved for Day ${currentDay + 1}", Toast.LENGTH_SHORT).show()

            // Move to the next day (cycling back to Day 1 after Day 7)
            currentDay = (currentDay + 1) % 7
        }

        clearButton.setOnClickListener {
            // Clear input fields
            clearFields(waterInput, exerciseInput, sleepInput)
        }

        viewDetailsButton.setOnClickListener {
            // Navigate to DetailedViewActivity, passing the arrays
            val intent = Intent(this, DetailedViewActivity::class.java)
            intent.putExtra("waterIntake", waterIntake)
            intent.putExtra("exerciseMinutes", exerciseMinutes)
            intent.putExtra("sleepHours", sleepHours)
            intent.putExtra("dates", dates)
            startActivity(intent)
        }
    }

    private fun updateDateInView(dateTextView: TextView, calendar: Calendar) {
        val formattedDate = "${calendar.get(Calendar.YEAR)}-${String.format("%02d", calendar.get(Calendar.MONTH) + 1)}-${String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))}"
        dateTextView.text = formattedDate
    }

    private fun clearFields(waterInput: EditText, exerciseInput: EditText, sleepInput: EditText) {
        waterInput.text.clear()
        exerciseInput.text.clear()
        sleepInput.text.clear()
    }
}
