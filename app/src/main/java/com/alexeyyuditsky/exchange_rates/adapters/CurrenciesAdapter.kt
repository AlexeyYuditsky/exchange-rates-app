package com.alexeyyuditsky.exchange_rates.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.ItemCurrencyBinding
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import com.alexeyyuditsky.exchange_rates.utils.currencyCodesAndNamesMap
import com.alexeyyuditsky.exchange_rates.utils.currencyImagesMap
import com.alexeyyuditsky.exchange_rates.utils.log
import com.bumptech.glide.Glide

class CurrenciesAdapter(
    private val listener: Listener
) : PagingDataAdapter<Currency, CurrenciesAdapter.Holder>(CurrenciesDiffCallback()), View.OnClickListener {

    override fun onClick(v: View) {
        listener.onToggleFavoriteFlag(v.tag as Currency)
        (v as ImageView).setImageResource(R.drawable.ic_favorite_red)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currency = getItem(position) ?: return
        with(holder.binding) {
           // log("${currency.code} ${currency.isFavorite}")
            setCurrencyImage(currency.code, currencyImageView)
            currencyCodeTextView.text = currency.code
            currencyNameTextView.text = currencyCodesAndNamesMap[currency.code]
            currencyValueTextView.text = currency.valueToday
            currencyDifferenceTextView.text = currency.valueDifference
            setCurrencyColor(currency.valueDifference, currencyDifferenceTextView)
            setIsFavorite(favoriteImageView, currency.isFavorite)

            favoriteImageView.tag = currency
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyBinding.inflate(inflater, parent, false)
        binding.favoriteImageView.setOnClickListener(this)
        return Holder(binding)
    }

    private fun setCurrencyImage(code: String, currencyImageView: ImageView) {
        Glide.with(currencyImageView.context)
            .load(currencyImagesMap[code])
            .into(currencyImageView)
    }

    private fun setCurrencyColor(value: String, textView: TextView) {
        if (value.toFloat() > 0f) textView.setTextColor(ContextCompat.getColor(textView.context, R.color.green))
        else if (value.toFloat() < 0f) textView.setTextColor(ContextCompat.getColor(textView.context, R.color.red))
        else textView.setTextColor(ContextCompat.getColor(textView.context, android.R.color.tab_indicator_text))
    }

    private fun setIsFavorite(favoriteImageView: ImageView, isFavorite: Boolean) {
        val context = favoriteImageView.context
        if (isFavorite) {
            favoriteImageView.setImageResource(R.drawable.ic_favorite_red)
            // favoriteImageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.active))
        } else {
            favoriteImageView.setImageResource(R.drawable.ic_favorite_black)
            // favoriteImageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.inactive))
        }
    }

    class Holder(val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun onToggleFavoriteFlag(currency: Currency)
    }

}

class CurrenciesDiffCallback : DiffUtil.ItemCallback<Currency>() {
    override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean = oldItem.code == newItem.code
    override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean = oldItem == newItem
}
