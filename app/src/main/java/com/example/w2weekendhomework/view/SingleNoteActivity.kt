package com.example.w2weekendhomework.view

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.w2weekendhomework.R
import com.example.w2weekendhomework.model.Notes
import com.example.w2weekendhomework.util.ColorObject
import kotlinx.android.synthetic.main.activity_single_note.*
import kotlinx.android.synthetic.main.activity_single_note.view.*

class SingleNoteActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_note)
        sharedPreferences = this.getSharedPreferences("color_app_101", Context.MODE_PRIVATE)
        val colorReceived = sharedPreferences.getString("my_app_color", "Error Color")
        val numberNote = sharedPreferences.getInt("Number Note", 0)
        val noteSelected = intent.getStringExtra("note")?: ""



        val retrievedNote = Notes(sharedPreferences.getString("Noteitem_${numberNote.minus(1)}", "Error Note")?:"")
        singleNote_textview.text = "${noteSelected}"
        when (colorReceived) {
            ColorObject.COLOR_GREEN -> {
                single_note_activity.singleNote_textview.setTextColor(
                    Color.parseColor("#006D05")
                )

            }
            ColorObject.COLOR_BLUE -> {
                single_note_activity.singleNote_textview.setTextColor(
                    Color.parseColor("#00009C")
                )


            }
            ColorObject.COLOR_RED -> {
                single_note_activity.singleNote_textview.setTextColor(
                    Color.parseColor("#A80000")
                )

            }
        }

    }
}
