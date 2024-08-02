package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.SharedPreferences
import android.media.Image
import android.widget.ImageView
import android.widget.TextView

class EmotionCalActivity : BaseActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var imageView5: ImageView
    private lateinit var imageView6: ImageView
    private lateinit var imageView7: ImageView
    private lateinit var imageView8: ImageView
    private lateinit var imageView9: ImageView
    private lateinit var imageView10: ImageView
    private lateinit var imageView11: ImageView
    private lateinit var imageView12: ImageView
    private lateinit var imageView13: ImageView
    private lateinit var imageView14: ImageView
    private lateinit var imageView15: ImageView
    private lateinit var imageView16: ImageView
    private lateinit var imageView17: ImageView
    private lateinit var imageView18: ImageView
    private lateinit var imageView19: ImageView
    private lateinit var imageView20: ImageView
    private lateinit var imageView21: ImageView
    private lateinit var imageView22: ImageView
    private lateinit var imageView23: ImageView
    private lateinit var imageView24: ImageView
    private lateinit var imageView25: ImageView
    private lateinit var imageView26: ImageView
    private lateinit var imageView27: ImageView
    private lateinit var imageView28: ImageView
    private lateinit var imageView29: ImageView
    private lateinit var imageView30: ImageView
    private lateinit var imageView31: ImageView
    private lateinit var imageView32: ImageView
    private lateinit var imageView33: ImageView
    private lateinit var imageView34: ImageView
    private lateinit var imageView35: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_emotion_cal)
        setupBottomNavigation(R.id.fragment_Calendar)

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)

        imageView5 = findViewById(R.id.imageView5)
        imageView6 = findViewById(R.id.imageView6)
        imageView7 = findViewById(R.id.imageView7)
        imageView8 = findViewById(R.id.imageView8)
        imageView9 = findViewById(R.id.imageView9)
        imageView10 = findViewById(R.id.imageView10)
        imageView11 = findViewById(R.id.imageView11)
        imageView12 = findViewById(R.id.imageView12)
        imageView13 = findViewById(R.id.imageView13)
        imageView14 = findViewById(R.id.imageView14)
        imageView15 = findViewById(R.id.imageView15)
        imageView16 = findViewById(R.id.imageView16)
        imageView17 = findViewById(R.id.imageView17)
        imageView18 = findViewById(R.id.imageView18)
        imageView19 = findViewById(R.id.imageView19)
        imageView20 = findViewById(R.id.imageView20)
        imageView21 = findViewById(R.id.imageView21)
        imageView22 = findViewById(R.id.imageView22)
        imageView23 = findViewById(R.id.imageView23)
        imageView24 = findViewById(R.id.imageView24)
        imageView25 = findViewById(R.id.imageView25)
        imageView26 = findViewById(R.id.imageView26)
        imageView27 = findViewById(R.id.imageView27)
        imageView28 = findViewById(R.id.imageView28)
        imageView29 = findViewById(R.id.imageView29)
        imageView30 = findViewById(R.id.imageView30)
        imageView31 = findViewById(R.id.imageView31)
        imageView32 = findViewById(R.id.imageView32)
        imageView33 = findViewById(R.id.imageView33)
        imageView34 = findViewById(R.id.imageView34)
        imageView35 = findViewById(R.id.imageView35)

        updateEmotionImages()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //setupBottomNavigation()
    }

    private fun updateEmotionImages() {
        val dateEmotionMap = mapOf(
            1 to imageView5,
            2 to imageView6,
            3 to imageView7,
            4 to imageView8,
            5 to imageView9,
            6 to imageView10,
            7 to imageView11,
            8 to imageView12,
            9 to imageView13,
            10 to imageView14,
            11 to imageView15,
            12 to imageView16,
            13 to imageView17,
            14 to imageView18,
            15 to imageView19,
            16 to imageView20,
            17 to imageView21,
            18 to imageView22,
            19 to imageView23,
            20 to imageView24,
            21 to imageView25,
            22 to imageView26,
            23 to imageView27,
            24 to imageView28,
            25 to imageView29,
            26 to imageView30,
            27 to imageView31,
            28 to imageView32,
            29 to imageView33,
            30 to imageView34,
            31 to imageView35
        )

        dateEmotionMap.forEach{(date, imageView) ->
            val savedEmotion = sharedPreferences.getString("DATE_$date", null)
            if (savedEmotion != null) {
                updateImageView(imageView, savedEmotion)
                imageView.visibility = ImageView.VISIBLE
            } else {
                imageView.visibility = ImageView.INVISIBLE
            }
        }
    }

    private fun updateImageView(imageView: ImageView, emotion: String) {
        val moodImage = when(emotion) {
            "angry" -> R.drawable.angry
            "happy" -> R.drawable.happy
            "confused" -> R.drawable.anxiety
            "sad" -> R.drawable.sad
            "normal" -> R.drawable.normal
            "happy2" -> R.drawable.excited
            else -> R.drawable.normal
        }
        imageView.setImageResource(moodImage)
        imageView.visibility = ImageView.VISIBLE
    }
}