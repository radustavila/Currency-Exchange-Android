package com.radustavila.currencyexchangerates.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.radustavila.currencyexchangerates.model.Exchange
import com.radustavila.currencyexchangerates.model.Rate
import com.radustavila.currencyexchangerates.network.CurrencyExchangeApi
import com.radustavila.currencyexchangerates.utils.Graph
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private lateinit var _graph: Graph
    private lateinit var _exchange: Exchange

    private val _rates = MutableLiveData<ArrayList<Rate>>()
    val rates: LiveData<ArrayList<Rate>> = _rates
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading
    private val _exception = MutableLiveData<Exception>().apply { value = null }
    val exception: LiveData<Exception> = _exception

    init {
        getExchange()
    }

    private fun getExchange() {
        viewModelScope.launch {
            _loading.value = true
            _exception.value = null
            try {
                _exchange = CurrencyExchangeApi.retrofitService.getExchangeObject()
                doTheMaths()
                _loading.value = false
            } catch (e: Exception) {
                _loading.value = false
                _exception.value = e
            }
        }
    }

    private fun doTheMaths() {
        val rates: ArrayList<Rate> = arrayListOf()
        _graph = Graph.createGraph(_exchange.rates)
        _exchange.pairs.forEach {
            rates.add(Rate(it.from, it.to, Graph.getRate(_graph, it.from, it.to)))
        }
        _rates.value = rates
    }
}
