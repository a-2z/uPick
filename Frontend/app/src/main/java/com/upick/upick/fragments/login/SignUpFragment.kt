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
import com.upick.upick.databinding.FragmentSignUpBinding
import com.upick.upick.network.UsersPOSTResponse
import kotlinx.coroutines.launch

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
                lifecycleScope.launch {
                    val username = binding.usernameEditText.text.toString()
                    val password = binding.passwordEditText.text.toString()
//                    val response = MainRepository.postUsers(username, password)
                    val response = UsersPOSTResponse(true, 1)
                    if (response.success) {
                        (requireActivity() as MainActivity).sharedPreferences
                            .edit()
                            .putInt(Keys.LOGGED_IN, response.data!!)
                            .commit()
                        findNavController().apply {
                            if (currentDestination?.id == R.id.signUpFragment) {
                                navigate(SignUpFragmentDirections.actionSignUpFragmentToDashboardFragment())
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
        // Do the rest of fragment preparation here.
    }
}