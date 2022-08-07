package com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency

@Entity(
    tableName = "currencies",
    indices = [
        Index("code", unique = true)
    ]
)
data class CurrencyDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val code: String,
    val valueToday: String,
    val valueDifference: String
) {

    fun toCurrency(): Currency = Currency(
        id = id,
        code = code,
        valueToday = valueToday,
        valueDifference = valueDifference
    )

}