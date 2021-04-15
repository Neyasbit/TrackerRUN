package com.example.tracker.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tracker.R
import com.example.tracker.databinding.FragmentSettingsBinding
import com.example.tracker.utls.Constants
import com.example.tracker.utls.Constants.SHARED_PREF_KEY_FIRST_TIME_TOGGLE
import com.example.tracker.utls.Constants.SHARED_PREF_KEY_NAME
import com.example.tracker.utls.Constants.SHARED_PREF_KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadFieldsFromSharedPref()

        binding.btnApplyChanges.setOnClickListener {
            val success = applyChangesTOSharedPref()
            if (success)
                showSnackBar("Saved changes")
            else showSnackBar("Please enter all the fields!")
        }
    }

    private fun loadFieldsFromSharedPref() {
        val name = sharedPreferences.getString(SHARED_PREF_KEY_NAME, "")
        val weight = sharedPreferences.getFloat(SHARED_PREF_KEY_WEIGHT, 80f)

        binding.apply {
            etName.setText(name)
            etWeight.setText(weight.toString())
        }
    }

    private fun applyChangesTOSharedPref(): Boolean {
        val name = binding.etName.text.toString()
        val weight = binding.etWeight.text.toString()

        if (name.isEmpty() || weight.isEmpty())
            return false

        sharedPreferences.edit()
            .putString(SHARED_PREF_KEY_NAME, name)
            .putFloat(SHARED_PREF_KEY_WEIGHT, weight.toFloat())
            .apply()

        val toolbarText = "Let's go $name!"
        requireActivity().findViewById<MaterialTextView>(R.id.tvToolbarTitle).text = toolbarText
        return true

    }

    private fun showSnackBar(text: String) =
        Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}