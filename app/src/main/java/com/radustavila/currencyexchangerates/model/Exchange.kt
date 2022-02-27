package com.radustavila.currencyexchangerates.model

data class Exchange(
    val rates: List<Rate>,
    val pairs: List<Pair>
)