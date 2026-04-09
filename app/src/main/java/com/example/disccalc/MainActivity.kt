package com.example.disccalc

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ResultAdapter

    var feedrate: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val shotweight = findViewById<EditText>(R.id.shotweight)
        val additiveratio = findViewById<EditText>(R.id.additiveratio)
        val recovertime = findViewById<EditText>(R.id.recoverytime)
        val calcButton = findViewById<Button>(R.id.calcButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        adapter = ResultAdapter(emptyList())
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        calcButton.setOnClickListener {
            val shotweightIN = shotweight.text.toString().toDoubleOrNull()
            val additiveIN = additiveratio.text.toString().toDoubleOrNull()
            val recoveryIN = recovertime.text.toString().toDoubleOrNull()
            if (shotweightIN != null && additiveIN != null && recoveryIN != null) {
                val results = generateResults(shotweightIN, additiveIN, recoveryIN)
                adapter.updateData(results)
            } else {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateResults(shotweightIN: Double,additiveIN: Double,recoveryIN: Double): List<ResultItem> {
      /*  return listOf(
            ResultItem("Double", (input * 2).toString()),
            ResultItem("Square", (input * input).toString()),
            ResultItem("Half", (input / 2).toString()),
            ResultItem("Square Root", kotlin.math.sqrt(input).toString())

       */
        feedrate = shotweightIN * additiveIN * 0.6 / recoveryIN

        return listOf(
            ResultItem(toplabel="top","tempfeedrate", feedrate.toString()),
            ResultItem(toplabel="top","Square", (shotweightIN * shotweightIN).toString()),
            ResultItem(toplabel="top","Half", (shotweightIN / 2).toString()),
            ResultItem(toplabel="top","Square Root", kotlin.math.sqrt(shotweightIN).toString())
        )
    }
}