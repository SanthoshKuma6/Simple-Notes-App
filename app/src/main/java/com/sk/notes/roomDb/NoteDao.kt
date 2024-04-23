package com.sk.notes.roomDb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(noteEntity:NoteEntity)

    @Query("select * from notes order by id asc")
    fun getAllNotes():LiveData<List<NoteEntity>>

    @Query("DELETE FROM notes where uuid=:uuid")
    fun deleteUuid(uuid: String)

    @Query("UPDATE notes SET title=:title ,description=:description where uuid=:uuid  ")
    fun update(title: String,description: String,uuid: String)


}