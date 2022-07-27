package com.alexeyyuditsky.exchange_rates.model.currencies.repositories.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.alexeyyuditsky.exchange_rates.model.currencies.Currency

@Entity(
    tableName = "currencies",
    indices = [
        Index("shortName", unique = true),
        Index("fullName", unique = true)
    ]
)
data class CurrencyDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val shortName: String,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val fullName: String,
    val valueToday: String,
    val valueTodayMinusYesterday: String
) {

    fun toCurrency(): Currency = Currency(
        id = id,
        shortName = shortName,
        fullName = fullName,
        valueToday = valueToday,
        valueTodayMinusYesterday = valueTodayMinusYesterday
    )

}