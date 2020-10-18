package com.upick.upick.fragments.groupformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.upick.upick.activities.Keys
import com.upick.upick.activities.MainActivity
import com.upick.upick.activities.MainRepository
import com.upick.upick.databinding.FragmentCreateGroupBinding
import com.upick.upick.network.CreatePOSTResponse
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

// Only the host will see this
class CreateGroupFragment : Fragment() {

    private lateinit var binding: FragmentCreateGroupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Host, select your time, and choose a group name!"
        binding.firstButton.apply {
            text = "Creates Group on Backend >> SURVEY"
            setOnClickListener {
                lifecycleScope.launch {
                    val hostId = (requireActivity() as MainActivity).sharedPreferences.getInt(
                        Keys.LOGGED_IN,
                        Int.MIN_VALUE
                    )
                    if (hostId == Int.MIN_VALUE) throw IllegalStateException("User not logged in but is inside the app")
                    val groupName = binding.groupNameEditText.text.toString()
                    val time = binding.timeEditText.text.toString()
                    var response = MainRepository.postCreate(hostId, groupName, time)
                    response = CreatePOSTResponse(true, 42)
                    if (response.success) {
                        findNavController().navigate(
                            CreateGroupFragmentDirections.actionCreateGroupFragmentToSurveyFragment(
                                groupId = response.data!!
                            )
                        )
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to create group. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}