package com.sk.notes

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sk.notes.adapter.NoteFolderAdapter
import com.sk.notes.databinding.ActivityMainBinding
import com.sk.notes.roomDb.NoteEntity
import com.sk.notes.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity(), NoteFolderAdapter.DeleteListener {

    private val tag = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var noteFolderAdapter: NoteFolderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainActivityViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[MainActivityViewModel::class.java]

        initialize()

        mainActivityViewModel.allNotes?.observe(this) {
            noteFolderAdapter.updateAdapter(it)
            noteFolderAdapter.notifyDataSetChanged()
        }

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
        }


    }

    private fun initialize() {
        noteFolderAdapter = NoteFolderAdapter(this)
        binding.recyclerViewFolder.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFolder.adapter = noteFolderAdapter

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, NotesPageActivity::class.java)
            startActivity(intent)
        }
    }

    override fun delete(noteEntity: NoteEntity) {
        showDeleteConfirmationPopup(noteEntity)
    }

    override fun onNoteClick(noteEntity: NoteEntity) {

        val intent = Intent(this,NotesPageActivity::class.java)
        intent.putExtra("noteType","EditNote")
        intent.putExtra("noteTitle",noteEntity.title)
        intent.putExtra("noteDescription",noteEntity.description)
        intent.putExtra("noteUuid",noteEntity.uuid)
        startActivity(intent)

    }

    private fun showDeleteConfirmationPopup(noteEntity: NoteEntity) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Delete Confirmation")
        alertDialogBuilder.setMessage("Are you sure you want to delete this Note?")
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            mainActivityViewModel.delete(noteEntity.uuid)
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
