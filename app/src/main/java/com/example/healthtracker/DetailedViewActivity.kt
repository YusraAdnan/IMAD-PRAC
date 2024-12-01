package com.example.healthtracker

import android.os.Bundle
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

        val additionalText = findViewById<TextView>(R.id.additionalText)

        val detailsText = findViewById<TextView>(R.id.detailsText)

        //StringBuilder is efficient for creating multi-line text dynamically.
        val details = StringBuilder("Habit Details:\n")
        for (i in 0 until 7) {
            details.append("Day ${i + 1}: Water: ${waterIntake[i]} cups, Exercise: ${exerciseMinutes[i]} mins, Sleep: ${sleepHours[i]} hrs\n")
        }
        detailsText.text = details.toString()

        // Calculate the day with the highest water intake
        val highestWaterDay = waterIntake.indices.maxByOrNull { waterIntake[it] }?.plus(1) ?: "None"
        val averageSleep = sleepHours.average() //use the inbuilt average function for automatically calculating the average of the array

        // Display additional information
        additionalText.text = """
            Additional Information:
            - Day with the highest water intake: Day $highestWaterDay
            - Average sleep duration: ${"%.2f".format(averageSleep)} hours
        """.trimIndent()
    }
}
