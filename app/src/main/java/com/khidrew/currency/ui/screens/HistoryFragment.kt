package com.khidrew.currency.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.khidrew.currency.databinding.FragmentHistoryBinding
import com.khidrew.currency.ui.adapters.ConversionsAdapter
import com.khidrew.currency.viewModels.HistoryViewModel
import com.khidrew.currency.viewModels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val viewModel:HistoryViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.conversions.collectLatest {
                binding.rvConversions.adapter = ConversionsAdapter(it) { conversionItem ->
                    sharedViewModel.getConversionBySymbols(
                        conversionItem.conversion.from.symbol,
                        conversionItem.conversion.to.symbol
                    )
                    findNavController().navigateUp()
                }
            }
        }
    }



}