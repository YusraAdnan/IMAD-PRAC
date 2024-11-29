package com.example.healthtracker

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailedViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view)

        val waterIntake = intent.getIntArrayExtra("waterIntake") ?: IntArray(7)
        val exerciseMinutes = intent.getIntArrayExtra("exerciseMinutes") ?: IntArray(7)
        val sleepHours = intent.getIntArrayExtra("sleepHours") ?: IntArray(7)

        val additionalText = findViewById<TextView>(R.id.additionalText)

        val detailsText = findViewById<TextView>(R.id.detailsText)
        val details = StringBuilder("Habit Details:\n")
        for (i in 0 until 7) {
            details.append("Day ${i + 1}: Water: ${waterIntake[i]} cups, Exercise: ${exerciseMinutes[i]} mins, Sleep: ${sleepHours[i]} hrs\n")
        }
        detailsText.text = details.toString()

        val highestWaterDay = waterIntake.indices.maxByOrNull { waterIntake[it] }?.plus(1) ?: "None"
        val averageSleep = sleepHours.average()

        // Display additional information
        additionalText.text = """
            Additional Information:
            - Day with the highest water intake: Day $highestWaterDay
            - Average sleep duration: ${"%.2f".format(averageSleep)} hours
        """.trimIndent()
    }
}
