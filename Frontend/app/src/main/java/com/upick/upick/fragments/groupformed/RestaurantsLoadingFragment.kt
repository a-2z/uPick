package com.upick.upick.fragments.groupformed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.upick.upick.databinding.FragmentRestaurantsLoadingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RestaurantsLoadingFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantsLoadingBinding
    private val args: RestaurantsLoadingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantsLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text = "Loading your favourite restaurants!"
        val loadingDone = false
        if (loadingDone) {
            findNavController().navigate(
                RestaurantsLoadingFragmentDirections.actionRestaurantsLoadingFragmentToVoteFragment(
                    groupId = args.groupId
                )
            )
        }

        // The following uses CoRoutines to generate a 1s delay. It's just a placeholder for the
        // actual HTTP requests
        lifecycleScope.launch {
            delay(1000L)
            findNavController().navigate(
                RestaurantsLoadingFragmentDirections.actionRestaurantsLoadingFragmentToVoteFragment(
                    groupId = args.groupId
                )
            )
        }
    }
}