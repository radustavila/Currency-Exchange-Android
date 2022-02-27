package com.radustavila.currencyexchangerates.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.radustavila.currencyexchangerates.R
import com.radustavila.currencyexchangerates.model.Rate

class ExchangeAdapter : RecyclerView.Adapter<ExchangeAdapter.ExchangeRateViewHolder>() {

    var exchangeRateList = emptyList<Rate>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ExchangeRateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fromTextView: TextView = itemView.findViewById(R.id.from_val)
        val rateTextView: TextView = itemView.findViewById(R.id.rate)
        val toTextView: TextView = itemView.findViewById(R.id.to_val)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateViewHolder {
        return ExchangeRateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_exchange, parent, false))
    }

    override fun onBindViewHolder(holder: ExchangeRateViewHolder, position: Int) {
        val exchangeRateItem = exchangeRateList[position]
        holder.fromTextView.text = exchangeRateItem.from
        holder.rateTextView.text = exchangeRateItem.rate
        holder.toTextView.text = exchangeRateItem.to
    }

    override fun getItemCount() = exchangeRateList.size
}