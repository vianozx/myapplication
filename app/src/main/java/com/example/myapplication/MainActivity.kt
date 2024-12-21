package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var rvJournal: RecyclerView
    private lateinit var btnAddNote: FloatingActionButton
    private lateinit var journalAdapter: JournalAdapter
    var noteTitle = ArrayList<String>()
    var noteContent = ArrayList<String>()
    var time = ArrayList<String>()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        setContentView(R.layout.activity_main)
        // Initialize views
        rvJournal = findViewById(R.id.rvJournal)
        btnAddNote = findViewById(R.id.btnNote)

        // Set up RecyclerView
        rvJournal.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        // If the current user is not null, fetch journals with matching UID

        firestore.collection("journal")
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // Handle empty state, e.g., show a "No journals found" message
                }
                noteTitle.clear()
                noteContent.clear()
                time.clear()

                for (document in documents) {

                    document.getString("NoteTitle")?.let { noteTitle.add(it) }
                    document.getString("NoteContent")?.let { noteContent.add(it) }
                    document.getString("Time")?.let { time.add(it) }
                }
                journalAdapter.notifyDataSetChanged() // Notify adapter to update the list
            }
            .addOnFailureListener { e ->
                // Handle failure, e.g., show an error message
                e.printStackTrace()
            }


        journalAdapter = JournalAdapter(noteTitle,noteContent,time)
        rvJournal.adapter = journalAdapter

        // Set up FloatingActionButton
        btnAddNote.setOnClickListener {
            val intent = Intent(this, AddJournal::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}