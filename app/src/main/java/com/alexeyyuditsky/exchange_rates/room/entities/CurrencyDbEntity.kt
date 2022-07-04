package com.alexeyyuditsky.exchange_rates.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "currencies",
    indices = [
        Index("name", unique = true)
    ]
)
data class CurrencyDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val name: String,
    val value: Float
) {

    fun toCurrency(): Currency = Currency(
        name = name,
        value = value
    )

    companion object {
        fun toCurrencyDbEntity(name: String, value: Float): CurrencyDbEntity = CurrencyDbEntity(
            id = 0,
            name = name,
            value = value
        )
    }

}