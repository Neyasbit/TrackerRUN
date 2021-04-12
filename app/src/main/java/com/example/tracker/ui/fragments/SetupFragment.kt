package com.example.tracker.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tracker.R
import com.example.tracker.databinding.FragmentSetupBinding
import dagger.hilt.android.AndroidEntryPoint

class SetupFragment : Fragment() {
    private var _binding : FragmentSetupBinding? = null
    private val binding get() = _binding!!

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
        binding.apply {
            tvContinue.setOnClickListener {
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}