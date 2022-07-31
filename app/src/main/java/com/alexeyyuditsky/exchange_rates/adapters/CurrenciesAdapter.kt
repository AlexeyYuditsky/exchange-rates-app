package com.alexeyyuditsky.exchange_rates.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.ItemCurrencyBinding
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import com.alexeyyuditsky.exchange_rates.utils.log

class CurrenciesAdapter : PagingDataAdapter<Currency, CurrenciesAdapter.Holder>(UsersDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currency = getItem(position) ?: return
        holder.binding.apply {
            currencyShortNameTextView.text = currency.shortName
            currencyFullNameTextView.text = currency.fullName
            currencyValueTextView.text = currency.valueToday
            currencyDifferenceTextView.text = currency.valueDifference
        }
        setColorForCurrencyDifferenceTextView(
            holder.itemView.context,
            currency.valueDifference,
            holder.binding.currencyDifferenceTextView,
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    private fun setColorForCurrencyDifferenceTextView(context: Context, value: String, textView: TextView) {
        if (value.toFloat() > 0f) textView.setTextColor(context.getColor(R.color.green))
        else if (value.toFloat() < 0f) textView.setTextColor(context.getColor(R.color.red))
        else textView.setTextColor(context.getColor(android.R.color.tab_indicator_text))
    }

    class Holder(val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root)

}

class UsersDiffCallback : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean = oldItem == newItem
}
