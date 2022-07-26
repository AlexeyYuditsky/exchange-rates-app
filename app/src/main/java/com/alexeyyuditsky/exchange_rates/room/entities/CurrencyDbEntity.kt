package com.alexeyyuditsky.exchange_rates.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.alexeyyuditsky.exchange_rates.network.RetrofitCurrenciesSource

@Entity(
    tableName = "currencies",
    indices = [
        Index("shortName", unique = true),
        Index("fullName", unique = true)
    ]
)
data class CurrencyDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val shortName: String,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val fullName: String,
    val valueToday: String,
    val valueTodayMinusYesterday: String
) {

    fun toUICurrency(): UICurrency = UICurrency(
        shortName = shortName,
        fullName = fullName,
        valueToday = valueToday,
        valueTodayMinusYesterday = valueTodayMinusYesterday
    )

}

data class UICurrency(
    val shortName: String,
    val fullName: String,
    val valueToday: String,
    val valueTodayMinusYesterday: String
)