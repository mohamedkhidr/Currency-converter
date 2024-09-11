package com.khidrew.currency.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.khidrew.currency.R
import com.khidrew.currency.databinding.FragmentHomeBinding
import com.khidrew.currency.viewModels.SharedViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel:SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.history -> {
                        findNavController().navigate(R.id.action_home_to_history)
                        true
                    }

                    R.id.settings -> {
                        findNavController().navigate(R.id.action_home_to_settings)
                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.fromClickListener =
            View.OnClickListener {

                findNavController().navigate(HomeFragmentDirections.actionHomeToCurrencies("from")) }

        binding.toClickListener =
            View.OnClickListener { findNavController().navigate(HomeFragmentDirections.actionHomeToCurrencies("to")) } }
    }