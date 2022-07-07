package com.alexeyyuditsky.exchange_rates.model

import com.squareup.moshi.Json

class ResponseRoot(
    @field:Json(name = "date") val date: String,
    @field:Json(name = "usd") val currencies: ResponseCurrencies
)

class ResponseCurrencies(
    @field:Json(name = "1inch") val `1inch`: Float, // криптовалюта ("1inch Network")
    @field:Json(name = "aave") val aave: Float, // криптовалюта ("Aave")
    @field:Json(name = "ada") val ada: Float, // криптовалюта ("Cardano")
    @field:Json(name = "aed") val aed: Float, // валюта ("United Arab Emirates Dirham")
    @field:Json(name = "afn") val afn: Float, // валюта ("Afghan afghani")
    @field:Json(name = "algo") val algo: Float, // криптовалюта ("Algorand")
    @field:Json(name = "all") val all: Float, // валюта ("Albanian lek")
    @field:Json(name = "amd") val amd: Float, // валюта ("Armenian dram")
    @field:Json(name = "amp") val amp: Float, // криптовалюта ("Synereo")
    @field:Json(name = "ang") val ang: Float, // валюта ("Netherlands Antillean Guilder")
    @field:Json(name = "aoa") val aoa: Float, // валюта ("Angolan kwanza")
    @field:Json(name = "ar") val ar: Float, // криптовалюта ("Arweave")
    @field:Json(name = "ars") val ars: Float, // валюта ("Argentine peso)
    @field:Json(name = "atom") val atom: Float, // криптовалюта ("Atomic Coin")
    @field:Json(name = "aud") val aud: Float, // валюта ("Australian dollar")
    @field:Json(name = "avax") val avax: Float, // криптовалюта ("Avalanche")
    @field:Json(name = "awg") val awg: Float, // валюта ("Aruban florin")
    @field:Json(name = "axs") val axs: Float, // криптовалюта ("AXS")
    @field:Json(name = "azn") val azn: Float, // валюта ("Azerbaijani manat")
    @field:Json(name = "bam") val bam: Float, // валюта ("Bosnia-Herzegovina Convertible Mark")
    @field:Json(name = "bat") val bat: Float, // криптовалюта ("Basic Attention Token")
    @field:Json(name = "bbd") val bbd: Float, // валюта ("Bajan dollar")
    @field:Json(name = "bch") val bch: Float, // криптовалюта ("Bitcoin Cash")
    @field:Json(name = "bdt") val bdt: Float, // валюта ("Bangladeshi taka")
    @field:Json(name = "bgn") val bgn: Float, // валюта ("Bulgarian lev")
    @field:Json(name = "bhd") val bhd: Float, // валюта ("Bahraini dinar")
    @field:Json(name = "bif") val bif: Float, // валюта ("Burundian Franc")
    @field:Json(name = "bmd") val bmd: Float, // валюта ("Bermudan dollar")
    @field:Json(name = "bnb") val bnb: Float, // криптовалюта ("Binance Coin")
    @field:Json(name = "bnd") val bnd: Float, // валюта ("Brunei dollar")
    @field:Json(name = "bob") val bob: Float, // валюта ("Bolivian boliviano")
    @field:Json(name = "brl") val brl: Float, // валюта ("Brazilian real")
    @field:Json(name = "bsd") val bsd: Float, // валюта ("Bahamian dollar")
    @field:Json(name = "bsv") val bsv: Float, // криптовалюта ("Bitcoin SV")
    @field:Json(name = "btc") val btc: Float, // криптовалюта ("Bitcoin")
    @field:Json(name = "btcb") val btcb: Float, // криптовалюта ("Bitcoin BEP2")
    @field:Json(name = "btg") val btg: Float, // криптовалюта ("Bitcoin Gold")
    @field:Json(name = "btn") val btn: Float, // валюта ("Bhutan currency")
    @field:Json(name = "busd") val busd: Float, // криптовалюта ("Binance USD")
    @field:Json(name = "bwp") val bwp: Float, // валюта ("Botswanan Pula")
    @field:Json(name = "byn") val byn: Float, // валюта ("New Belarusian Ruble")
    @field:Json(name = "bzd") val bzd: Float, // валюта ("Belize dollar")
    @field:Json(name = "cad") val cad: Float, // валюта ("Canadian dollar")
    @field:Json(name = "cake") val cake: Float, // криптовалюта ("PancakeSwap")
    @field:Json(name = "cdf") val cdf: Float, // валюта ("Congolese franc")
    @field:Json(name = "celo") val celo: Float, // криптовалюта ("Celo")
    @field:Json(name = "chf") val chf: Float, // валюта ("Swiss franc")
    @field:Json(name = "chz") val chz: Float, // криптовалюта ("Chiliz")
    @field:Json(name = "clf") val clf: Float, // валюта ("Chilean Unit of Account (UF)")
    @field:Json(name = "clp") val clp: Float, // валюта ("Chilean peso")
    @field:Json(name = "cny") val cny: Float, // валюта ("Chinese Yuan")
    @field:Json(name = "comp") val comp: Float, // криптовалюта ("Compound Coin")
    @field:Json(name = "cop") val cop: Float, // валюта ("Colombian peso")
    @field:Json(name = "crc") val crc: Float, // валюта ("Costa Rican Colón")
    @field:Json(name = "cro") val cro: Float, // криптовалюта ("Crypto.com Chain Token")
    @field:Json(name = "crv") val crv: Float, // криптовалюта ("Cravy")
    @field:Json(name = "cup") val cup: Float, // валюта ("Cuban peso")
    @field:Json(name = "cve") val cve: Float, // валюта ("Cape Verdean escudo")
    @field:Json(name = "cvx") val cvx: Float, // криптовалюта ("Convex Finance")
    @field:Json(name = "czk") val czk: Float, // валюта ("Czech koruna")
    @field:Json(name = "dai") val dai: Float, // криптовалюта ("Dai")
    @field:Json(name = "dash") val dash: Float, // криптовалюта ("Dash")
    @field:Json(name = "dcr") val dcr: Float, // криптовалюта ("Decred")
    @field:Json(name = "dfi") val dfi: Float, // криптовалюта ("DfiStarter")
    @field:Json(name = "djf") val djf: Float, // валюта ("Djiboutian franc")
    @field:Json(name = "dkk") val dkk: Float, // валюта ("Danish krone")
    @field:Json(name = "doge") val doge: Float, // криптовалюта ("Dogecoin")
    @field:Json(name = "dop") val dop: Float, // валюта ("Dominican peso")
    @field:Json(name = "dot") val dot: Float, // криптовалюта ("Dotcoin")
    @field:Json(name = "dzd") val dzd: Float, // валюта ("Algerian dinar")
    @field:Json(name = "egld") val egld: Float, // криптовалюта ("Elrond")
    @field:Json(name = "egp") val egp: Float, // валюта ("Egyptian pound")
    @field:Json(name = "enj") val enj: Float, // криптовалюта ("Enjin Coin")
    @field:Json(name = "eos") val eos: Float, // криптовалюта ("EOS")
    @field:Json(name = "ern") val ern: Float, // криптовалюта ("Eritrean nakfa")
    @field:Json(name = "etb") val etb: Float, // валюта ("Ethiopian birr")
    @field:Json(name = "etc") val etc: Float, // криптовалюта ("Ethereum Classic")
    @field:Json(name = "eth") val eth: Float, // криптовалюта ("Ether")
    @field:Json(name = "eur") val eur: Float, // валюта ("Euro")
    @field:Json(name = "fei") val fei: Float, // криптовалюта ("Fei USD")
    @field:Json(name = "fil") val fil: Float, // криптовалюта ("FileCoin")
    @field:Json(name = "fjd") val fjd: Float, // валюта ("Fijian dollar")
    @field:Json(name = "fkp") val fkp: Float, // валюта ("Falkland Islands pound")
    @field:Json(name = "flow") val flow: Float, // криптовалюта ("Flow")
    @field:Json(name = "frax") val frax: Float, // криптовалюта ("Frax")
    @field:Json(name = "ftm") val ftm: Float, // криптовалюта ("Fantom")
    @field:Json(name = "ftt") val ftt: Float, // криптовалюта ("FarmaTrust")
    @field:Json(name = "gala") val gala: Float, // криптовалюта ("Gala")
    @field:Json(name = "gbp") val gbp: Float, // валюта ("Pound sterling")
    @field:Json(name = "gel") val gel: Float, // валюта ("Georgian lari")
    @field:Json(name = "ggp") val ggp: Float, // криптовалюта ("GGPro")
    @field:Json(name = "ghs") val ghs: Float, // валюта ("Ghanaian cedi")
    @field:Json(name = "gip") val gip: Float, // валюта ("Gibraltar pound")
    @field:Json(name = "gmd") val gmd: Float, // валюта ("Gambian dalasi")
    @field:Json(name = "gnf") val gnf: Float, // валюта ("Guinean franc")
    @field:Json(name = "gno") val gno: Float, // криптовалюта ("Gnosis")
    @field:Json(name = "grt") val grt: Float, // криптовалюта ("Golden Ratio Token")
    @field:Json(name = "gt") val gt: Float, // криптовалюта ("GateToken")
    @field:Json(name = "gtq") val gtq: Float, // валюта ("Guatemalan quetzal")
    @field:Json(name = "gyd") val gyd: Float, // валюта ("Guyanaese Dollar")
    @field:Json(name = "hbar") val hbar: Float, // криптовалюта ("Hedera")
    @field:Json(name = "hkd") val hkd: Float, // валюта ("Hong Kong dollar")
    @field:Json(name = "hnl") val hnl: Float, // валюта ("Honduran lempira")
    @field:Json(name = "hnt") val hnt: Float, // криптовалюта ("Helium")
    @field:Json(name = "hot") val hot: Float, // криптовалюта ("Hydro Protocol")
    @field:Json(name = "hrk") val hrk: Float, // валюта ("Croatian kuna")
    @field:Json(name = "ht") val ht: Float, // криптовалюта ("Huobi Token")
    @field:Json(name = "htg") val htg: Float, // валюта ("Haitian gourde")
    @field:Json(name = "huf") val huf: Float, // валюта ("Hungarian forint")
    @field:Json(name = "icp") val icp: Float, // криптовалюта ("Internet Computer")
    @field:Json(name = "idr") val idr: Float, // валюта ("Indonesian rupiah")
    @field:Json(name = "ils") val ils: Float, // валюта ("Israeli New Shekel")
    @field:Json(name = "imp") val imp: Float, // криптовалюта ("CoinIMP")
    @field:Json(name = "inj") val inj: Float, // криптовалюта ("Injective")
    @field:Json(name = "inr") val inr: Float, // валюта ("Indian rupee")
    @field:Json(name = "iqd") val iqd: Float, // валюта ("Iraqi dinar")
    @field:Json(name = "irr") val irr: Float, // валюта ("Iranian rial")
    @field:Json(name = "isk") val isk: Float, // валюта ("Icelandic króna")
    @field:Json(name = "jep") val jep: Float, // валюта ("Jersey Pound")
    @field:Json(name = "jmd") val jmd: Float, // валюта ("Jamaican dollar")
    @field:Json(name = "jod") val jod: Float, // валюта ("Jordanian dinar")
    @field:Json(name = "jpy") val jpy: Float, // валюта ("Japanese yen")
    @field:Json(name = "kava") val kava: Float, // криптовалюта ("Kava")
    @field:Json(name = "kcs") val kcs: Float, // криптовалюта ("Kucoin")
    @field:Json(name = "kda") val kda: Float, // криптовалюта ("Kadena")
    @field:Json(name = "kes") val kes: Float, // валюта ("Kenyan shilling")
    @field:Json(name = "kgs") val kgs: Float, // валюта ("Kyrgystani Som")
    @field:Json(name = "khr") val khr: Float, // валюта ("Cambodian riel")
    @field:Json(name = "klay") val klay: Float, // криптовалюта ("Klaytn")
    @field:Json(name = "kmf") val kmf: Float, // валюта ("Comorian franc")
    @field:Json(name = "knc") val knc: Float, // криптовалюта ("Kyber Network")
    @field:Json(name = "kpw") val kpw: Float, // валюта ("North Korean won")
    @field:Json(name = "krw") val krw: Float, // валюта ("South Korean won")
    @field:Json(name = "ksm") val ksm: Float, // криптовалюта ("Kusama")
    @field:Json(name = "kwd") val kwd: Float, // валюта ("Kuwaiti dinar")
    @field:Json(name = "kyd") val kyd: Float, // валюта ("Cayman Islands dollar")
    @field:Json(name = "kzt") val kzt: Float, // валюта ("Kazakhstani tenge")
    @field:Json(name = "lbp") val lbp: Float, // валюта ("Lebanese pound")
    @field:Json(name = "leo") val leo: Float, // криптовалюта ("LEOcoin")
    @field:Json(name = "link") val link: Float, // криптовалюта ("ChainLink")
    @field:Json(name = "lkr") val lkr: Float, // валюта ("Sri Lankan rupee")
    @field:Json(name = "lrc") val lrc: Float, // криптовалюта ("Loopring")
    @field:Json(name = "lrd") val lrd: Float, // валюта ("Liberian dollar")
    @field:Json(name = "lsl") val lsl: Float, // валюта ("Lesotho loti")
    @field:Json(name = "ltc") val ltc: Float, // криптовалюта ("Litecoin")
    @field:Json(name = "ltl") val ltl: Float, // валюта ("Lithuanian litas")
    @field:Json(name = "luna") val luna: Float, // криптовалюта ("Luna Coin")
    @field:Json(name = "lvl") val lvl: Float, // валюта ("Latvian lats")
    @field:Json(name = "lyd") val lyd: Float, // валюта ("Libyan dinar")
    @field:Json(name = "mad") val mad: Float, // валюта ("Moroccan dirham")
    @field:Json(name = "mana") val mana: Float, // криптовалюта ("Decentraland")
    @field:Json(name = "matic") val matic: Float, // криптовалюта ("Polygon")
    @field:Json(name = "mdl") val mdl: Float, // валюта ("Moldovan leu")
    @field:Json(name = "mga") val mga: Float, // валюта ("Malagasy ariary")
    @field:Json(name = "mina") val mina: Float, // криптовалюта ("Mina")
    @field:Json(name = "miota") val miota: Float, // криптовалюта ("IOTA")
    @field:Json(name = "mkd") val mkd: Float, // валюта ("Macedonian denar")
    @field:Json(name = "mkr") val mkr: Float, // криптовалюта ("Maker")
    @field:Json(name = "mmk") val mmk: Float, // валюта ("Myanmar Kyat")
    @field:Json(name = "mnt") val mnt: Float, // валюта ("Mongolian tugrik")
    @field:Json(name = "mop") val mop: Float, // валюта ("Macanese pataca")
    @field:Json(name = "mro") val mro: Float, // валюта ("Mauritanian ouguiya")
    @field:Json(name = "mur") val mur: Float, // валюта ("Mauritian rupee")
    @field:Json(name = "mvr") val mvr: Float, // валюта ("Maldivian rufiyaa")
    @field:Json(name = "mwk") val mwk: Float, // валюта ("Malawian kwacha")
    @field:Json(name = "mxn") val mxn: Float, // валюта ("Mexican peso")
    @field:Json(name = "myr") val myr: Float, // валюта ("Malaysian ringgit")
    @field:Json(name = "mzn") val mzn: Float, // валюта ("Mozambican Metical")
    @field:Json(name = "nad") val nad: Float, // валюта ("Namibian dollar")
    @field:Json(name = "near") val near: Float, // криптовалюта ("NEAR Protocol")
    @field:Json(name = "neo") val neo: Float, // криптовалюта ("NEO")
    @field:Json(name = "nexo") val nexo: Float, // криптовалюта ("NEXO")
    @field:Json(name = "ngn") val ngn: Float, // валюта ("Nigerian naira")
    @field:Json(name = "nio") val nio: Float, // валюта ("Nicaraguan córdoba")
    @field:Json(name = "nok") val nok: Float, // валюта ("Norwegian krone")
    @field:Json(name = "npr") val npr: Float, // валюта ("Nepalese rupee")
    @field:Json(name = "nzd") val nzd: Float, // валюта ("New Zealand dollar")
    @field:Json(name = "okb") val okb: Float, // криптовалюта ("Okex")
    @field:Json(name = "omr") val omr: Float, // валюта ("Omani rial")
    @field:Json(name = "one") val one: Float, // криптовалюта ("Menlo One")
    @field:Json(name = "pab") val pab: Float, // валюта ("Panamanian balboa")
    @field:Json(name = "paxg") val paxg: Float, // криптовалюта ("PAX Gold")
    @field:Json(name = "pen") val pen: Float, // криптовалюта ("Sol")
    @field:Json(name = "pgk") val pgk: Float, // валюта ("Papua New Guinean kina")
    @field:Json(name = "php") val php: Float, // валюта ("Philippine peso")
    @field:Json(name = "pkr") val pkr: Float, // валюта ("Pakistani rupee")
    @field:Json(name = "pln") val pln: Float, // валюта ("Poland złoty")
    @field:Json(name = "pyg") val pyg: Float, // валюта ("Paraguayan guarani")
    @field:Json(name = "qar") val qar: Float, // валюта ("Qatari Rial")
    @field:Json(name = "qnt") val qnt: Float, // криптовалюта ("Quant")
    @field:Json(name = "qtum") val qtum: Float, // криптовалюта ("QTUM")
    @field:Json(name = "ron") val ron: Float, // валюта ("Romanian leu")
    @field:Json(name = "rsd") val rsd: Float, // валюта ("Serbian dinar")
    @field:Json(name = "rub") val rub: Float, // валюта ("Russian ruble")
    @field:Json(name = "rune") val rune: Float, // криптовалюта ("THORChain (ERC20)")
    @field:Json(name = "rwf") val rwf: Float, // валюта ("Rwandan Franc")
    @field:Json(name = "sand") val sand: Float, // криптовалюта ("BeachCoin")
    @field:Json(name = "sar") val sar: Float, // валюта ("Saudi riyal")
    @field:Json(name = "sbd") val sbd: Float, // валюта ("Solomon Islands dollar")
    @field:Json(name = "scr") val scr: Float, // валюта ("Seychellois rupee")
    @field:Json(name = "sdg") val sdg: Float, // валюта ("Sudanese pound")
    @field:Json(name = "sek") val sek: Float, // валюта ("Swedish krona")
    @field:Json(name = "sgd") val sgd: Float, // валюта ("Singapore dollar")
    @field:Json(name = "shib") val shib: Float, // криптовалюта ("Shiba Inu")
    @field:Json(name = "shp") val shp: Float, // валюта ("Saint Helena pound")
    @field:Json(name = "sll") val sll: Float, // валюта ("Sierra Leonean leone")
    @field:Json(name = "sol") val sol: Float, // валюта ("Sola")
    @field:Json(name = "sos") val sos: Float, // валюта ("Somali shilling")
    @field:Json(name = "srd") val srd: Float, // валюта ("Surinamese dollar")
    @field:Json(name = "std") val std: Float, // валюта ("São Tomé and Príncipe Dobra (pre-2018)")
    @field:Json(name = "stx") val stx: Float, // криптовалюта ("Stox")
    @field:Json(name = "svc") val svc: Float, // валюта ("Salvadoran Colón")
    @field:Json(name = "syp") val syp: Float, // валюта ("Syrian pound")
    @field:Json(name = "szl") val szl: Float, // валюта ("Swazi lilangeni")
    @field:Json(name = "thb") val thb: Float, // валюта ("Thai baht")
    @field:Json(name = "theta") val theta: Float, // криптовалюта ("Theta")
    @field:Json(name = "tjs") val tjs: Float, // валюта ("Tajikistani somoni")
    @field:Json(name = "tmt") val tmt: Float, // валюта ("Turkmenistani manat")
    @field:Json(name = "tnd") val tnd: Float, // валюта ("Tunisian dinar")
    @field:Json(name = "top") val top: Float, // валюта ("Tongan Paʻanga")
    @field:Json(name = "trx") val trx: Float, // криптовалюта ("TRON")
    @field:Json(name = "try") val `try`: Float, // валюта ("Turkish lira")
    @field:Json(name = "ttd") val ttd: Float, // валюта ("Trinidad & Tobago Dollar")
    @field:Json(name = "ttt") val ttt: Float, // криптовалюта ("Tap Project")
    @field:Json(name = "tusd") val tusd: Float, // криптовалюта ("True USD")
    @field:Json(name = "twd") val twd: Float, // валюта ("New Taiwan dollar")
    @field:Json(name = "tzs") val tzs: Float, // валюта ("Tanzanian shilling")
    @field:Json(name = "uah") val uah: Float, // валюта ("Ukrainian hryvnia")
    @field:Json(name = "ugx") val ugx: Float, // валюта ("Ugandan shilling")
    @field:Json(name = "uni") val uni: Float, // криптовалюта ("Universe")
    @field:Json(name = "usd") val usd: Float, // валюта ("United States dollar")
    @field:Json(name = "usdc") val usdc: Float, // криптовалюта ("USD Coin")
    @field:Json(name = "usdp") val usdp: Float, // криптовалюта ("USDP Stablecoin")
    @field:Json(name = "usdt") val usdt: Float, // криптовалюта ("Tether")
    @field:Json(name = "uyu") val uyu: Float, // валюта ("Tether")
    @field:Json(name = "uzs") val uzs: Float, // валюта ("Uzbekistani som")
    @field:Json(name = "vef") val vef: Float, // валюта ("Sovereign Bolivar")
    @field:Json(name = "vet") val vet: Float, // криптовалюта ("Vechain")
    @field:Json(name = "vnd") val vnd: Float, // валюта ("Vietnamese dong")
    @field:Json(name = "vuv") val vuv: Float, // валюта ("Vanuatu vatu")
    @field:Json(name = "waves") val waves: Float, // криптовалюта ("Waves")
    @field:Json(name = "wemix") val wemix: Float, // криптовалюта ("WEMIX")
    @field:Json(name = "wst") val wst: Float, // валюта ("Samoan tala")
    @field:Json(name = "xaf") val xaf: Float, // валюта ("Central African CFA franc")
    @field:Json(name = "xag") val xag: Float, // криптовалюта ("Silver Ounce")
    @field:Json(name = "xcd") val xcd: Float, // валюта ("East Caribbean dollar")
    @field:Json(name = "xdc") val xdc: Float, // криптовалюта ("XDC Network")
    @field:Json(name = "xdr") val xdr: Float, // валюта ("XDC Network")
    @field:Json(name = "xec") val xec: Float, // криптовалюта ("Eternal Coin")
    @field:Json(name = "xem") val xem: Float, // криптовалюта ("NEM")
    @field:Json(name = "xlm") val xlm: Float, // криптовалюта ("Stellar")
    @field:Json(name = "xmr") val xmr: Float, // криптовалюта ("Monero")
    @field:Json(name = "xof") val xof: Float, // валюта ("West African CFA franc")
    @field:Json(name = "xpf") val xpf: Float, // валюта ("CFP franc")
    @field:Json(name = "xrp") val xrp: Float, // криптовалюта ("XRP")
    @field:Json(name = "xtz") val xtz: Float, // криптовалюта ("Tezos")
    @field:Json(name = "yer") val yer: Float, // валюта ("Yemeni rial")
    @field:Json(name = "zar") val zar: Float, // валюта ("South African rand")
    @field:Json(name = "zec") val zec: Float, // криптовалюта ("ZCash")
    @field:Json(name = "zil") val zil: Float, // криптовалюта ("Zilliqa")
    @field:Json(name = "zmw") val zmw: Float, // валюта ("Zambian Kwacha")
    @field:Json(name = "zwl") val zwl: Float, // валюта ("Zimbabwean Dollar")
)

class ConvertedRoot(
    val date: String,
    val currencies: List<Currency>
)

data class Currency(
    var name: String,
    var value: Float,
    var yesterdayValue: Float = 0f
)