package com.example.myapplication

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Diary_List : BaseActivity() {

    private lateinit var dbHelper: DiaryDatabaseHelper
    private lateinit var diaryListLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary_list)

        diaryListLayout = findViewById(R.id.dadarar)
        dbHelper = DiaryDatabaseHelper(this)

        loadDiaryEntries()
        setupBottomNavigation()
    }

    // 데이터베이스 불러오기
    private fun loadDiaryEntries() {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            DiaryDatabaseHelper.TABLE_NAME,
            arrayOf(DiaryDatabaseHelper.COLUMN_ID, DiaryDatabaseHelper.COLUMN_DATE, DiaryDatabaseHelper.COLUMN_CONTENT),
            null, null, null, null, null
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DiaryDatabaseHelper.COLUMN_ID))
                val date = getString(getColumnIndexOrThrow(DiaryDatabaseHelper.COLUMN_DATE))
                val content = getString(getColumnIndexOrThrow(DiaryDatabaseHelper.COLUMN_CONTENT))

                val entryView = TextView(this@Diary_List).apply {
                    text = "$date\n$content"
                    textSize = 16f
                    setPadding(0, 0, 0, 24)
                    maxLines = 3  // 최대 3줄까지만 표시
                    ellipsize = android.text.TextUtils.TruncateAt.END  // 내용이 길어지면 말줄임표(...)로 표시
                    setOnClickListener {
                        val intent = Intent(this@Diary_List, Diary_list_item::class.java)
                        intent.putExtra("id", id)
                        intent.putExtra("date", date)
                        intent.putExtra("content", content)
                        startActivity(intent)
                    }
                }
                diaryListLayout.addView(entryView)
            }
        }
        cursor.close()
        db.close()
    }
}
