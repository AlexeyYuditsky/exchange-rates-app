package com.alexeyyuditsky.exchange_rates.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.CurrencyLayoutBinding
import com.alexeyyuditsky.exchange_rates.room.entities.UICurrency

class CurrenciesAdapter : RecyclerView.Adapter<CurrenciesAdapter.CurrencyViewHolder>() {

    var currencies: List<UICurrency> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CurrencyLayoutBinding.inflate(inflater, parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencies[position]
        val context = holder.itemView.context
        holder.binding.apply {
            currencyValueTextView.text = context.getString(R.string.currency_value, currency.valueToday)
            currencyNameTextView.text = context.getString(R.string.currency_name, currency.shortName, currency.fullName)
            currencyValueTodayMinusYesterday.text = currency.valueTodayMinusYesterday.toString()
        }
    }

    override fun getItemCount(): Int = currencies.size

    class CurrencyViewHolder(val binding: CurrencyLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}