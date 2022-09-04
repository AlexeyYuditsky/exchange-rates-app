package com.alexeyyuditsky.exchangerates.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchangerates.R
import com.alexeyyuditsky.exchangerates.databinding.ItemCurrencyBinding
import com.alexeyyuditsky.exchangerates.model.currencies.Currency
import com.alexeyyuditsky.exchangerates.screens.favorite.FavoriteListener
import com.alexeyyuditsky.exchangerates.screens.currencies.CurrenciesDiffCallback
import com.alexeyyuditsky.exchangerates.utils.codesAndNamesMap
import com.alexeyyuditsky.exchangerates.utils.imagesMap
import com.bumptech.glide.Glide

class CurrenciesAdapter(
    private val favoriteListener: FavoriteListener,
    private val stopShimmer: () -> Unit
) : PagingDataAdapter<Currency, CurrenciesAdapter.Holder>(CurrenciesDiffCallback()), View.OnClickListener {

    override fun onClick(v: View) {
        val currency = v.tag as Currency
        if (v.id == R.id.favoriteImageView) {
            favoriteListener.onToggleFavoriteFlag(currency)
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currency = getItem(position) ?: return
        if (position == 0) stopShimmer()
        val context = holder.itemView.context
        with(holder.binding) {
            codeTextView.text = currency.code
            nameTextView.text = context.getString(R.string.currency_name, codesAndNamesMap[currency.code])
            valueTextView.text = context.getString(R.string.currency_value, currency.valueToday)
            rateTextView.text = currency.valueDifference

            setCurrencyImage(currency.code, flagImageView)
            setCurrencyColor(currency.valueDifference, rateTextView)
            setIsFavoriteCurrency(currency.isFavorite, favoriteImageView)

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
            .load(imagesMap[code])
            .into(currencyImageView)
    }

    private fun setCurrencyColor(value: String, textView: TextView) {
        if (value.toFloat() > 0f) {
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.green))
        } else if (value.toFloat() < 0f) {
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.red))
        } else {
            textView.setTextColor(ContextCompat.getColor(textView.context, android.R.color.tab_indicator_text))
        }
    }

    private fun setIsFavoriteCurrency(isFavorite: Boolean, favoriteImageView: ImageView) {
        if (isFavorite) {
            favoriteImageView.setImageResource(R.drawable.ic_favorite)
            favoriteImageView.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(favoriteImageView.context, android.R.color.holo_red_dark)
            )
        } else {
            favoriteImageView.setImageResource(R.drawable.ic_favorite_outline)
            favoriteImageView.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(favoriteImageView.context, android.R.color.tab_indicator_text)
            )
        }
    }

    class Holder(val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root)

}
