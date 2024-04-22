package com.example.notes.repository

import androidx.lifecycle.LiveData
import com.example.notes.roomDb.NoteDao
import com.example.notes.roomDb.NoteEntity


class NoteRepository(private var noteDao: NoteDao?){


     var allNotes:LiveData<List<NoteEntity>>? = noteDao?.getAllNotes()

    fun insert(noteEntity: NoteEntity){
        noteDao?.insert(noteEntity)
    }
    fun deleteUuid(uuid:String){
        noteDao?.deleteUuid(uuid)
    }
    fun update(title:String,description:String,uuid:String){
        noteDao?.update(title,description,uuid)
    }
}

