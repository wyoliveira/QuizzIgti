package com.wybrendon.quizzigti.support

import android.content.Context
import android.content.SharedPreferences

class SecurityPreferences(mContext: Context) {

    private val mSharedPreferences: SharedPreferences =
        mContext.getSharedPreferences(APP_NAME_SEC_PREFERENCES, Context.MODE_PRIVATE)

    fun storeString(key: String, value: Boolean) {
        this.mSharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getStoredString(key: String): Boolean? {
        return this.mSharedPreferences.getBoolean(key, true)
    }
}