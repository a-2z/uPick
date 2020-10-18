package com.upick.upick.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.upick.upick.R
import com.upick.upick.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Settings Fragment!"
        binding.thirdButton.apply {
            text = "Logout"
            setOnClickListener {
                findNavController().apply {
                    if (currentDestination?.id == R.id.settingsFragment) {
                        navigate(SettingsFragmentDirections.actionGlobalLoginFragment())
                    }
                }
            }
        }
    }

}