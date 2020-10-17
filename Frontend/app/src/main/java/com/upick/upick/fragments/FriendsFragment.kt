package com.upick.upick.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.upick.upick.R
import com.upick.upick.databinding.FragmentFriendsBinding

class FriendsFragment : Fragment() {

    private lateinit var binding: FragmentFriendsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFriendsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mainTextView.text =
            "Friends / join room fragment"
        binding.firstButton.apply {
            text = "+ BUTTON >> JOIN GROUP"
            setOnClickListener {
                findNavController().navigate(
                    FriendsFragmentDirections.actionFriendsFragmentToSurveyFragment(
                        groupId = 0
                    )
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_friends, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController().navigate(FriendsFragmentDirections.actionFriendsFragmentToSettingsFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}