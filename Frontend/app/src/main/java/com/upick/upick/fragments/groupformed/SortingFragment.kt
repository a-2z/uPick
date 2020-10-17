package com.upick.upick.fragments.groupformed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.upick.upick.databinding.FragmentSortingBinding

class SortingFragment : Fragment() {

    private lateinit var binding: FragmentSortingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSortingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun finishSortingIfDone() {
        val allThreePressed = true  // to implement logic and send backend an array of the ranking
        if (allThreePressed) {
            findNavController().navigate(SortingFragmentDirections.actionSortingFragmentToWaitingAfterSortingFragment())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Click the three restaurants in order of preference!"
        binding.firstButton.apply {
            text = "Choice 1"
            setOnClickListener { finishSortingIfDone() }
        }
        binding.secondButton.apply {
            text = "Choice 2"
            setOnClickListener { finishSortingIfDone() }
        }
        binding.thirdButton.apply {
            text = "Choice 3"
            setOnClickListener { finishSortingIfDone() }
        }
    }
}