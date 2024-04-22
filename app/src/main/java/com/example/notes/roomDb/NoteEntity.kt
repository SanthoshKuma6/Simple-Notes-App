package com.example.notes.roomDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Notes")
data class NoteEntity(
    @ColumnInfo(name = "title")
    val title:String,
    @ColumnInfo(name = "description")
    var description:String,
    @ColumnInfo(name = "timeStamp")
    var timeStamp:String,
    @ColumnInfo(name = "uuid")
    var uuid:String
){
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}


