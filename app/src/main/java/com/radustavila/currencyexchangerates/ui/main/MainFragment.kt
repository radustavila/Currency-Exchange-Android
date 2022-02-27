package com.radustavila.currencyexchangerates.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.radustavila.currencyexchangerates.R
import com.radustavila.currencyexchangerates.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ExchangeAdapter

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        init(inflater, container)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModelObs()
    }

    private fun init(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        adapter = ExchangeAdapter()
        binding.recyclerView.adapter = adapter
    }

    private fun setupViewModelObs() {

        viewModel.rates.observe(viewLifecycleOwner) {
            adapter.exchangeRateList = it
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.exception.observe(viewLifecycleOwner) {
            it?.let { Toast.makeText(requireContext(), getString(R.string.exception, it.message), Toast.LENGTH_LONG).show() }
        }
    }

}