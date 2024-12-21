package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class JournalAdapter(
    var noteTitle: ArrayList<String>,
    var noteContent: ArrayList<String>,
    var time: ArrayList<String>,

    ) : RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    inner class JournalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvnoteTitle: TextView = itemView.findViewById(R.id.noteTitle)
        var noteDesc: TextView = itemView.findViewById(R.id.noteDesc)
        var noteDesc2: TextView = itemView.findViewById(R.id.noteDesc2)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.journalcard, parent, false)
        return JournalViewHolder(view)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        holder.tvnoteTitle.text = noteTitle.get(position)
        holder.noteDesc.text = noteContent.get(position)
        holder.noteDesc2.text = time.get(position)
    }

    override fun getItemCount(): Int = noteTitle.size
}