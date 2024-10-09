package com.latifaumunyana.multimedia.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.latifaumunyana.multimedia.R
import com.latifaumunyana.multimedia.databinding.FragmentSettingsBinding
import com.latifaumunyana.multimedia.utils.Constants

class SettingsFragment : Fragment() {
//    var fsbbinding: FragmentSettingsBinding? = null

    var _binding: FragmentSettingsBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onResume() {
        super.onResume()
        binding.btnLogout.setOnClickListener{
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Log Out")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes"){dialog, which ->
                    logoutUser()
                    dialog.dismiss()
                }
                .setNegativeButton("No"){dialog, which ->
                    dialog.dismiss()
                }
            dialog.show()
//            logoutUser()
        }
    }

    private fun logoutUser(){
        val prefs = requireContext().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE)
        val editor = prefs.edit()
//        editor.clear()
        editor.remove(Constants.ACCESS_TOKEN)
        editor.remove(Constants.FIRST_NAME)
        editor.remove(Constants.LAST_NAME)
        editor.apply()
        requireActivity().finish()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}