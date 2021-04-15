package com.example.tracker.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.tracker.R
import com.example.tracker.databinding.FragmentSetupBinding
import com.example.tracker.utls.Constants.SHARED_PREF_KEY_FIRST_TIME_TOGGLE
import com.example.tracker.utls.Constants.SHARED_PREF_KEY_NAME
import com.example.tracker.utls.Constants.SHARED_PREF_KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment() {

    private var _binding : FragmentSetupBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @set:Inject
    var isFirstTimeOpenApp = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetupBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isFirstTimeOpenApp) {
            val navOption = NavOptions.Builder()
                .setPopUpTo(R.id.setupFragment, true)
                .build()
            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment,
                savedInstanceState,
                navOption
            )
        }
        binding.apply {
            tvContinue.setOnClickListener {
                val success = writePersonalToSharedPref()
                if (success)
                    findNavController().navigate(R.id.action_setupFragment_to_runFragment)
                else Snackbar.make(requireView(), "Please enter all the fields", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun writePersonalToSharedPref() : Boolean {
        val name = binding.etName.text.toString()
        val weight = binding.etWeight.text.toString()

        if (name.isEmpty() || weight.isEmpty()) {
            return false
        }
        sharedPreferences.edit()
            .putString(SHARED_PREF_KEY_NAME, name)
            .putFloat(SHARED_PREF_KEY_WEIGHT, weight.toFloat())
            .putBoolean(SHARED_PREF_KEY_FIRST_TIME_TOGGLE, false)
            .apply()

        val toolbarText = "Let's go $name!"
        requireActivity().findViewById<MaterialTextView>(R.id.tvToolbarTitle).text = toolbarText
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}