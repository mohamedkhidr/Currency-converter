package com.khidrew.currency.ui.screens

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.khidrew.currency.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        // Retrieve the uiMode preference
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val isNightModeEnabled =
            sharedPreferences.getBoolean(
                getString(R.string.key_ui_mode),
                isSystemNightModed()
            )
        val prefSwitch = findPreference<SwitchPreferenceCompat>(getString(R.string.key_ui_mode))
        prefSwitch?.isChecked = isNightModeEnabled
        prefSwitch?.setOnPreferenceChangeListener { _, isEnabled ->
            if (isEnabled as Boolean) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            sharedPreferences.edit().putBoolean(getString(R.string.key_ui_mode), isEnabled)
            true
        }

    }

    private fun isSystemNightModed(): Boolean {
        val enabled = resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES
        return enabled
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        view.setBackgroundColor(resources.getColor(R.color.backgroundColor));
        return view
    }


}