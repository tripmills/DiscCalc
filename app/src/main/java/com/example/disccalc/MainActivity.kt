package com.example.disccalc

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ResultAdapter

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
            val input = shotweight.text.toString().toDoubleOrNull()
            if (input != null) {
                val results = generateResults(input)
                adapter.updateData(results)
            } else {
                Toast.makeText(this, "Enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateResults(input: Double): List<ResultItem> {
        return listOf(
            ResultItem("Double", (input * 2).toString()),
            ResultItem("Square", (input * input).toString()),
            ResultItem("Half", (input / 2).toString()),
            ResultItem("Square Root", kotlin.math.sqrt(input).toString())
        )
    }
}