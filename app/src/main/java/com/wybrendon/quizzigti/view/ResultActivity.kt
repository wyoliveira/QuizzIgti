package com.wybrendon.quizzigti.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wybrendon.quizzigti.R
import com.wybrendon.quizzigti.support.MY_CORRECT_QUESTIONS
import com.wybrendon.quizzigti.support.TOTAL_QUESTIONS
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val intent = intent
        if (intent != null) {
            val myPoints = intent.getIntExtra(MY_CORRECT_QUESTIONS, 0)
            val totalPoints = intent.getIntExtra(TOTAL_QUESTIONS, 0)
            val result = myPoints.toDouble() / totalPoints.toDouble() * 100
            val text = "$result % de acertos"

            tv_result_percent.text = text

        }

        btn_restart_quizz.setOnClickListener {
            finish()
        }

    }
}