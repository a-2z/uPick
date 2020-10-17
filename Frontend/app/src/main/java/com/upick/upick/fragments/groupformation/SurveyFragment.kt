package com.upick.upick.fragments.groupformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.upick.upick.databinding.FragmentSurveyBinding

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
        Snackbar.make(
            binding.root,
            "You came here with groupId ${args.groupId}",
            Snackbar.LENGTH_LONG
        ).show()

        binding.mainTextView.text = "Fill in your preferences, host/member!"
        binding.firstButton.apply {
            text = "Done with survey >> LOBBY"
            setOnClickListener {
                findNavController().navigate(SurveyFragmentDirections.actionSurveyFragmentToGroupLobbyFragment())
            }
        }
    }
}