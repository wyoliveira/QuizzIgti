package com.wybrendon.quizzigti.repository

import android.content.Context
import com.wybrendon.quizzigti.R
import java.io.IOException
import java.io.InputStream

class QuizzRepository {

    fun loadQuizz(context: Context): List<String> {
        var listAskAndAnswers = mutableListOf(context.getString(R.string.loading))
        // Function to load all questions of resource and populate local variable list
        try{

            val inputStream: InputStream? = context.resources.openRawResource(R.raw.perguntas)
            if(inputStream != null) {
                val list = inputStream.bufferedReader().readLines()
                listAskAndAnswers = list as MutableList<String>
                return listAskAndAnswers
            }

        } catch (io: IOException){
            io.printStackTrace()
        } catch (e: Exception){
            e.printStackTrace()
        }

        return listAskAndAnswers

    }

}