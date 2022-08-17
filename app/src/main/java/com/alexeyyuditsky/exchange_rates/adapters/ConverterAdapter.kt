package com.alexeyyuditsky.exchange_rates.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.ItemConverterBinding
import com.alexeyyuditsky.exchange_rates.model.currencies.ConverterCurrency
import com.alexeyyuditsky.exchange_rates.utils.currencyCodesAndNamesMap
import com.alexeyyuditsky.exchange_rates.utils.currencyImagesMap
import com.alexeyyuditsky.exchange_rates.utils.log
import com.bumptech.glide.Glide

var cursorPosition = 0

class ConverterAdapter : RecyclerView.Adapter<ConverterAdapter.Holder>() {

    var currencies = emptyList<ConverterCurrency>()

    override fun onBindViewHolder(holder: Holder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            log("payloads isNotEmpty")
            if (cursorPosition != position) holder.binding.valueEditText.setText(currencies[position].valueShow)
        } else {
            log("payloads empty")
            onBindViewHolder(holder, position)
        }
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        log("onBindViewHolder basic")
        val currency = currencies[position]
        val context = holder.itemView.context
        with(holder.binding) {
            setCurrencyImage(currency.code, flagImageView)
            codeTextView.text = currency.code
            nameTextView.text = context.getString(R.string.currency_name_2, currencyCodesAndNamesMap[currency.code])
            valueEditText.setText(currency.valueShow)

            valueEditText.addTextChangedListener2(
                valueEditText,
                holder.adapterPosition,
                currencies,
                currency.code,
                this@ConverterAdapter
            )
            valueEditText.setOnTouchListener(
                valueEditText,
                holder.adapterPosition,
                currencies,
                this@ConverterAdapter
            )

            if (holder.adapterPosition == cursorPosition) valueEditText.requestFocus()
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
