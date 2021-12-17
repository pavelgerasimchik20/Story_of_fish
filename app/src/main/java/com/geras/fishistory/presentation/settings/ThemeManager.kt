package com.geras.fishistory.presentation.settings

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.geras.fishistory.R
import javax.inject.Inject

class ThemeManager @Inject constructor(private val context: Context) {
    fun changeTheme(isChecked: Boolean) {
        if (isChecked) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            context.setTheme(R.style.DarkTheme)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            context.setTheme(R.style.AppTheme)
        }
    }
}