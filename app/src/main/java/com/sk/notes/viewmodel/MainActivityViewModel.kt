package com.sk.notes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sk.notes.repository.NoteRepository
import com.sk.notes.roomDb.NoteDataBase
import com.sk.notes.roomDb.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application):AndroidViewModel(application) {

     var allNotes:LiveData<List<NoteEntity>>?=null
     private var repository:NoteRepository

    init {
        val dao = NoteDataBase.getNoteDataBase(application)?.noteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    fun insert(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(noteEntity)
    }

    fun delete(uuid:String) = viewModelScope.launch(Dispatchers.IO){
        repository.deleteUuid(uuid)
    }

    fun update(title:String,description:String,uuid:String)=viewModelScope.launch(Dispatchers.IO){
        repository.update(title,description,uuid)
    }
}