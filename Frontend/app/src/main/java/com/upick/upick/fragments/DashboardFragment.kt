package com.upick.upick.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.upick.upick.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text =
            "You're at the screen after login! Host, you can create a group here. Members will receive a notif and will jump straight to SurveyFragment"
        binding.firstButton.apply {
            text = "+ BUTTON >> CREATE GROUP"
            setOnClickListener {
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToHostSurveyFragment())
            }
        }
    }
}