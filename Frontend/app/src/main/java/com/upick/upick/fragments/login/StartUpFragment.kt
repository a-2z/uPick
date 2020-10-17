package com.upick.upick.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.upick.upick.databinding.FragmentStartUpBinding

class StartUpFragment : Fragment() {

    private lateinit var binding: FragmentStartUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Entering app!"
        binding.firstButton.apply {
            text = "Enter App"
            setOnClickListener {
                findNavController().navigate(StartUpFragmentDirections.actionStartUpFragmentToLoginFragment())
            }
        }
        // Do the rest of fragment preparation here.
    }
}