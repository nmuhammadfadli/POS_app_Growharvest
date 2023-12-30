package com.features.growharvest.Transaksi

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.features.growharvest.R
import org.json.JSONObject

class RiwayatTransaksi : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_transaksi)

        recyclerView = findViewById(R.id.rv_riwayat)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Panggil metode untuk melakukan pemanggilan API
        fetchDataFromApi()
    }

    private fun fetchDataFromApi() {
        val url = "https://growharvest.my.id/API/cobagettransaksi.php" // Ganti dengan URL API sesuai kebutuhan

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                // Handle respons JSON
                try {
                    val transactions = parseJsonResponse(response)
                    showDataInRecyclerView(transactions)
                } catch (e: Exception) {
                    Log.e("RiwayatTransaksi", "Error parsing JSON response: ${e.message}", e)
                }
            },
            { error ->
                // Handle error
                Log.e("RiwayatTransaksi", "Volley error: ${error.message}", error)
            })

        // Tambahkan request ke queue Volley
        Volley.newRequestQueue(this).add(request)
    }

    private fun parseJsonResponse(response: JSONObject): List<Transaction> {
        val transactions = mutableListOf<Transaction>()

        // Lakukan parsing respons JSON ke dalam objek Transaction
        val keys = response.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val transactionJson = response.optJSONObject(key)
            if (transactionJson != null) {
                val transaction = Transaction(
                    transactionJson.optString("id_transaksi", ""),
                    transactionJson.optString("total_harga", ""),
                    transactionJson.optString("tanggal_transaksi", ""),
                    transactionJson.optString("id_akun", "")
                )
                transactions.add(transaction)
            }
        }

        return transactions
    }

    private fun showDataInRecyclerView(transactions: List<Transaction>) {
        transactionAdapter = TransactionAdapter(transactions) {
            // Tambahkan aksi yang ingin dilakukan ketika item diklik di sini, jika diperlukan
        }
        recyclerView.adapter = transactionAdapter

        // Set total harga pada TextView yang diinginkan
        val totalHargaTextView: TextView = findViewById(R.id.ttlPenjualan)
        totalHargaTextView.text = "Omset : Rp ${transactionAdapter.calculateTotalHarga()}"
    }
}

