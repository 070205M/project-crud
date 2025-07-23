package com.example.hewannusantara

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HewanAdapter(
    private val context: Context,
    private var listHewan: List<Hewan>,
    private val onEdit: (Hewan) -> Unit,
    private val onDelete: (Hewan) -> Unit
) : RecyclerView.Adapter<HewanAdapter.HewanViewHolder>() {

    inner class HewanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgHewan: ImageView = itemView.findViewById(R.id.imgHewan)
        val txtNama: TextView = itemView.findViewById(R.id.txtNama)
        val txtLatin: TextView = itemView.findViewById(R.id.txtLatin)
        val txtDeskripsi: TextView = itemView.findViewById(R.id.txtDeskripsi)
        val btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HewanViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_hewan, parent, false)
        return HewanViewHolder(view)
    }

    override fun onBindViewHolder(holder: HewanViewHolder, position: Int) {
        val hewan = listHewan[position]

        holder.txtNama.text = hewan.nama
        holder.txtLatin.text = hewan.latin
        holder.txtDeskripsi.text = hewan.deskripsi

        Glide.with(context)
            .load(hewan.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imgHewan)

        holder.btnEdit.setOnClickListener {
            onEdit(hewan)
        }

        holder.btnDelete.setOnClickListener {
            onDelete(hewan)
        }
    }

    override fun getItemCount(): Int = listHewan.size

    fun updateData(newList: List<Hewan>) {
        listHewan = newList
        notifyDataSetChanged()
    }
}
