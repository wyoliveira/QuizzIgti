package com.wybrendon.quizzigti.view

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wybrendon.quizzigti.repository.QuizzRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class QuizzViewModel : ViewModel() {
    private val quizzRepository = QuizzRepository()

    private val _asks = MutableLiveData<HashMap<Int, String>>()
    val asksLiveData: LiveData<HashMap<Int, String>>
        get() = _asks

    private val _answers = MutableLiveData<HashMap<Int, Boolean>>()
    val answersLiveData: LiveData<HashMap<Int, Boolean>>
        get() = _answers

    private val _currentQuestion = MutableLiveData<Int>()
    val currentQuestion: LiveData<Int>
        get() = _currentQuestion

    fun loadAsksAndAnswers(context: Context) {
        val quizz = quizzRepository.loadQuizz(context)
        val asks = HashMap<Int, String>()
        val answers = HashMap<Int, Boolean>()
        for (item in quizz) {
            val index = quizz.indexOf(item)
            val askAndAnswer = item.split(";")
            asks[index] = askAndAnswer[0]
            answers[index] = (askAndAnswer[1] == " verdadeiro")
        }
        _asks.value = asks
        _answers.value = answers

    }

    fun incrementCurrentQuestion() {
        viewModelScope.launch {
            delay(1000L)
            if (_currentQuestion.value == null) {
                _currentQuestion.value = 0
            } else {
                val newValue = _currentQuestion.value!! + 1
                if (newValue < _asks.value?.size!!) {
                    _currentQuestion.value = newValue
                }
            }
        }
    }

    fun resetQuizz() {
        viewModelScope.launch {
            delay(1000L)
            _currentQuestion.value = 0
        }
    }


}