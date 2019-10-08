package com.example.w2weekendhomework.adapter

import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.w2weekendhomework.R
import com.example.w2weekendhomework.model.Notes

class NoteAdapter(private val noteList: MutableList<Notes>, private val noteadapterDelegate: NoteAdapterDelegate) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    interface  NoteAdapterDelegate{
        fun noteSelect(note: Notes)
    }

    fun addNote(newNote : Notes){
        noteList.add(newNote)
        this.notifyItemInserted(noteList.lastIndex)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item_view_layout, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.apply {

            noteTextView.text = noteList.get(position).note
            viewGroup.setOnClickListener{
                noteadapterDelegate.noteSelect(noteList.get(position))
            }
        }
    }
    class NoteViewHolder(view: View):RecyclerView.ViewHolder(view){
        val noteTextView: TextView = view.findViewById(R.id.note_textiew)
        val viewGroup: ConstraintLayout = view.findViewById(R.id.note_itemview)

    }
}
