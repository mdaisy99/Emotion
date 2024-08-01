package com.example.myapplication

import android.database.Cursor
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DiaryListActivity : AppCompatActivity() {

    private lateinit var dbHelper: DiaryDatabaseHelper
    private lateinit var diaryListLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_list)

        diaryListLayout = findViewById(R.id.dadarar)
        dbHelper = DiaryDatabaseHelper(this)

        loadDiaryEntries()
    }

    private fun loadDiaryEntries() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            DiaryDatabaseHelper.TABLE_NAME,
            arrayOf(DiaryDatabaseHelper.COLUMN_DATE, DiaryDatabaseHelper.COLUMN_CONTENT),
            null, null, null, null, null
        )

        with(cursor) {
            while (moveToNext()) {
                val date = getString(getColumnIndexOrThrow(DiaryDatabaseHelper.COLUMN_DATE))
                val content = getString(getColumnIndexOrThrow(DiaryDatabaseHelper.COLUMN_CONTENT)).take(100)

                val entryView = TextView(this@DiaryListActivity).apply {
                    text = "$date\n$content"
                    textSize = 16f
                    setPadding(0, 0, 0, 24)
                }
                diaryListLayout.addView(entryView)
            }
        }
        cursor.close()
        db.close()
    }
}
