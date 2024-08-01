package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class Diary_write : AppCompatActivity() {

    private lateinit var diaryText: EditText
    private lateinit var dateText: TextView
    private lateinit var saveButton: ImageButton
    private lateinit var dbHelper: DiaryDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_write)

        diaryText = findViewById(R.id.diary_text)
        dateText = findViewById(R.id.daily_date)
        saveButton = findViewById(R.id.imageButton3)
        dbHelper = DiaryDatabaseHelper(this)

        val currentDate = SimpleDateFormat("yyyy.MM.dd.", Locale.getDefault()).format(Date())
        dateText.text = currentDate

        saveButton.setOnClickListener {
            saveDiaryEntry()
        }
    }

    private fun saveDiaryEntry() {
        val content = diaryText.text.toString()
        val date = dateText.text.toString()

        if (content.isNotBlank()) {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(DiaryDatabaseHelper.COLUMN_DATE, date)
                put(DiaryDatabaseHelper.COLUMN_CONTENT, content)
            }
            db.insert(DiaryDatabaseHelper.TABLE_NAME, null, values)
            db.close()

            startActivity(Intent(this, Diary_List::class.java))
            finish()
        }
    }
}
