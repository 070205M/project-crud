package com.example.hewannusantara

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var db: HewanDatabase
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HewanAdapter
    private lateinit var btnTambah: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = HewanDatabase.getInstance(this)
        recyclerView = findViewById(R.id.rvHewan)
        btnTambah = findViewById(R.id.btnTambah)

        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = HewanAdapter(
            this,
            listOf(),
            onEdit = { hewan ->
                // Kirim data lengkap ke AddHewanActivity
                val intent = Intent(this, AddHewanActivity::class.java).apply {
                    putExtra("id", hewan.id)
                    putExtra("nama", hewan.nama)
                    putExtra("latin", hewan.latin)
                    putExtra("deskripsi", hewan.deskripsi)
                    putExtra("imageUrl", hewan.imageUrl)
                }
                startActivity(intent)
            },
            onDelete = { hewan ->
                db.hewanDao().delete(hewan)
                loadData()
            }
        )

        recyclerView.adapter = adapter

        btnTambah.setOnClickListener {
            val intent = Intent(this, AddHewanActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        val newData = db.hewanDao().getAll()
        adapter.updateData(newData)
    }
}
