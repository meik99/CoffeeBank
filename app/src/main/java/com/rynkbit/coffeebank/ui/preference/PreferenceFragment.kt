package com.rynkbit.coffeebank.ui.preference


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.rynkbit.coffeebank.R

/**
 * A simple [Fragment] subclass.
 */
class PreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference<Preference>("customer")
            ?.setOnPreferenceClickListener {
                Navigation.findNavController(activity!!, R.id.navhost)
                    .navigate(R.id.action_preferenceFragment_to_CRUDCustomerFragment)
                return@setOnPreferenceClickListener true
            }


        findPreference<Preference>("product")
            ?.setOnPreferenceClickListener {
                Navigation.findNavController(activity!!, R.id.navhost)
                    .navigate(R.id.action_preferenceFragment_to_CRUDProductFragment)
                return@setOnPreferenceClickListener true
            }

        findPreference<Preference>("transaction")
            ?.setOnPreferenceClickListener {
                Navigation.findNavController(activity!!, R.id.navhost)
                    .navigate(R.id.action_preferenceFragment_to_CRUDTransactionFragment)
                return@setOnPreferenceClickListener true
            }
    }
}
