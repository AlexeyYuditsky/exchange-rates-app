package com.alexeyyuditsky.exchange_rates.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.CurrencyLayoutBinding
import com.alexeyyuditsky.exchange_rates.model.Currency

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
            differenceValue.text = preparationColorTextView(currency, this)
        }
    }

    override fun getItemCount(): Int = currencies.size

    private fun preparationColorTextView(currency: Currency, binding: CurrencyLayoutBinding): String {
        return if (currency.yesterdayValue > 0f) {
            binding.differenceValue.setTextColor(Color.parseColor("#FF00FF0C"))
            "+${currency.yesterdayValue}"
        } else if (currency.yesterdayValue == 0f) {
            binding.differenceValue.setTextColor(Color.parseColor("#FF000000"))
            currency.yesterdayValue.toString()
        } else {
            binding.differenceValue.setTextColor(Color.parseColor("#FFFF0000"))
            currency.yesterdayValue.toString()
        }
    }

    class CurrencyViewHolder(val binding: CurrencyLayoutBinding) : RecyclerView.ViewHolder(binding.root)

}