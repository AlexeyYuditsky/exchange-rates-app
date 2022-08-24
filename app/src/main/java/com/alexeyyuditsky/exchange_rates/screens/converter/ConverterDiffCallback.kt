package com.alexeyyuditsky.exchange_rates.screens.converter

import androidx.recyclerview.widget.DiffUtil
import com.alexeyyuditsky.exchange_rates.model.currencies.ConverterCurrency

class ConverterDiffCallback(
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