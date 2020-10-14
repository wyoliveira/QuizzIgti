package com.wybrendon.quizzigti

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wybrendon.quizzigti.support.IS_FIRST
import com.wybrendon.quizzigti.support.SecurityPreferences
import com.wybrendon.quizzigti.view.QuizzFragment
import com.wybrendon.quizzigti.view.SplashFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mSecurityPreferences: SecurityPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        mSecurityPreferences = SecurityPreferences(applicationContext)
        if(mSecurityPreferences.getStoredString(IS_FIRST)!!){
            mSecurityPreferences.storeString(IS_FIRST, false)
            setSplashWithDelay()
        } else {
            setQuizzWithoutDelay()
        }
    }
    private fun setSplashWithDelay() {
        GlobalScope.launch {
            supportFragmentManager.beginTransaction().add(R.id.frame_content, SplashFragment()).commit()
            delay(1500L)
            supportFragmentManager.beginTransaction().add(R.id.frame_content, QuizzFragment()).commit()
        }
    }
    private fun setQuizzWithoutDelay() {
        GlobalScope.launch {
            supportFragmentManager.beginTransaction().add(R.id.frame_content, QuizzFragment()).commit()
        }
    }

}