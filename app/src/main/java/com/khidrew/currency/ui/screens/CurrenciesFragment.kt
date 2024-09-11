package com.khidrew.currency.ui.screens

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.khidrew.currency.R
import com.khidrew.currency.databinding.FragmentCurrenciesBinding
import com.khidrew.currency.ui.adapters.CurrenciesAdapter
import com.khidrew.currency.viewModels.CurrenciesViewModel
import com.khidrew.currency.viewModels.SharedViewModel
import com.khidrew.domain.entities.CurrencyModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CurrenciesFragment : DialogFragment() {
    private val args: CurrenciesFragmentArgs by navArgs()
    private lateinit var binding: FragmentCurrenciesBinding
    private val viewModel: CurrenciesViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var currenciesAdapter: CurrenciesAdapter

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
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }

    private fun initUI() {
        lifecycleScope.launch {
            viewModel.currencies.collectLatest {
                setupCurrenciesList(it)
            }
        }

    }

    private fun setupCurrenciesList(currencies: List<CurrencyModel>) {
        currenciesAdapter = CurrenciesAdapter(currencies) { currency ->
            if (checkIfSameCurrency(currency, args.type)) {
                if (args.type == "from") {
                    sharedViewModel.fromCurrency.value = currency
                } else {
                    sharedViewModel.toCurrency.value = currency
                }
                dismiss()
            }
        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSymbols.layoutManager = layoutManager
        binding.rvSymbols.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                layoutManager.orientation
            )
        )
        binding.rvSymbols.adapter = currenciesAdapter
        binding.svFilter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                currenciesAdapter.filter(newText ?: "")
                return true
            }
        })

    }

    private fun checkIfSameCurrency(currency: CurrencyModel, type: String): Boolean {
        return if (type == "from") {
            if (currency.symbol == sharedViewModel.toCurrency.value?.symbol) {
                Toast.makeText(requireContext(), R.string.same_currency, Toast.LENGTH_LONG).show()
                false
            } else {
                true
            }
        } else {
            if (currency.symbol == sharedViewModel.fromCurrency.value?.symbol) {
                Toast.makeText(requireContext(), R.string.same_currency, Toast.LENGTH_LONG).show()
                false
            } else {
                true
            }
        }
    }

}