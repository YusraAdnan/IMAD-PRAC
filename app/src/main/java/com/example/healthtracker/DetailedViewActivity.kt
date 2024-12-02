package com.example.healthtracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailedViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view)

        // Retrieve arrays passed from MainActivity
        val waterIntake = intent.getIntArrayExtra("waterIntake") ?: IntArray(7)
        val exerciseMinutes = intent.getIntArrayExtra("exerciseMinutes") ?: IntArray(7)
        val sleepHours = intent.getIntArrayExtra("sleepHours") ?: IntArray(7)
        val dates = intent.getStringArrayExtra("dates") ?: arrayOfNulls<String>(7)
        val backButton = findViewById<Button>(R.id.backButton)

        val detailsText = findViewById<TextView>(R.id.detailsText)

        // Create a table-like format for displaying details
        val details = StringBuilder()
        details.append(
            String.format(
                "%-15s%-15s%-15s%-15s\n",
                "Date", "Water Intake", "Exercise", "Sleep"
            )
        )
        details.append("------------------------------------------------------------\n")
        for (i in 0 until 7) {
            val date = dates[i] ?: "N/A" // Handle cases where no date was chosen
            details.append(
                String.format(
                    "%-15s%-15s%-15s%-15s\n",
                    date,
                    "${waterIntake[i]} cups",
                    "${exerciseMinutes[i]} mins",
                    "${sleepHours[i]} hrs"
                )
            )
        }

        detailsText.text = details.toString()
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close the DetailedViewActivity to prevent going back to it
        }
    }
}
