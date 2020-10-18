package com.upick.upick.fragments.groupformed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.upick.upick.databinding.FragmentWaitingAfterVoteBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WaitingAfterVoteFragment : Fragment() {

    private lateinit var binding: FragmentWaitingAfterVoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWaitingAfterVoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Let's wait for your friends to finish sorting!"
        val loadingDone = false
        if (loadingDone) {
            findNavController().navigate(WaitingAfterVoteFragmentDirections.actionWaitingAfterVoteFragmentToResultFragment())
        }

        // The following uses CoRoutines to generate a 1s delay. It's just a placeholder for the
        // actual HTTP requests
        lifecycleScope.launch {
            delay(1000L)
            findNavController().navigate(WaitingAfterVoteFragmentDirections.actionWaitingAfterVoteFragmentToResultFragment())
        }
    }
}