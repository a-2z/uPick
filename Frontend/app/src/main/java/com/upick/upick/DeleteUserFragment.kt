package com.upick.upick

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.upick.upick.activities.MainRepository
import com.upick.upick.databinding.FragmentDeleteUserBinding
import com.upick.upick.network.UsersDELETEResponse
import kotlinx.coroutines.launch

class DeleteUserFragment : Fragment() {

    private lateinit var binding: FragmentDeleteUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeleteUserBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Delete user: enter your username and password."
        binding.firstButton.apply {
            text = "Delete User"
            setOnClickListener {
                lifecycleScope.launch {
                    val username = binding.usernameEditText.text.toString()
                    val password = binding.passwordEditText.text.toString()
                    var response = MainRepository.deleteUsers(username, password)
                    response = UsersDELETEResponse(true, username)  // TODO: Remove (here for development only)
                    if (response.success) {
                        findNavController().apply {
                            if (currentDestination?.id == R.id.deleteUserFragment) {
                                navigate(DeleteUserFragmentDirections.actionGlobalLoginFragment())
                            }
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Delete user failed. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

}