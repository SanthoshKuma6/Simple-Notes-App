package com.sk.notes

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.sk.notes.databinding.ActivityNotesPageBinding
import com.sk.notes.roomDb.NoteEntity
import com.sk.notes.viewmodel.MainActivityViewModel
import java.text.SimpleDateFormat
import kotlin.random.Random

class NotesPageActivity:AppCompatActivity() {

    private val tag = "NotesPageActivity"
    private lateinit var binding:ActivityNotesPageBinding
    private lateinit var viewModel :MainActivityViewModel


    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotesPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this,R.color.white)
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightStatusBars = true }

        viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application))[MainActivityViewModel::class.java]


        val date = System.currentTimeMillis()
        val sdf = SimpleDateFormat("MMM dd yyyy, hh:mm a")
        val dateString = sdf.format(date)

        val noteType = intent.getStringExtra("noteType")
        val uuid = intent.getStringExtra("noteUuid")


        if(noteType.equals("EditNote")){
            val title = intent.getStringExtra("noteTitle")
            val description = intent.getStringExtra("noteDescription")
            binding.save.text = "UpdateNote"
            binding.title.setText(title)
            binding.description.setText(description)
        }else{
            binding.save.text = "Save"
        }


        binding.save.setOnClickListener{
            val titleText = binding.title.text.toString()
            val description = binding.description.text.toString()


            if(noteType.equals("EditNote")){
                viewModel.update(titleText,description,uuid!!)
            }else{
                viewModel.insert(NoteEntity(titleText,description,dateString,generateOrderedNDigitNumber(5).toString()))
            }
            this.finish()
        }

    }
    private fun generateOrderedNDigitNumber(value: Int): Int {
        var number = 0
        require(value > 0) { "Number of digits must be greater than 0" }
        repeat(value) {
            val digit = Random.nextInt(10)
            number = number * 10 + digit
        }
        return number
    }
}