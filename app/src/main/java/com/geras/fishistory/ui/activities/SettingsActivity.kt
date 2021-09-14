package com.geras.fishistory.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geras.fishistory.R
import com.geras.fishistory.settings.SettingsFragment

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings_container, SettingsFragment())
            .commit()
        }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}