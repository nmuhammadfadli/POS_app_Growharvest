package com.features.growharvest.Transaksi

// TransactionAdapter.kt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.features.growharvest.R

class TransactionAdapter(private val transactionList: List<Transaction>, private val onItemClick: (Transaction) -> Unit) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }
    fun calculateTotalHarga(): String {
        var totalHarga = 0
        for (transaction in transactionList) {
            totalHarga += transaction.total_harga.toIntOrNull() ?: 0
        }
        return totalHarga.toString()
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactionList[position]

        // Menetapkan nilai-nilai ke TextView
        holder.txtTransactionId.text = "ID Transaksi : ${transaction.id_transaksi}"
        holder.txtTransaksiTgl.text = "Tanggal : ${transaction.tanggal_transaksi}"
        holder.txtTransaksiTotal.text = "Total Harga: ${transaction.total_harga}"
        holder.txtTransaksiAkun.text = "Id Kasir: ${transaction.id_akun}"



        // Menambahkan onClickListener untuk setiap item
        holder.itemView.setOnClickListener {
            onItemClick.invoke(transaction)
        }
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTransactionId: TextView = itemView.findViewById(R.id.txtIdTransaksi)
        val txtTransaksiTgl: TextView = itemView.findViewById(R.id.txtTglTransaksi)
        val txtTransaksiTotal : TextView = itemView.findViewById(R.id.isiTotal)
        val txtTransaksiAkun : TextView = itemView.findViewById(R.id.txtIdBarang)

    }
}
