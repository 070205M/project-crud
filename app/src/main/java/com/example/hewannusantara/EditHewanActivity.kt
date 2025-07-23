package com.example.hewannusantara

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class EditHewanActivity : AppCompatActivity() {

    private lateinit var db: HewanDatabase
    private lateinit var edtNama: EditText
    private lateinit var edtLatin: EditText
    private lateinit var edtDeskripsi: EditText
    private lateinit var edtUrl: EditText
    private lateinit var imgPreview: ImageView
    private lateinit var btnSimpan: Button
    private lateinit var btnBrowse: Button

    private var hewanId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hewan)

        db = HewanDatabase.getInstance(this)

        hewanId = intent.getIntExtra("id", 0)
        if (hewanId <= 0) {
            Toast.makeText(this, "ID tidak valid!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Inisialisasi view
        edtNama = findViewById(R.id.edtNama)
        edtLatin = findViewById(R.id.edtLatin)
        edtDeskripsi = findViewById(R.id.edtDeskripsi)
        edtUrl = findViewById(R.id.edtUrl)
        imgPreview = findViewById(R.id.imgPreview)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnBrowse = findViewById(R.id.btnBrowse)

        val hewan = db.hewanDao().getById(hewanId)

        if (hewan != null) {
            edtNama.setText(hewan.nama)
            edtLatin.setText(hewan.latin)
            edtDeskripsi.setText(hewan.deskripsi)
            edtUrl.setText(hewan.imageUrl)
            Glide.with(this).load(hewan.imageUrl).into(imgPreview)
        } else {
            Toast.makeText(this, "Data tidak ditemukan!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        btnBrowse.setOnClickListener {
            val url = edtUrl.text.toString().trim()
            if (url.isNotEmpty()) {
                Glide.with(this).load(url).into(imgPreview)
            } else {
                Toast.makeText(this, "Masukkan URL gambar terlebih dahulu!", Toast.LENGTH_SHORT).show()
            }
        }

        btnSimpan.setOnClickListener {
            val nama = edtNama.text.toString().trim()
            val latin = edtLatin.text.toString().trim()
            val deskripsi = edtDeskripsi.text.toString().trim()
            val url = edtUrl.text.toString().trim()

            if (nama.isEmpty() || latin.isEmpty() || deskripsi.isEmpty() || url.isEmpty()) {
                Toast.makeText(this, "Isi semua data!", Toast.LENGTH_SHORT).show()
            } else {
                val updated = Hewan(
                    id = hewanId,
                    nama = nama,
                    latin = latin,
                    deskripsi = deskripsi,
                    imageUrl = url
                )

                db.hewanDao().update(updated)
                Toast.makeText(this, "Data diperbarui!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
