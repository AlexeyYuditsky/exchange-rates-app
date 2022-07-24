package com.alexeyyuditsky.exchange_rates.adapters

import com.alexeyyuditsky.exchange_rates.network.*
import com.squareup.moshi.FromJson
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

class MoshiAdapterCurrencyValues {

    @FromJson
    fun fromJson(
        responseRoot: ResponseRoot
    ): ConvertedRoot = ConvertedRoot(
        date = responseRoot.date,
        currencies = createCurrenciesList(responseRoot.currencies)
    )

    private fun createCurrenciesList(
        currencies: ResponseCurrencies
    ): List<Currency> {
        val declaredMemberProperties = currencies::class.declaredMemberProperties
        val rubleExchangeRate = findRubleExchangeRate(declaredMemberProperties, currencies)

        return declaredMemberProperties
            // exclude the ruble exchange rate from the list && exclude currency that are missing from the server
            .filter {
                it.name != "rub" && it.call(currencies) != null
            }
            .map {
                Currency(
                    name = it.name,
                    value = (rubleExchangeRate / it.call(currencies).toString().toFloat()).toString(),
                    isCryptocurrency = cryptocurrencyNames.contains(it.name)
                )
            }
    }

    private fun findRubleExchangeRate(
        declaredMemberProperties: Collection<KProperty1<out ResponseCurrencies, *>>,
        currencies: ResponseCurrencies
    ): Float {
        return declaredMemberProperties
            .first { it.name == "rub" }
            .call(currencies)
            .toString()
            .toFloat()
    }

    private companion object {
        val cryptocurrencyNames = setOf(
            "1inch",
            "aave",
            "ada",
            "algo",
            "amp",
            "ar",
            "atom",
            "avax",
            "axs",
            "bat",
            "bch",
            "bnb",
            "bsv",
            "btc",
            "btcb",
            "btg",
            "busd",
            "cake",
            "celo",
            "chz",
            "comp",
            "cro",
            "crv",
            "cvx",
            "dai",
            "dash",
            "dcr",
            "dfi",
            "doge",
            "dot",
            "egld",
            "enj",
            "eos",
            "ern",
            "etc",
            "eth",
            "fei",
            "fil",
            "flow",
            "frax",
            "ftm",
            "ftt",
            "gala",
            "ggp",
            "gno",
            "grt",
            "gt",
            "hbar",
            "hnt",
            "hot",
            "ht",
            "icp",
            "imp",
            "inj",
            "kava",
            "kcs",
            "kda",
            "klay",
            "knc",
            "ksm",
            "leo",
            "link",
            "lrc",
            "ltc",
            "luna",
            "mana",
            "matic",
            "mina",
            "miota",
            "mkr",
            "near",
            "neo",
            "nexo",
            "okb",
            "one",
            "paxg",
            "pen",
            "qnt",
            "qtum",
            "rune",
            "sand",
            "shib",
            "stx",
            "theta",
            "trx",
            "ttt",
            "tusd",
            "uni",
            "usdc",
            "usdp",
            "usdt",
            "vet",
            "waves",
            "wbtc",
            "wemix",
            "xag",
            "xdc",
            "xec",
            "xem",
            "xlm",
            "xmr",
            "xrp",
            "xtz",
            "zec",
            "zil"
        )
    }

}