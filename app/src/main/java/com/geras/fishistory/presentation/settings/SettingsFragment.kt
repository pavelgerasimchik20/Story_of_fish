package com.geras.fishistory.presentation.settings

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import com.geras.fishistory.R
import com.geras.fishistory.data.Fish
import com.geras.fishistory.presentation.FishAdapter
import com.geras.fishistory.presentation.activities.MainActivity

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
    }
}