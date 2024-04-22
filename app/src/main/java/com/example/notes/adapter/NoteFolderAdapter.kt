package com.example.notes.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.databinding.FolderAdapterViewBinding
import com.example.notes.roomDb.NoteEntity

class NoteFolderAdapter(private var deleteClick:DeleteListener):RecyclerView.Adapter<NoteFolderAdapter.ViewHolder>() {

    private lateinit var binding:FolderAdapterViewBinding
    private var folderList = ArrayList<NoteEntity>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.folder_adapter_view, parent, false)
        return ViewHolder(itemView)
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = folderList[position]
        holder.titleFolder.text = list.title
        holder.timeStampFolder.text = "Last Updated -  "+list.timeStamp
        holder.delete.setOnClickListener{
            deleteClick.delete(list)
        }

        holder.itemView.setOnClickListener{
            deleteClick.onNoteClick(list)
        }

    }
    override fun getItemCount(): Int {
        return folderList.size
    }

    fun updateAdapter(noteEntities: List<NoteEntity>){
        folderList.clear()
        folderList.addAll(noteEntities)
        notifyDataSetChanged()

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleFolder: TextView
         var timeStampFolder: TextView
         var delete: ImageView

        init {
            titleFolder = itemView.findViewById(R.id.title_folder)
            timeStampFolder = itemView.findViewById(R.id.timeStamp_folder)
            delete = itemView.findViewById(R.id.delete)
        }
    }

    interface DeleteListener{
        fun delete(noteEntity: NoteEntity)
        fun onNoteClick(noteEntity: NoteEntity)
    }
}
