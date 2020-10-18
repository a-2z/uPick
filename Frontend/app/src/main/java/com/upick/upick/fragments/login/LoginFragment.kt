package com.upick.upick.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.upick.upick.R
import com.upick.upick.activities.Keys
import com.upick.upick.activities.MainActivity
import com.upick.upick.activities.MainRepository
import com.upick.upick.databinding.FragmentLoginBinding
import com.upick.upick.network.SignInPOSTResponse
import com.upick.upick.network.UsersPOSTResponse
import kotlinx.coroutines.launch

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
        binding.mainTextView.text = "You're not logged in yet."
        binding.firstButton.apply {
            text = "Login >> Dashboard"
            setOnClickListener {
                lifecycleScope.launch {
                    val username = binding.usernameEditText.text.toString()
                    val password = binding.passwordEditText.text.toString()
                    var response = MainRepository.postSignIn(username, password)
                    response = SignInPOSTResponse(true, 1)  // TODO: Remove (here for development only)
                    if (response.success) {
                        (requireActivity() as MainActivity).sharedPreferences
                            .edit()
                            .putInt(Keys.LOGGED_IN, response.data!!)
                            .commit()
                        findNavController().apply {
                            if (currentDestination?.id == R.id.loginFragment) {
                                navigate(LoginFragmentDirections.actionLoginFragmentToDashboardFragment())
                            }
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Login failed. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).sharedPreferences
            .edit()
            .putInt(Keys.LOGGED_IN, Int.MIN_VALUE)
            .commit()
    }
}