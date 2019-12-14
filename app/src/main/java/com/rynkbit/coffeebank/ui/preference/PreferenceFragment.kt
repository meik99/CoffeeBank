package com.rynkbit.coffeebank.ui.preference


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.Preference
import androidx.preference.PreferenceFragment
import androidx.preference.PreferenceFragmentCompat

import com.rynkbit.coffeebank.R

/**
 * A simple [Fragment] subclass.
 */
class PreferenceFragment : PreferenceFragmentCompat() {

    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_preference, container, false)
    }
*/
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val preference = findPreference<Preference>("create_report")
        print("")
    }
}
