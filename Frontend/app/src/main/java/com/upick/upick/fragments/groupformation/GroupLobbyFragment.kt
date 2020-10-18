package com.upick.upick.fragments.groupformation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.upick.upick.activities.MainActivity
import com.upick.upick.databinding.FragmentGroupLobbyBinding

class GroupLobbyFragment : Fragment() {

    private lateinit var binding: FragmentGroupLobbyBinding
    private val args: GroupLobbyFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGroupLobbyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as MainActivity).mainActivityBinding.toolbar.title =
            "Abracadabra"  // set group name here
        binding.mainTextView.text =
            "Let's wait for everyone to arrive. Host , click ready when everyone's here! (everyone else simply waits, no button to press)"
        binding.firstButton.apply {
            text = "HOST: READY >> LOAD RESTAURANTS"
            setOnClickListener {
                findNavController().navigate(
                    GroupLobbyFragmentDirections.actionGroupLobbyFragmentToRestaurantsLoadingFragment(
                        groupId = args.groupId
                    )
                )
            }
        }
    }
}