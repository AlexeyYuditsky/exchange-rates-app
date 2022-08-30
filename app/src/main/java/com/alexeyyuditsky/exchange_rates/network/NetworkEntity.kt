package com.alexeyyuditsky.exchange_rates.network

import com.squareup.moshi.Json

class ResponseRoot(
    @field:Json(name = "date") val date: String,
    @field:Json(name = "usd") val currencies: ResponseCurrencies
)

class ResponseCurrencies(
    @field:Json(name = "aed") val aed: String,
    @field:Json(name = "afn") val afn: String,
    @field:Json(name = "all") val all: String,
    @field:Json(name = "amd") val amd: String,
    @field:Json(name = "ang") val ang: String,
    @field:Json(name = "aoa") val aoa: String,
    @field:Json(name = "ars") val ars: String,
    @field:Json(name = "aud") val aud: String,
    @field:Json(name = "awg") val awg: String,
    @field:Json(name = "azn") val azn: String,
    @field:Json(name = "bam") val bam: String,
    @field:Json(name = "bbd") val bbd: String,
    @field:Json(name = "bdt") val bdt: String,
    @field:Json(name = "bgn") val bgn: String,
    @field:Json(name = "bhd") val bhd: String,
    @field:Json(name = "bif") val bif: String,
    @field:Json(name = "bmd") val bmd: String,
    @field:Json(name = "bnd") val bnd: String,
    @field:Json(name = "bob") val bob: String,
    @field:Json(name = "brl") val brl: String,
    @field:Json(name = "bsd") val bsd: String,
    @field:Json(name = "btn") val btn: String,
    @field:Json(name = "bwp") val bwp: String,
    @field:Json(name = "byn") val byn: String,
    @field:Json(name = "bzd") val bzd: String,
    @field:Json(name = "cad") val cad: String,
    @field:Json(name = "cdf") val cdf: String,
    @field:Json(name = "chf") val chf: String,
    @field:Json(name = "clp") val clp: String,
    @field:Json(name = "cny") val cny: String,
    @field:Json(name = "cop") val cop: String,
    @field:Json(name = "crc") val crc: String,
    @field:Json(name = "cup") val cup: String,
    @field:Json(name = "cve") val cve: String,
    @field:Json(name = "czk") val czk: String,
    @field:Json(name = "djf") val djf: String,
    @field:Json(name = "dkk") val dkk: String,
    @field:Json(name = "dop") val dop: String,
    @field:Json(name = "dzd") val dzd: String,
    @field:Json(name = "egp") val egp: String,
    @field:Json(name = "etb") val etb: String,
    @field:Json(name = "eur") val eur: String,
    @field:Json(name = "fjd") val fjd: String,
    @field:Json(name = "fkp") val fkp: String,
    @field:Json(name = "gbp") val gbp: String,
    @field:Json(name = "gel") val gel: String,
    @field:Json(name = "ghs") val ghs: String,
    @field:Json(name = "gip") val gip: String,
    @field:Json(name = "gmd") val gmd: String,
    @field:Json(name = "gnf") val gnf: String,
    @field:Json(name = "gtq") val gtq: String,
    @field:Json(name = "gyd") val gyd: String,
    @field:Json(name = "hkd") val hkd: String,
    @field:Json(name = "hnl") val hnl: String,
    @field:Json(name = "hrk") val hrk: String,
    @field:Json(name = "htg") val htg: String,
    @field:Json(name = "huf") val huf: String,
    @field:Json(name = "idr") val idr: String,
    @field:Json(name = "ils") val ils: String,
    @field:Json(name = "inr") val inr: String,
    @field:Json(name = "iqd") val iqd: String,
    @field:Json(name = "irr") val irr: String,
    @field:Json(name = "isk") val isk: String,
    @field:Json(name = "jep") val jep: String,
    @field:Json(name = "jmd") val jmd: String,
    @field:Json(name = "jod") val jod: String,
    @field:Json(name = "jpy") val jpy: String,
    @field:Json(name = "kes") val kes: String,
    @field:Json(name = "kgs") val kgs: String,
    @field:Json(name = "khr") val khr: String,
    @field:Json(name = "kmf") val kmf: String,
    @field:Json(name = "kpw") val kpw: String,
    @field:Json(name = "krw") val krw: String,
    @field:Json(name = "kwd") val kwd: String,
    @field:Json(name = "kyd") val kyd: String,
    @field:Json(name = "kzt") val kzt: String,
    @field:Json(name = "lak") val lak: String,
    @field:Json(name = "lbp") val lbp: String,
    @field:Json(name = "lkr") val lkr: String,
    @field:Json(name = "lrd") val lrd: String,
    @field:Json(name = "lsl") val lsl: String,
    @field:Json(name = "ltl") val ltl: String,
    @field:Json(name = "lvl") val lvl: String,
    @field:Json(name = "lyd") val lyd: String,
    @field:Json(name = "mad") val mad: String,
    @field:Json(name = "mdl") val mdl: String,
    @field:Json(name = "mga") val mga: String,
    @field:Json(name = "mkd") val mkd: String,
    @field:Json(name = "mmk") val mmk: String,
    @field:Json(name = "mnt") val mnt: String,
    @field:Json(name = "mop") val mop: String,
    @field:Json(name = "mro") val mro: String,
    @field:Json(name = "mur") val mur: String,
    @field:Json(name = "mvr") val mvr: String,
    @field:Json(name = "mwk") val mwk: String,
    @field:Json(name = "mxn") val mxn: String,
    @field:Json(name = "myr") val myr: String,
    @field:Json(name = "mzn") val mzn: String,
    @field:Json(name = "nad") val nad: String,
    @field:Json(name = "ngn") val ngn: String,
    @field:Json(name = "nio") val nio: String,
    @field:Json(name = "nok") val nok: String,
    @field:Json(name = "npr") val npr: String,
    @field:Json(name = "nzd") val nzd: String,
    @field:Json(name = "omr") val omr: String,
    @field:Json(name = "pab") val pab: String,
    @field:Json(name = "pgk") val pgk: String,
    @field:Json(name = "php") val php: String,
    @field:Json(name = "pkr") val pkr: String,
    @field:Json(name = "pln") val pln: String,
    @field:Json(name = "pyg") val pyg: String,
    @field:Json(name = "qar") val qar: String,
    @field:Json(name = "ron") val ron: String,
    @field:Json(name = "rsd") val rsd: String,
    @field:Json(name = "rub") val rub: String,
    @field:Json(name = "rwf") val rwf: String,
    @field:Json(name = "sar") val sar: String,
    @field:Json(name = "sbd") val sbd: String,
    @field:Json(name = "scr") val scr: String,
    @field:Json(name = "sdg") val sdg: String,
    @field:Json(name = "sek") val sek: String,
    @field:Json(name = "sgd") val sgd: String,
    @field:Json(name = "shp") val shp: String,
    @field:Json(name = "sll") val sll: String,
    @field:Json(name = "sol") val sol: String,
    @field:Json(name = "sos") val sos: String,
    @field:Json(name = "srd") val srd: String,
    @field:Json(name = "std") val std: String,
    @field:Json(name = "svc") val svc: String,
    @field:Json(name = "syp") val syp: String,
    @field:Json(name = "szl") val szl: String,
    @field:Json(name = "thb") val thb: String,
    @field:Json(name = "tjs") val tjs: String,
    @field:Json(name = "tmt") val tmt: String,
    @field:Json(name = "tnd") val tnd: String,
    @field:Json(name = "top") val top: String,
    @field:Json(name = "try") val `try`: String,
    @field:Json(name = "ttd") val ttd: String,
    @field:Json(name = "twd") val twd: String,
    @field:Json(name = "tzs") val tzs: String,
    @field:Json(name = "uah") val uah: String,
    @field:Json(name = "ugx") val ugx: String,
    @field:Json(name = "usd") val usd: String,
    @field:Json(name = "uyu") val uyu: String,
    @field:Json(name = "uzs") val uzs: String,
    @field:Json(name = "vnd") val vnd: String,
    @field:Json(name = "vuv") val vuv: String,
    @field:Json(name = "wst") val wst: String,
    @field:Json(name = "xaf") val xaf: String,
    @field:Json(name = "xcd") val xcd: String,
    @field:Json(name = "yer") val yer: String,
    @field:Json(name = "zar") val zar: String,
    @field:Json(name = "zmw") val zmw: String,
    @field:Json(name = "zwl") val zwl: String
)

class ConvertedRoot(
    val date: String,
    val currencies: List<CurrencyNetworkEntity>
)

data class CurrencyNetworkEntity(
    val name: String,
    val value: String
)