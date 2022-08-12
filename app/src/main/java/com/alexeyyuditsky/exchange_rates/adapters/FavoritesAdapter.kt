package com.alexeyyuditsky.exchange_rates.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.ItemCurrencyBinding
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import com.alexeyyuditsky.exchange_rates.screens.favorite_currencies.FavoritesDiffCallback
import com.alexeyyuditsky.exchange_rates.utils.currencyCodesAndNamesMap
import com.alexeyyuditsky.exchange_rates.utils.currencyImagesMap
import com.bumptech.glide.Glide

class FavoritesAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<FavoritesAdapter.Holder>(), View.OnClickListener {

    private var currencies: List<Currency> = emptyList()

    override fun onClick(v: View) {
        val currency = v.tag as Currency
        if (v.id == R.id.favoriteImageView)
            listener.onToggleFavoriteFlag(currency)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currency = currencies[position]
        val context = holder.itemView.context
        with(holder.binding) {
            codeTextView.text = currency.code
            nameTextView.text = context.getString(R.string.currency_name, currencyCodesAndNamesMap[currency.code])
            valueTextView.text = context.getString(R.string.currency_value, currency.valueToday)
            rateTextView.text = currency.valueDifference

            setCurrencyImage(currency.code, pictureImageView)
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

    override fun getItemCount(): Int = currencies.size

    private fun setCurrencyImage(code: String, currencyImageView: ImageView) {
        Glide.with(currencyImageView.context)
            .load(currencyImagesMap[code])
            .into(currencyImageView)
    }

    private fun setCurrencyColor(value: String, textView: TextView) {
        if (value.toFloat() > 0f)
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.green))
        else if (value.toFloat() < 0f)
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.red))
        else
            textView.setTextColor(ContextCompat.getColor(textView.context, android.R.color.tab_indicator_text))
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

    fun renderSettings(currencies: List<Currency>) {
        val diffResult = DiffUtil.calculateDiff(FavoritesDiffCallback(this.currencies, currencies))
        this.currencies = currencies
        diffResult.dispatchUpdatesTo(this)
    }

    class Holder(val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root)

    interface Listener {
        fun onToggleFavoriteFlag(currency: Currency)
    }

}
