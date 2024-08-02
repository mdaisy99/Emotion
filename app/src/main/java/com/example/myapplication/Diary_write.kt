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
    private var id: Long = 0
    private var isEditMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_write)

        diaryText = findViewById(R.id.diary_text)
        dateText = findViewById(R.id.daily_date)
        saveButton = findViewById(R.id.imageButton3)
        dbHelper = DiaryDatabaseHelper(this)

        // Intent에서 데이터 받아오기
        id = intent.getLongExtra("id", 0)
        val date = intent.getStringExtra("date")
        val content = intent.getStringExtra("content")

        // 수정 모드인지 확인
        isEditMode = intent.hasExtra("id")

        if (isEditMode) {
            // 수정 모드일 경우 기존 데이터 표시
            dateText.text = date
            diaryText.setText(content)
        } else {
            // 새 작성 모드일 경우 현재 날짜 표시
            val currentDate = SimpleDateFormat("yyyy.MM.dd.", Locale.getDefault()).format(Date())
            dateText.text = currentDate
        }

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

            if (isEditMode) {
                // 수정 모드일 경우 업데이트
                db.update(DiaryDatabaseHelper.TABLE_NAME, values, "${DiaryDatabaseHelper.COLUMN_ID} = ?", arrayOf(id.toString()))
            } else {
                // 새 작성 모드일 경우 삽입
                db.insert(DiaryDatabaseHelper.TABLE_NAME, null, values)
            }

            db.close()
        }
        // 내용이 비어 있더라도 화면 종료
        startActivity(Intent(this, Diary_List::class.java))
        finish()
    }
}
