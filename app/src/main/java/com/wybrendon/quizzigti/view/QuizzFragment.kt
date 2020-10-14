package com.wybrendon.quizzigti.view

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.wybrendon.quizzigti.R
import com.wybrendon.quizzigti.support.MY_CORRECT_QUESTIONS
import com.wybrendon.quizzigti.support.TOTAL_QUESTIONS
import kotlinx.android.synthetic.main.fragment_quizz.*


class QuizzFragment : Fragment() {

    private lateinit var quizzViewModel: QuizzViewModel
    private lateinit var mediaPlayerCorrect: MediaPlayer
    private lateinit var mediaPlayerIncorrect: MediaPlayer
    private var mySelectedOption: Boolean = false
    private var myPoints: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //quizzViewModel = ViewModelProvider(this).get(QuizzViewModel::class.java)
        quizzViewModel =
            ViewModelProvider(this, QuizzViewModelFactory()).get(QuizzViewModel::class.java)
        return inflater.inflate(R.layout.fragment_quizz, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        quizzViewModel.loadAsksAndAnswers(requireContext())
        mediaPlayerCorrect = MediaPlayer.create(requireContext(), R.raw.certa_resposta_silvio)
        mediaPlayerIncorrect = MediaPlayer.create(requireContext(), R.raw.errou_faustao)
        quizzViewModel.incrementCurrentQuestion()


        quizzViewModel.currentQuestion.observe(viewLifecycleOwner, {
            tv_quizz_question.text = quizzViewModel.asksLiveData.value?.get(it)
        })


        btn_answer_false.setOnClickListener {
            mySelectedOption = false
            validate(mySelectedOption)

        }

        btn_answer_true.setOnClickListener {
            mySelectedOption = true
            validate(mySelectedOption)

        }


    }

    private fun validate(selectedOption: Boolean) {
        validateAnswer(selectedOption)
        quizzViewModel.incrementCurrentQuestion()
        if (quizzViewModel.currentQuestion.value == (quizzViewModel.asksLiveData.value?.size)?.minus(
                1
            )
        ) {
            val myCorrectAnswers = myPoints
            myPoints = 0
            quizzViewModel.resetQuizz()
            showResults(myCorrectAnswers)
        }

    }

    private fun validateAnswer(selectedOption: Boolean): Boolean {
        val result =
            (quizzViewModel.answersLiveData.value?.get(quizzViewModel.currentQuestion.value) == selectedOption)
        if (result) {
            myPoints++
            Toast.makeText(
                requireContext(),
                getString(R.string.correct_msgn_toast),
                Toast.LENGTH_SHORT
            ).show()
            executeCorrectAudio()
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.incorrect_msgn_toast),
                Toast.LENGTH_SHORT
            ).show()
            executeIncorrectAudio()
        }
        return result
    }

    private fun executeCorrectAudio() {
        mediaPlayerCorrect.start()
    }

    private fun executeIncorrectAudio() {
        mediaPlayerIncorrect.start()
    }


    private fun showResults(myPointsToResults: Int) {
        val totalQuestions = quizzViewModel.asksLiveData.value!!.size
        val intent = Intent(requireContext(), ResultActivity::class.java)
        intent.putExtra(MY_CORRECT_QUESTIONS, myPointsToResults)
        intent.putExtra(TOTAL_QUESTIONS, totalQuestions)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayerCorrect.isPlaying || mediaPlayerIncorrect.isPlaying) {
            mediaPlayerIncorrect.stop()
            mediaPlayerCorrect.stop()
        }
        mediaPlayerCorrect.release()
        mediaPlayerIncorrect.release()
    }
}