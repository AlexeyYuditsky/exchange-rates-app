package com.alexeyyuditsky.exchange_rates.adapters

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.exchange_rates.R
import com.alexeyyuditsky.exchange_rates.databinding.ItemConverterBinding
import com.alexeyyuditsky.exchange_rates.model.currencies.ConverterCurrency
import com.alexeyyuditsky.exchange_rates.utils.currencyCodesAndNamesMap
import com.alexeyyuditsky.exchange_rates.utils.currencyImagesMap
import com.alexeyyuditsky.exchange_rates.utils.log
import com.bumptech.glide.Glide
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class ConverterAdapter : RecyclerView.Adapter<ConverterAdapter.Holder>() {

    var currencies = emptyList<ConverterCurrency>()
        set(newValue) {
            val diffResult = DiffUtil.calculateDiff(CurrencyDiffCallback(field, newValue), false)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currency = currencies[position]
        val context = holder.itemView.context
        with(holder.binding) {
            setCurrencyImage(currency.code, flagImageView)
            codeTextView.text = currency.code
            nameTextView.text = context.getString(R.string.currency_name_2, currencyCodesAndNamesMap[currency.code])
            valueEditText.hint = if (currency.valueShow.toFloat() == 0f) "0" else currency.valueShow
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemConverterBinding.inflate(inflater, parent, false)
        addTextChangeListener(binding.valueEditText, binding.codeTextView)
        addFocusChangeListener(binding.valueEditText)
        return Holder(binding)
    }

    override fun getItemCount(): Int = currencies.size

    private fun setCurrencyImage(code: String, currencyImageView: ImageView) {
        Glide.with(currencyImageView.context)
            .load(currencyImagesMap[code])
            .into(currencyImageView)
    }

    private fun addTextChangeListener(valueShowEditText: EditText, codeTextView: TextView) =
        valueShowEditText.addTextChangedListener { text ->
            if (text!!.startsWith('.')) {
                //log("startsWith .")
                text.replace(0, 0, "0.")
                return@addTextChangedListener
            }

            if (text.startsWith("0") && text.length == 1) {
                //log("startsWith 0 && text.length == 1")
                text.clear()
                return@addTextChangedListener
            }

            if (text.endsWith('.')) {
                //log("endsWith .")
                return@addTextChangedListener
            }

            if (text.isBlank()) {
                //log("isBlank")
                currencies = currencies.map { it.copy(valueShow = "0") }
                return@addTextChangedListener
            }

            val currency = currencies.first { codeTextView.text == it.code }
            updateCurrencies(currency, text)
            return@addTextChangedListener
        }

    private fun updateCurrencies(currency: ConverterCurrency, text: Editable) {
        //log("updateCurrencies")
        currencies = currencies.map {
            if (it.code == currency.code) return@map it
            if (it.code == "RUB") return@map it.copy(
                valueShow = (currency.valueToday.toFloat() * text.toString().toFloat()).toBigDecimal().toString()
            )
            if (currency.code == "RUB") return@map it.copy(
                valueShow = (text.toString().toFloat() / it.valueToday.toFloat()).toBigDecimal().toString()
            )
            it.copy(
                valueShow = ((currency.valueToday.toFloat() / it.valueToday.toFloat()) * text.toString()
                    .toFloat()).toBigDecimal().toString()
            )
        }
    }

    private fun Float.toBigDecimal(): BigDecimal = BigDecimal(this.toString()).setScale(4, RoundingMode.HALF_UP)

    private fun addFocusChangeListener(valueShowEditText: EditText) =
        valueShowEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus && (v as EditText).text.isNotBlank()) {
                //log("focusChangeListener")
                valueShowEditText.text.clear()
            }
        }

    class Holder(val binding: ItemConverterBinding) : RecyclerView.ViewHolder(binding.root)

}

class CurrencyDiffCallback(
    private val oldList: List<ConverterCurrency>,
    private val newList: List<ConverterCurrency>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].code == newList[newItemPosition].code
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}
