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


class NotesActivity : AppCompatActivity(), NoteAdapter.NoteAdapterDelegate {
    lateinit var noteEdittext: String
    var noteList: MutableList<Notes> = mutableListOf()
    private lateinit var sharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        sharedPreferences = this.getSharedPreferences("color_app_101", Context.MODE_PRIVATE)
        val colorReceived = sharedPreferences.getString("my_app_color", "Error Color")
        val numberNote = sharedPreferences.getInt("Number Note", 0)


        for (i in 0..numberNote){
           val retrievedNote = Notes(sharedPreferences.getString("Noteitem_${i}", "Error Note")?:"")
            Log.d("KeyNote", "onStop ${i}")

            if(!(retrievedNote.note == "Error Note" || retrievedNote.note == ""))
            {
                noteList.add(retrievedNote)
            }
        }

        when (colorReceived) {
            ColorObject.COLOR_GREEN -> {
                notes_activity_layout.tvNotesTitle.setTextColor(
                    Color.parseColor("#006D05")
                )
                notes_activity_layout.etNotes.setTextColor(
                    Color.parseColor("#006D05")
                )

            }
            ColorObject.COLOR_BLUE -> {
                notes_activity_layout.tvNotesTitle.setTextColor(
                    Color.parseColor("#00009C")
                )
                notes_activity_layout.etNotes.setTextColor(
                    Color.parseColor("#00009C")
                )


            }
            ColorObject.COLOR_RED -> {
                notes_activity_layout.tvNotesTitle.setTextColor(
                    Color.parseColor("#A80000")
                )
                notes_activity_layout.etNotes.setTextColor(
                    Color.parseColor("#A80000")
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

                etNotes.text.clear()


            }
        }

       // setRecyclerViewItemTouchListener()
    }
    fun onClick(view:View){}

    override fun onStop() {
        super.onStop()
        val spEditor = sharedPreferences.edit()

        for (i in noteList.indices){
            spEditor.putString("Noteitem_${i}", noteList[i].note)
            Log.d("KeyNote", "onStop ${i}")
        }
        spEditor.putInt("Number Note", noteList.size)
        spEditor.apply()

    }
    fun setUpView(list: MutableList<Notes>){

        note_recyclerview.adapter = NoteAdapter(list, this)
        note_recyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)


        val itemDecorator = DividerItemDecoration(this, LinearLayout.VERTICAL)
        note_recyclerview.addItemDecoration(itemDecorator)
    }
/*
    private fun setRecyclerViewItemTouchListener() {

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val position = viewHolder.adapterPosition
                noteList.removeAt(position)
                note_recyclerview.adapter!!.notifyItemRemoved(position)
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(note_recyclerview)
    }

 */


override fun noteSelect(note: Notes){

    val intent = Intent(this, SingleNoteActivity::class.java)
    intent.putExtra("note", note.note)
    startActivity(intent)


}


}
