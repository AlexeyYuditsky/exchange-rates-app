package com.alexeyyuditsky.exchangerates.model.currencies.repositories.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexeyyuditsky.exchangerates.model.currencies.Currency

@Entity(tableName = "currencies")
data class CurrencyDbEntity(
    @PrimaryKey @ColumnInfo(collate = ColumnInfo.NOCASE) val code: String,
    val valueToday: String,
    val valueDifference: String,
    val isFavorite: Boolean = false
) {

    fun toCurrency(): Currency = Currency(
        code = code,
        valueToday = valueToday,
        valueDifference = valueDifference,
        isFavorite = isFavorite
    )

}