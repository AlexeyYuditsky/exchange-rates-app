package com.alexeyyuditsky.exchange_rates.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.ItemConverterBinding
import com.alexeyyuditsky.exchange_rates.databinding.ItemCurrencyBinding
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency
import com.alexeyyuditsky.exchange_rates.utils.currencyCodesAndNamesMap
import com.alexeyyuditsky.exchange_rates.utils.currencyImagesMap
import com.bumptech.glide.Glide

class ConverterAdapter : RecyclerView.Adapter<ConverterAdapter.Holder>() {

    var currencies = emptyList<Currency>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currency = currencies[position]
        val context = holder.itemView.context
        with(holder.binding) {
            setCurrencyImage(currency.code, flagImageView)
            codeTextView.text = currency.code
            nameTextView.text = context.getString(R.string.currency_name_2, currencyCodesAndNamesMap[currency.code])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemConverterBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int = currencies.size

    private fun setCurrencyImage(code: String, currencyImageView: ImageView) {
        Glide.with(currencyImageView.context)
            .load(currencyImagesMap[code])
            .into(currencyImageView)
    }

    class Holder(val binding: ItemConverterBinding) : RecyclerView.ViewHolder(binding.root)

}
