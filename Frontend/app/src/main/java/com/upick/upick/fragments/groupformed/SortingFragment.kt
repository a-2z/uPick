package com.upick.upick.fragments.groupformed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.upick.upick.R
import com.upick.upick.activities.MainRepository
import com.upick.upick.databinding.FragmentSortingBinding
import com.upick.upick.network.VotePOSTResponse
import kotlinx.coroutines.launch

class SortingFragment : Fragment() {

    private lateinit var binding: FragmentSortingBinding
    private val args: SortingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSortingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val choices = mutableListOf<Int>()
    private fun displayChoices() {
        Snackbar.make(
            requireView(),
            "Your choices: $choices",
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction("RESET") {
                binding.firstButton.isEnabled = true
                binding.secondButton.isEnabled = true
                binding.thirdButton.isEnabled = true
                choices.clear()
                displayChoices()
            }
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        displayChoices()
        binding.mainTextView.text = "Click the three restaurants in order of preference!"
        binding.firstButton.apply {
            text = "Choice 1"
            setOnClickListener {
                isEnabled = false
                choices.add(1)
                displayChoices()
            }
        }
        binding.secondButton.apply {
            text = "Choice 2"
            setOnClickListener {
                isEnabled = false
                choices.add(2)
                displayChoices()
            }
        }
        binding.thirdButton.apply {
            text = "Choice 3"
            setOnClickListener {
                isEnabled = false
                choices.add(3)
                displayChoices()
            }
        }
        binding.fourthButton.apply {
            text = "Submit Vote"
            setOnClickListener {
                lifecycleScope.launch {
                    var response = MainRepository.postVote(
                        group = args.groupId,
                        restaurants = choices
                    )
                    response = VotePOSTResponse(
                        true,
                        "Vote submitted."
                    )  // TODO: Remove (here for development only)
                    if (response.success) {
                        findNavController().apply {
                            if (currentDestination?.id == R.id.surveyFragment) {
                                navigate(
                                    SortingFragmentDirections.actionSortingFragmentToWaitingAfterSortingFragment()
                                )
                            }
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to submit vote. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}