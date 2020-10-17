package com.upick.upick.fragments.groupformed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.upick.upick.databinding.FragmentRestaurantsLoadingBinding

class RestaurantsLoadingFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantsLoadingBinding

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
//            findNavController().navigate() // stopped here
        }
    }
}