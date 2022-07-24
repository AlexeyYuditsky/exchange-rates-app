package com.alexeyyuditsky.exchange_rates.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

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
    val valueTodayMinusYesterday: Float,
    val isCryptocurrency: Boolean
) {

    /*fun toCurrency(): Currency = Currency(
        name = name,
        value = value
    )

    companion object {
        fun toCurrencyDbEntity(name: String, value: Float): CurrencyDbEntity = CurrencyDbEntity(
            id = 0,
            name = name,
            value = value
        )
    }*/

}