package com.alexeyyuditsky.exchange_rates.screens.currencies

import androidx.recyclerview.widget.DiffUtil
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency

class CurrenciesDiffCallback : DiffUtil.ItemCallback<Currency>() {

    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean = oldItem.code == newItem.code

    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean = oldItem == newItem

}