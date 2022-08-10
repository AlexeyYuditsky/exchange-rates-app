package com.alexeyyuditsky.exchange_rates.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cryptocurrencies",
    indices = [
        Index("code", unique = true)
    ]
)
data class CryptocurrencyDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val code: String,
    val valueToday: String,
    val valueDifference: Float,
    val isFavorite: Boolean
)