package com.radustavila.currencyexchangerates.model

data class Rate(
    val from: String,
    val to: String,
    val rate: String
)
