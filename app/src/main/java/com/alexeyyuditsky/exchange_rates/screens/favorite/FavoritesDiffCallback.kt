package com.alexeyyuditsky.exchange_rates.screens.favorite

import androidx.recyclerview.widget.DiffUtil
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency

class FavoritesDiffCallback(
    private val oldList: List<Currency>,
    private val newList: List<Currency>
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