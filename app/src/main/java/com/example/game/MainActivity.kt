package com.example.game

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var timer: CountDownTimer
    private var score = 0
    private lateinit var imgViews: List<ImageView>
    private lateinit var txt1: TextView
    private lateinit var txt2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }

        txt1 = findViewById(R.id.txt1)
        txt2 = findViewById(R.id.txt2)
        val btn = findViewById<Button>(R.id.btn)
        imgViews = listOf(
            findViewById(R.id.img1),
            findViewById(R.id.img2),
            findViewById(R.id.img3),
            findViewById(R.id.img4),
            findViewById(R.id.img5),
            findViewById(R.id.img6),
            findViewById(R.id.img7),
            findViewById(R.id.img8),
            findViewById(R.id.img9)
        )

        updateScore()
        setupGame(imgViews)
        startGame(txt1)

        btn.setOnClickListener {
            restartGame()
        }
    }

    private fun setupGame(imgViews: List<ImageView>) {
        imgViews.forEach { imgView ->
            imgView.setOnClickListener {
                if (imgView.visibility == View.VISIBLE) {
                    score++
                    updateScore()
                    imgView.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun startGame(txt1: TextView) {
        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                txt1.text = "Time: ${millisUntilFinished / 1000}"
                imgViews.forEach { imgView ->
                    imgView.visibility = View.INVISIBLE
                }
                val randomIndex = (imgViews.indices).random()
                imgViews[randomIndex].visibility = View.VISIBLE
            }

            override fun onFinish() {
                imgViews.forEach { imgView ->
                    imgView.visibility = View.INVISIBLE
                }
                txt1.text = "Finish"
                showAlertDialog()
            }
        }.start()
    }

    private fun updateScore() {
        txt2.text = "Score: $score"
    }

    private fun restartGame() {
        score = 0
        updateScore()
        timer.cancel()
        startGame(txt1)
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("Game Over")
            .setMessage("Score: $score\nWould you like to restart?")
            .setPositiveButton("Restart") { _, _ ->
                restartGame()
            }
            .setNegativeButton("Exit") { _, _ ->
                finish()
            }.show()
    }
}