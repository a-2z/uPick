package com.upick.upick.fragments.groupformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.upick.upick.databinding.FragmentCreateGroupBinding

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
                findNavController().navigate(CreateGroupFragmentDirections.actionCreateGroupFragmentToSurveyFragment())
            }
        }
    }
}