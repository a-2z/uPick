package com.upick.upick.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.upick.upick.R
import com.upick.upick.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToSettingsFragment())
                true
            }
            R.id.action_friends -> {
                findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToFriendsFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}