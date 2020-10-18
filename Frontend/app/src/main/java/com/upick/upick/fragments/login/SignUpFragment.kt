package com.upick.upick.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.upick.upick.R
import com.upick.upick.activities.Keys
import com.upick.upick.activities.MainActivity
import com.upick.upick.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "You're signing up!"
        binding.firstButton.apply {
            text = "SIGN UP OK >> DASHBOARD"
            setOnClickListener {
                (requireActivity() as MainActivity).sharedPreferences
                    .edit()
                    .putBoolean(Keys.LOGGED_IN, false)
                    .commit()
                findNavController().apply {
                    if (currentDestination?.id == R.id.signUpFragment) {
                        navigate(SignUpFragmentDirections.actionSignUpFragmentToDashboardFragment())
                    }
                }
            }
        }
        // Do the rest of fragment preparation here.
    }
}