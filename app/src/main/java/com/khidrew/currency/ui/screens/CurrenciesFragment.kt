package com.khidrew.currency.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.khidrew.currency.databinding.FragmentCurrenciesBinding
import com.khidrew.currency.ui.adapters.CurrenciesAdapter
import com.khidrew.currency.viewModels.CurrenciesViewModel
import com.khidrew.currency.viewModels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrenciesFragment : DialogFragment() {
    // Use SafeArgs to access arguments
    private val args: CurrenciesFragmentArgs by navArgs()
    private lateinit var binding: FragmentCurrenciesBinding
    private val viewModel: CurrenciesViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCurrencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrenciesBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }

    private fun initUI() {
        setupCurrenciesList()
    }

    private fun setupCurrenciesList() {
        lifecycleScope.launch {
            viewModel.currencies.collectLatest {
                val adapter = CurrenciesAdapter(it) { currency ->
                    if (args.type == "from") {
                        sharedViewModel.fromCurrency.value = currency
                    } else {
                        sharedViewModel.toCurrency.value = currency
                    }
                    dismiss()
                }
                binding.rvSymbols.adapter = adapter
                binding.svFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        adapter.filter(newText ?: "")
                        return true
                    }
                })
            }
        }
    }

}