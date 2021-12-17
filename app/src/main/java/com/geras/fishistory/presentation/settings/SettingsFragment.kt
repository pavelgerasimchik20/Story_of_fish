package com.geras.fishistory.presentation.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.geras.fishistory.R
import com.geras.fishistory.presentation.FishAdapter

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
    }
}