package com.upick.upick.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.upick.upick.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "You're logging in!"
        binding.firstButton.apply {
            text = "Login >> Dashboard"
            setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToDashboardFragment())
            }
        }
        binding.secondButton.apply {
            text = "No Acc >> Sign Up"
            setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
            }
        }
        // Do the rest of fragment preparation here.
    }
}