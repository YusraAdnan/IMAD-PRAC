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

        // Retrieve arrays passed from MainActivity. This is how we gain access to information passed from other screens
        val waterIntake = intent.getIntArrayExtra("waterIntake") ?: IntArray(7)
        val exerciseMinutes = intent.getIntArrayExtra("exerciseMinutes") ?: IntArray(7)
        val sleepHours = intent.getIntArrayExtra("sleepHours") ?: IntArray(7)
        val dates = intent.getStringArrayExtra("dates") ?: arrayOfNulls<String>(7)
        val backButton = findViewById<Button>(R.id.backButton)
        val additionalText = findViewById<TextView>(R.id.additionalText)

        val detailsText = findViewById<TextView>(R.id.detailsText)

        // This helps create a table-like format for displaying details
        val details = StringBuilder()
        details.append(
            String.format(
                "%-15s%-15s%-15s%-15s\n",
                "Date", "Water Intake", "Exercise", "Sleep"
            )
        )
        details.append("------------------------------------------------------------\n")
        for (i in 0 until 7) {
            val date = dates[i] ?: "N/A" // This handles cases where no date was chosen
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
            finish()
        }


        val highestWaterDay = waterIntake.indices.maxByOrNull { waterIntake[it] }?.plus(1) ?: "None"
        val averageSleep = sleepHours.average()

        additionalText.text = """
            Additional Information:
            - Day with the highest water intake: Day $highestWaterDay
            - Average sleep duration: ${"%.2f".format(averageSleep)} hours
        """.trimIndent()
    }
}
