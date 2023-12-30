package com.features.growharvest.Transaksi

data class Transaction(
    val id_transaksi: String,
    val total_harga: String,
    val tanggal_transaksi: String,
    val id_akun : String
)