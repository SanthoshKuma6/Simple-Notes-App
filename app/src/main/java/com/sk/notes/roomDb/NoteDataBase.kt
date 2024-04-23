package com.sk.notes.roomDb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class NoteDataBase:RoomDatabase(){

    abstract fun noteDao():NoteDao?

   companion object {
       private var noteDataBase:NoteDataBase?=null
       fun getNoteDataBase(context: Context): NoteDataBase? {

           if (noteDataBase == null) {
               noteDataBase = Room.databaseBuilder(context.applicationContext, NoteDataBase::class.java, "Notes")
                   .build()
           }
           return noteDataBase
       }
   }
}