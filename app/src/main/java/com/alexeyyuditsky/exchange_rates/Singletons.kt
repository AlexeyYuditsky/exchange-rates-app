package com.alexeyyuditsky.exchange_rates

import android.content.Context
import androidx.room.Room
import com.alexeyyuditsky.exchange_rates.adapters.MoshiAdapter
import com.alexeyyuditsky.exchange_rates.network.RetrofitApi
import com.alexeyyuditsky.exchange_rates.room.AppDatabase
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Singletons {

    private lateinit var applicationContext: Context

    val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db").build()
    }

    val retrofitApi: RetrofitApi by lazy {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val moshi = Moshi.Builder()
            .add(MoshiAdapter())
            .build()

        val client = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        retrofit.create(RetrofitApi::class.java)
    }

    fun init(context: Context) {
        this.applicationContext = context
    }

}