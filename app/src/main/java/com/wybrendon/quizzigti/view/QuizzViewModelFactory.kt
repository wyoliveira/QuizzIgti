package com.wybrendon.quizzigti.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuizzViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizzViewModel::class.java)) {
            return QuizzViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}