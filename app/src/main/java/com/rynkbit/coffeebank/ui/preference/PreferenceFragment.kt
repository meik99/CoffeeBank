package com.rynkbit.coffeebank.ui.preference


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.rynkbit.coffeebank.R
import com.rynkbit.coffeebank.ui.preference.backup.BackupReader
import com.rynkbit.coffeebank.ui.preference.backup.BackupWriter
import java.text.DateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class PreferenceFragment : PreferenceFragmentCompat() {
    companion object {
        val CREATE_BACKUP_REQUEST = 5
        val READ_BACKUP_REQUEST = 10
        val ASK_READ_PERMISSION = 15
        val ASK_WRITE_PERMISSION = 20
    }

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

        findPreference<Preference>("create_backup")
            ?.setOnPreferenceClickListener {
                if(checkWritePermissions()){
                    openFile(CREATE_BACKUP_REQUEST)
                } else {
                    askWritePermissions()
                }

                return@setOnPreferenceClickListener true
            }

        findPreference<Preference>("restore_backup")
            ?.setOnPreferenceClickListener {
                if(checkReadPermissions()){
                    openFile(READ_BACKUP_REQUEST)
                } else {
                    askReadPermissions()
                }

                return@setOnPreferenceClickListener true
            }
    }

    private fun askReadPermissions() {
        activity?.let {
            ActivityCompat.requestPermissions(it,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                ASK_READ_PERMISSION)
        }
    }

    private fun askWritePermissions() {
        activity?.let {
            ActivityCompat.requestPermissions(it,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                ASK_WRITE_PERMISSION)
        }
    }

    private fun checkReadPermissions(): Boolean {
        activity?.let {
            val permission = PermissionChecker
                .checkSelfPermission(it, android.Manifest.permission.READ_EXTERNAL_STORAGE)

            return permission == PermissionChecker.PERMISSION_GRANTED
        }

        return false
    }

    private fun checkWritePermissions(): Boolean {
        activity?.let {
            val permission = PermissionChecker
                .checkSelfPermission(it, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            return permission == PermissionChecker.PERMISSION_GRANTED
        }

        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == ASK_WRITE_PERMISSION) {
            if(checkWritePermissions()){
                openFile(CREATE_BACKUP_REQUEST)
            }
        } else if (requestCode == ASK_READ_PERMISSION){
            if(checkReadPermissions()){
                openFile(READ_BACKUP_REQUEST)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK){
            if(requestCode == CREATE_BACKUP_REQUEST){
                val uri = data?.data

                if(uri != null){
                    BackupWriter(context!!)
                        .write(uri)
                }
            } else if(requestCode == READ_BACKUP_REQUEST){
                val uri = data?.data

                if(uri != null){
                    BackupReader(context!!)
                        .read(uri)
                }
            }
        }
    }

    private fun openFile(requestCode: Int) {
        val intent = if(requestCode == CREATE_BACKUP_REQUEST) {
            Intent(Intent.ACTION_CREATE_DOCUMENT)
        } else {
            Intent(Intent.ACTION_OPEN_DOCUMENT)
        }

        intent.type = "application/json"

        if(requestCode == CREATE_BACKUP_REQUEST) {
            intent.putExtra(
                Intent.EXTRA_TITLE, "backup-" +
                        DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(
                            Date()
                        ) +
                        ".json"
            )
        }

        startActivityForResult(intent, requestCode)
    }


}
