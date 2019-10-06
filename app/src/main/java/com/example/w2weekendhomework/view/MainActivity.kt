package com.example.w2weekendhomework.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.w2weekendhomework.R
import com.example.w2weekendhomework.adapter.ColorAdapter
import com.example.w2weekendhomework.util.ColorObject
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.activity_notes.view.*
import kotlinx.android.synthetic.main.color_item_view_layout.view.*
import kotlinx.android.synthetic.main.note_item_view_layout.view.*

class MainActivity : AppCompatActivity(), ColorAdapter.colorAdapterDelegator {

    private lateinit var sharedPreferences: SharedPreferences

    override fun colorPicked(colorResource: String) {
        val spEditor = sharedPreferences.edit()
        setUpColor(colorResource)
        spEditor.putString("my_app_color", colorResource)
        spEditor.apply()


        Toast.makeText(applicationContext, "You have selected, ${colorResource}", Toast.LENGTH_SHORT).show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = this.getSharedPreferences("color_app_101", Context.MODE_PRIVATE)
        setUpColor(sharedPreferences.getString("my_app_color", "BLUE"))

        setupRV()

        btnMain.setOnClickListener {
            val intent = Intent(this, NotesActivity::class.java)
            startActivity(intent)
        }

    }

    fun setupRV() {
        color_recyclerview.adapter = ColorAdapter(
            mutableListOf(
                ColorObject.COLOR_BLUE,
                ColorObject.COLOR_GREEN,
                ColorObject.COLOR_RED
            ), this
        )
        color_recyclerview.layoutManager = LinearLayoutManager(this)
        color_recyclerview.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
    }

    fun setUpColor(colorPicked: String?) {
        when (colorPicked) {
            ColorObject.COLOR_GREEN -> (
                ContextCompat.getColor(
                    this,
                    R.color.appGreen
                )
            )
            ColorObject.COLOR_RED -> (
                ContextCompat.getColor(
                    this,
                    R.color.appRed
                )
            )
            ColorObject.COLOR_BLUE -> (
                ContextCompat.getColor(
                    this,
                    R.color.appBlue
                )
            )
        }

    }

}
