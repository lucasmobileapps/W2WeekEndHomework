package com.example.w2weekendhomework.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.w2weekendhomework.R
import com.example.w2weekendhomework.model.Notes
import com.example.w2weekendhomework.util.ColorObject
import com.example.w2weekendhomework.adapter.NoteAdapter
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.android.synthetic.main.activity_notes.view.*
import kotlinx.android.synthetic.main.note_item_view_layout.*
import kotlinx.android.synthetic.main.note_item_view_layout.view.*


class NotesActivity : AppCompatActivity(), NoteAdapter.NoteAdapterDelegate {
    lateinit var noteEdittext: String
    var noteList: MutableList<Notes> = mutableListOf()
    private lateinit var colorSharedPreferences: SharedPreferences
    private lateinit var noteSharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        colorSharedPreferences = this.getSharedPreferences("color_app_101", Context.MODE_PRIVATE)
        noteSharedPreferences = this.getSharedPreferences("note_app_101", Context.MODE_PRIVATE)
        val colorReceived = colorSharedPreferences.getString("my_app_color", "Error Color")
        val numberNote = noteSharedPreferences.getInt("Number Note", 0)


        for (i in 0..numberNote){
           val retrievedNote = Notes(noteSharedPreferences.getString("Noteitem_${i}", "Error Note")?:"")
            if(!(retrievedNote.note == "Error Note" || retrievedNote.note == ""))
            {
                noteList.add(retrievedNote)
            }
        }
        Log.d("KeyNote", "onCreate ${noteList.size}")


        when (colorReceived) {
            ColorObject.COLOR_GREEN -> {
                notes_activity_layout.tvNotesTitle.setTextColor(
                    Color.parseColor("#006D05")
                )
                notes_activity_layout.etNotes.setTextColor(
                    Color.parseColor("#006D05")
                )
                notes_activity_layout.notes_activity_layout.setBackgroundColor(
                    Color.parseColor("#AEFFB2")
                )

            }
            ColorObject.COLOR_BLUE -> {
                notes_activity_layout.tvNotesTitle.setTextColor(
                    Color.parseColor("#00009C")
                )
                notes_activity_layout.etNotes.setTextColor(
                    Color.parseColor("#00009C")
                )
                notes_activity_layout.notes_activity_layout.setBackgroundColor(
                    Color.parseColor("#94DBFD")
                )

            }
            ColorObject.COLOR_RED -> {
                notes_activity_layout.tvNotesTitle.setTextColor(
                    Color.parseColor("#A80000")
                )
                notes_activity_layout.etNotes.setTextColor(
                    Color.parseColor("#A80000")
                )
                notes_activity_layout.notes_activity_layout.setBackgroundColor(
                    Color.parseColor("#FFBBB4")
                )
            }
        }
        setUpView(noteList)

        btnNotes.setOnClickListener{
            noteEdittext = etNotes.text.toString()
            if(noteEdittext == "")
                Toast.makeText(this, "Please enter a Note", Toast.LENGTH_SHORT).show()
            else
            {
                noteList.add(Notes(noteEdittext))
                note_recyclerview.adapter?.notifyItemInserted(noteList.lastIndex)

                Log.d("KeyNote", "onButtonClick :${noteList.lastIndex}")
                etNotes.text.clear()


            }
        }

        setRecyclerViewItemTouchListener()
    }
    fun onClick(view:View){}

    override fun onStop() {
        super.onStop()
        val spEditor = noteSharedPreferences.edit()
            spEditor.clear()

            for (i in noteList.indices) {
                spEditor.putString("Noteitem_${i}", noteList[i].note)
            }
            spEditor.putInt("Number Note", noteList.size)
        Log.d("KeyNote", "onStop ${noteList.size}")

        spEditor.apply()

    }




    fun setUpView(list: MutableList<Notes>){

        note_recyclerview.adapter = NoteAdapter(list, this)
        note_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)


        val itemDecorator = DividerItemDecoration(this, LinearLayout.VERTICAL)
        note_recyclerview.addItemDecoration(itemDecorator)
    }

    private fun setRecyclerViewItemTouchListener() {

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                Log.d("KeyNote", "position: ${position}")
                noteList.removeAt(position)
                note_recyclerview.adapter!!.notifyDataSetChanged()
/*
                val spEditor = sharedPreferences.edit()

                val retrievedNote = sharedPreferences.getInt("Position Note ${position}", 99)
                Log.d("KeyNote", "removed at : ${retrievedNote}")
                spEditor.remove(retrievedNote.toString())
                spEditor.apply()


 */

            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(note_recyclerview)
    }



override fun noteSelect(note: Notes){


    val intent = Intent(this, SingleNoteActivity::class.java)
    intent.putExtra("note", note.note)
    startActivity(intent)


}


}
