package com.upick.upick.fragments.groupformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.upick.upick.R
import com.upick.upick.activities.MainRepository
import com.upick.upick.databinding.FragmentSurveyBinding
import com.upick.upick.network.SurveyPOSTResponse
import kotlinx.android.synthetic.main.fragment_survey.view.*
import kotlinx.coroutines.launch

class SurveyFragment : Fragment() {

    private lateinit var binding: FragmentSurveyBinding
    private val args: SurveyFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSurveyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            firstTextView.text = "Distance"
            secondTextView.text = "Wait Time"
            thirdTextView.text = "Price Range"

            firstSeekBar.progress = 5
            secondSeekBar.progress = 3
            thirdSeekBar.progress = 8
        }

        binding.firstButton.apply {
            text = "Submit survey >> LOBBY"
            setOnClickListener {
                lifecycleScope.launch {
                    val distance = binding.firstSeekBar.progress
                    val waitTime = binding.secondSeekBar.progress
                    val priceRange = binding.thirdSeekBar.progress
                    var response = MainRepository.postSurvey(
                        group = args.groupId,
                        locationX = 1f,  // TODO: not implemented yet (time constraint)
                        locationY = 1f,  // TODO: not implemented yet (time constraint)
                        price = priceRange,
                        distance = distance,
                        time = waitTime,
                        preferences = listOf(
                            1,
                            2,
                            3
                        )  // TODO: not implemented yet (time constraint)
                    )
                    response = SurveyPOSTResponse(
                        true,
                        "Responses submitted"
                    )  // TODO: Remove (here for development only)
                    if (response.success) {
                        findNavController().apply {
                            if (currentDestination?.id == R.id.surveyFragment) {
                                navigate(
                                    SurveyFragmentDirections.actionSurveyFragmentToGroupLobbyFragment(
                                        groupId = args.groupId
                                    )
                                )
                            }
                        }
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to submit survey. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}