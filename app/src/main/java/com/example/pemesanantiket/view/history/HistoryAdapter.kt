package com.azhar.pemesanantiket.view.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pemesanantiket.databinding.ListItemHistoryBinding
import com.example.pemesanantiket.model.ModelDatabase
import com.example.pemesanantiket.utils.FunctionHelper.rupiahFormat

class HistoryAdapter(private val modelDatabase: MutableList<ModelDatabase>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    fun setDataAdapter(items: List<ModelDatabase>) {
        modelDatabase.clear()
        modelDatabase.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ListItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = modelDatabase[position]

        when (data.keberangkatan) {
            "Jakarta" -> holder.tvKode1.text = "JKT"
            "Semarang" -> holder.tvKode1.text = "SRG"
            "Surabaya" -> holder.tvKode1.text = "SUB"
            "Bali" -> holder.tvKode1.text = "DPS"
        }

        when (data.tujuan) {
            "Jakarta" -> holder.tvKode2.text = "JKT"
            "Semarang" -> holder.tvKode2.text = "SRG"
            "Surabaya" -> holder.tvKode2.text = "SUB"
            "Bali" -> holder.tvKode2.text = "DPS"
        }

        holder.tvKelas.text = data.kelas
        holder.tvDate.text = data.tanggal
        holder.tvNama.text = data.namaPenumpang
        holder.tvKeberangkatan.text = data.keberangkatan
        holder.tvTujuan.text = data.tujuan
        holder.tvHargaTiket.text = rupiahFormat(data.hargaTiket)
    }

    override fun getItemCount(): Int {
        return modelDatabase.size
    }

    inner class ViewHolder(itemView: ListItemHistoryBinding) : RecyclerView.ViewHolder(itemView.root) {
        var tvKelas = itemView.tvKelas
        var tvDate = itemView.tvDate
        var tvNama = itemView.tvNama
        var tvHargaTiket = itemView.tvHargaTiket
        var tvKode1 = itemView.tvKode1
        var tvKode2 = itemView.tvKode2
        var tvKeberangkatan = itemView.tvKeberangkatan
        var tvTujuan = itemView.tvTujuan
    }

    fun setSwipeRemove(position: Int): ModelDatabase {
        return modelDatabase.removeAt(position)
    }
}