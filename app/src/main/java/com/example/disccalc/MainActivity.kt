package com.example.disccalc

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.stream.Gatherer


class MainActivity : AppCompatActivity() {
    var feedrate: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val shotweight = findViewById<EditText>(R.id.shotweight)
        val additiveratio = findViewById<EditText>(R.id.additiveratio)
        val recovertime = findViewById<EditText>(R.id.recoverytime)
        val calcButton = findViewById<Button>(R.id.calcButton)
       // val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        calcButton.setOnClickListener {
            val shotweightIN = shotweight.text.toString().toDoubleOrNull()
            val additiveIN = additiveratio.text.toString().toDoubleOrNull()
            val recoveryIN = recovertime.text.toString().toDoubleOrNull()
            if (shotweightIN != null && additiveIN != null && recoveryIN != null) {
                val results = generateResults(shotweightIN, additiveIN, recoveryIN)
              //  adapter.updateData(results)
            } else {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateResults(shotweightIN: Double,additiveIN: Double,recoveryIN: Double) {
        feedrate = shotweightIN * additiveIN * 0.6 / recoveryIN

        val table = findViewById<TableLayout>(R.id.myTable)
        val row = TableRow(this)
        row.setLayoutParams(
            TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
        )


// Create and add cells

        val col1 = TextView(this).apply { text = "DISC (Typical grams/revolution)" }
        //col1.setBackgroundColor(Color.RED)
        row.addView(col1)

        val col2 = TextView(this).apply { text = "BD ≈ 0.6 kg/l \nGrams/Rev." }
        col2.setBackgroundColor(Color.CYAN)
        row.addView(col2)

        val col3 = TextView(this).apply { text = "% Disc \nSpeed" }
        col3.setBackgroundColor(Color.CYAN)
        row.addView(col3)

        val col4 = TextView(this).apply { text = "Seconds/\nPocket" }
        col4.setBackgroundColor(Color.CYAN)
        row.addView(col4)

        val col5 = TextView(this).apply { text = "Disc Speed \nRPS" }
        col5.setBackgroundColor(Color.CYAN)
        row.addView(col5)

        table.addView(row)

        //val row2 = TableRow(this)
        val discs = resources.getStringArray(R.array.discs)
        val discVals = resources.getStringArray(R.array.discVals)

        var myNum: Int = 0
        for (disc in discs) {

            val row = TableRow(this)
            row.setLayoutParams(
                TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
            )
            val col1 = TextView(this).apply { text = disc }
            //col1.setBackgroundColor(Color.RED)
            row.addView(col1)

            val col2 = TextView(this).apply { text = discVals[myNum] }
            //col2.setBackgroundColor(Color.CYAN)
            row.addView(col2)

            //val mydouble = discVals[myNum].toDouble()
            val calcspeed=100*feedrate/(14.65 * discVals[myNum].toDouble())
            val col3 = TextView(this).apply { text =  "%.2f".format(calcspeed) }
            //col3.setBackgroundColor(Color.CYAN).
            col3.setPadding(16, 16, 16, 16) // left, top, right, bottom in pixels

            row.addView(col3)

            val calpockets=409.5/(calcspeed*60)
            val col4 = TextView(this).apply { text = "%.3f".format(calpockets)  }
            //col4.setBackgroundColor(Color.CYAN)
            row.addView(col4)

            val col5 = TextView(this).apply { text = "Disc Speed \nRPS" }
            //col5.setBackgroundColor(Color.CYAN)
            row.addView(col5)
            table.addView(row)
            myNum++
        }

      /*  val discs = resources.getStringArray(R.array.discs)
        val mytextView = findViewById<TextView?>(R.id.mytextView)

        val htmlText = "DISC (Typical grams/revolution)<span style=\"background-color: aqua;\">BD ≈ 0.6 kg/l</span> <font color='#FF0000'>Red</font> text."


        mytextView.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
        // mytextView.setText("")
        for (disc in discs) {
            mytextView.append("HI:" + disc + "\n");
        }

        */

    }
}