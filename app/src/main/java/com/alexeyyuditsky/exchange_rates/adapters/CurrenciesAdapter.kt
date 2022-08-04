package com.alexeyyuditsky.exchange_rates.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.ItemCurrencyBinding
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import com.alexeyyuditsky.exchange_rates.utils.currencyImagesMap
import com.bumptech.glide.Glide

class CurrenciesAdapter : PagingDataAdapter<Currency, CurrenciesAdapter.Holder>(CurrenciesDiffCallback()) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currency = getItem(position) ?: return
        val context = holder.itemView.context
        with(holder.binding) {
            currencyShortNameTextView.text = currency.shortName
            currencyFullNameTextView.text = currency.fullName
            currencyValueTextView.text = currency.valueToday
            currencyDifferenceTextView.text = currency.valueDifference
            setCurrencyImage(context, currency.shortName, currencyImageView)
            setCurrencyColor(context, currency.valueDifference, currencyDifferenceTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    private fun setCurrencyImage(context: Context, shortName: String, currencyImageView: ImageView) {
        Glide.with(context)
            .load(currencyImagesMap[shortName] ?: R.drawable.ic_error)
            .placeholder(R.drawable.ic_temp)
            .error(R.drawable.ic_error)
            .into(currencyImageView)
    }

    private fun setCurrencyColor(context: Context, value: String, textView: TextView) {
        if (value.toFloat() > 0f) textView.setTextColor(context.getColor(R.color.green))
        else if (value.toFloat() < 0f) textView.setTextColor(context.getColor(R.color.red))
        else textView.setTextColor(context.getColor(android.R.color.tab_indicator_text))
    }

    class Holder(val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root)

}

class CurrenciesDiffCallback : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean = oldItem == newItem
}
