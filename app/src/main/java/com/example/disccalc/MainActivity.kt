package com.example.disccalc

import android.graphics.Color
import android.graphics.Typeface
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
import java.util.function.DoubleUnaryOperator
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
        table.removeAllViews()
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

        val discs = resources.getStringArray(R.array.discs)
        val discVals = resources.getStringArray(R.array.discVals)

        var myNum: Int = 0
        var b17: Double = 1.0
        var b18: Double = 2.0
        var b19: Double = 0.0
        var b20: Double = 0.0
        var e19: Double = 0.0
        var e20: Double = 0.0

        //
        //start the disc loop
        //

        for (disc in discs) {
            if (disc=="12 Pocket (9 mm)"){b17=discVals[6].toDouble()}
            else if (disc=="8 Pocket (8.5 mm)"){b18=discVals[7].toDouble()}

            val row = TableRow(this)
            row.setLayoutParams(
                TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )
            )

            if (myNum % 2 == 0) {
                row.setBackgroundColor(Color.LTGRAY)

            }
            else {
                row.setBackgroundColor(Color.TRANSPARENT)
                row
            }

            val col1 = TextView(this).apply {
                text = disc
                setTypeface(null, Typeface.BOLD)
            }
            //col1.setBackgroundColor(Color.RED)
            row.addView(col1)

            val col2:TextView
            if (disc=="12 Pocket (9 mm) - High Speed"){
                b19=(b17*0.9)
                col2 = TextView(this).apply { text = "%.2f".format(b19)  }
            }
            else if (disc=="8 Pocket (8.5 mm) - High Speed"){
                b20=(b18*0.9)
                col2 = TextView(this).apply { text = "%.2f".format(b20) }
            }
            else {
                col2 = TextView(this).apply { text = discVals[myNum] }
                //col2.setBackgroundColor(Color.CYAN)
            }

            val textView = TextView(this)
            textView.text = col2.text
            textView.setTextColor(Color.RED)

            row.addView(textView)
            //row.addView(col2)

            val col3:TextView
            val calcspeed:Double

            if (disc=="12 Pocket (9 mm) - High Speed"){
                calcspeed = 100 * feedrate / (40.96 * b19)
                e19=(calcspeed/100)*40.69/60
            }
            else if (disc=="8 Pocket (8.5 mm) - High Speed"){
                calcspeed = 100 * feedrate / (40.96 * b20)
                e20=(calcspeed/100)*40.69/60}
            else {
                calcspeed = 100 * feedrate / (14.65 * discVals[myNum].toDouble())
            }
            col3 = TextView(this).apply { text =  "%.2f".format(calcspeed) }
            //col3.setBackgroundColor(Color.CYAN).
            col3.setPadding(16, 16, 16, 16) // left, top, right, bottom in pixels

            if ((calcspeed>1.5 && calcspeed<4.99)or (calcspeed>85 && calcspeed<95)){col3.setBackgroundColor(Color.YELLOW)}
            if (calcspeed<1.5 || calcspeed>95){col3.setBackgroundColor(Color.RED)}
            if ((calcspeed>5)&& (calcspeed<85)){col3.setBackgroundColor(Color.GREEN)}

            row.addView(col3)

            val calpockets: Double
            if (disc=="40 Pocket (5 mm)"){calpockets=409.5/(calcspeed*40)}
            else if (disc=="24 Pocket (5 mm)"){calpockets=409.5/(calcspeed*24)}
            else if (disc=="15 Pocket (5 mm)"){calpockets=409.5/(calcspeed*15)}
            else if (disc=="15 Pocket (9 mm)"){calpockets=409.5/(calcspeed*15)}
            else if (disc=="12 Pocket (9 mm)"){calpockets=409.5/(calcspeed*12)}
            else if (disc=="8 Pocket (8.5 mm)"){calpockets=409.5/(calcspeed*8)}
            else if (disc=="12 Pocket (9 mm) - High Speed"){calpockets=1/(12*e19)}
            else if (disc=="8 Pocket (8.5 mm) - High Speed"){calpockets=1/(8*e20)}
            else {calpockets=409.5/(calcspeed*60)}

            val col4 = TextView(this).apply { text = "%.3f".format(calpockets)  }
            //col4.setBackgroundColor(Color.CYAN)
            row.addView(col4)

            var discspeedRPS: Double =0.0
            if (disc=="12 Pocket (9 mm) - High Speed"){discspeedRPS=(calcspeed/100)*40.69/60}
            else if (disc=="8 Pocket (8.5 mm) - High Speed"){discspeedRPS=(calcspeed/100)*40.69/60}
            else {discspeedRPS = (calcspeed/100)*14.65/60}
            val col5 = TextView(this).apply { text = "%.3f".format(discspeedRPS)  }

            //col5.setBackgroundColor(Color.CYAN)
            row.addView(col5)
            table.addView(row)
            myNum++
        }

    }
}