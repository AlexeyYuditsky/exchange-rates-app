package com.alexeyyuditsky.exchange_rates.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.CurrencyLayoutBinding
import com.alexeyyuditsky.exchange_rates.network.Currency
import com.alexeyyuditsky.exchange_rates.screens.all_currencies.yesterdayCurrenciesList
import com.alexeyyuditsky.exchange_rates.utils.getCurrencyChangeRelativeToTodayAndYesterday
import com.alexeyyuditsky.exchange_rates.utils.log

class CurrenciesAdapter : RecyclerView.Adapter<CurrenciesAdapter.CurrencyViewHolder>() {

    var currencies: List<Currency> = emptyList()
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
        holder.binding.apply {
            currencyName.text = holder.itemView.context.getString(R.string.currency_name, currency.name)
            currencyValue.text = currency.value.toString()
           // differenceValue.text = getCurrencyDifference(currency, this, position)
        }
    }

    override fun getItemCount(): Int = currencies.size

    private fun getCurrencyDifference(currency: Currency, binding: CurrencyLayoutBinding, position: Int): String {
        log("${currency.name}: ${currency.value} and ${yesterdayCurrenciesList[position].name}: ${yesterdayCurrenciesList[position].value}")
        val res = getCurrencyChangeRelativeToTodayAndYesterday(currency.value, yesterdayCurrenciesList[position].value)
        log("${currency.name} = $res")
        binding.differenceValue.text = res
        return res
    }

    class CurrencyViewHolder(val binding: CurrencyLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}