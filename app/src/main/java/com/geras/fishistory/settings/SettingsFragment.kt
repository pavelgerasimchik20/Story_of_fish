package com.geras.fishistory.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.geras.fishistory.R

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
    }
}