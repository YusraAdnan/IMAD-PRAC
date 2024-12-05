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

        // Sets the current date as default
        val calendar = Calendar.getInstance()
        updateDateInView(dateTextView, calendar)

        pickDateButton.setOnClickListener {

            // This allows to show DatePickerDialog to select a date
            pickDateButton.setOnClickListener {
                DatePickerDialog(
                    this,
                    { _, year, month, dayOfMonth ->
                        calendar.set(year, month, dayOfMonth)  // Update the calendar object with the selected date
                        updateDateInView(dateTextView, calendar)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        saveButton.setOnClickListener {
            if (waterInput.text.isNullOrBlank() || exerciseInput.text.isNullOrBlank() || sleepInput.text.isNullOrBlank()) {
                //Error handling that warns if any input field is empty. Prevents from adding null items in the array
                Toast.makeText(this, "Please fill out all fields before saving.", Toast.LENGTH_SHORT).show()
            } else {
                // Save user inputs for the selected day only if fields are not empty
                waterIntake[currentDay] = waterInput.text.toString().toIntOrNull() ?: 0
                exerciseMinutes[currentDay] = exerciseInput.text.toString().toIntOrNull() ?: 0
                sleepHours[currentDay] = sleepInput.text.toString().toIntOrNull() ?: 0
                dates[currentDay] = dateTextView.text.toString() // Save the selected date

                Toast.makeText(this, "Details Saved for Day ${currentDay + 1}", Toast.LENGTH_SHORT).show()

                // Move to the next day (cycling back to Day 1 after Day 7)
                currentDay = (currentDay + 1) % 7
            }
        }


        clearButton.setOnClickListener {
            clearFields(waterInput, exerciseInput, sleepInput)
        }

        viewDetailsButton.setOnClickListener {
            // Navigate to DetailedViewActivity, passing the arrays to the screen
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
        // This check only clears the text view if there is text in it
        if (!waterInput.text.isNullOrBlank()) {
            waterInput.text.clear()
        }
        if (!exerciseInput.text.isNullOrBlank()) {
            exerciseInput.text.clear()
        }
        if (!sleepInput.text.isNullOrBlank()) {
            sleepInput.text.clear()
        }
        Toast.makeText(this, "Input fields cleared", Toast.LENGTH_SHORT).show()
    }

}
