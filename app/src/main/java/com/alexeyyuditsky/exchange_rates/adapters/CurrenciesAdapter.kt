package com.alexeyyuditsky.exchange_rates.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.ItemCurrencyBinding
import com.alexeyyuditsky.exchange_rates.model.currencies.UICurrency
import com.alexeyyuditsky.exchange_rates.utils.log

/**
 * Adapter for rendering users list in a RecyclerView.
 */
class CurrenciesAdapter : PagingDataAdapter<UICurrency, CurrenciesAdapter.Holder>(UsersDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currency = getItem(position) ?: return
        val context = holder.itemView.context
        holder.binding.apply {
            currencyValueTextView.text = context.getString(R.string.currency_value, currency.valueToday)
            currencyNameTextView.text = context.getString(R.string.currency_name, currency.shortName, currency.fullName)
            currencyValueTodayMinusYesterday.text = currency.valueTodayMinusYesterday
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    class Holder(val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root)

}

class UsersDiffCallback : DiffUtil.ItemCallback<UICurrency>() {

    override fun areItemsTheSame(oldItem: UICurrency, newItem: UICurrency): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UICurrency, newItem: UICurrency): Boolean {
        return oldItem == newItem
    }

}
