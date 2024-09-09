package com.khidrew.currency.screens

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.khidrew.currency.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}