package com.example.hewannusantara

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class AddHewanActivity : AppCompatActivity() {

    private lateinit var edtNama: EditText
    private lateinit var edtLatin: EditText
    private lateinit var edtDeskripsi: EditText
    private lateinit var imgPreview: ImageView
    private lateinit var btnSimpan: Button
    private lateinit var btnBrowse: Button

    private lateinit var db: HewanDatabase
    private var hewanId: Int = -1 // Untuk mode edit
    private var selectedImageUri: Uri? = null // URI dari gambar yang dipilih

    companion object {
        const val IMAGE_PICK_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_hewan)

        edtNama = findViewById(R.id.edtNama)
        edtLatin = findViewById(R.id.edtLatin)
        edtDeskripsi = findViewById(R.id.edtDeskripsi)
        imgPreview = findViewById(R.id.imgPreview)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnBrowse = findViewById(R.id.btnBrowse)

        db = HewanDatabase.getInstance(this)

        // Mode Edit
        hewanId = intent.getIntExtra("id", -1)
        if (hewanId != -1) {
            btnSimpan.text = "Update"
            edtNama.setText(intent.getStringExtra("nama"))
            edtLatin.setText(intent.getStringExtra("latin"))
            edtDeskripsi.setText(intent.getStringExtra("deskripsi"))

            val uriString = intent.getStringExtra("imageUrl") // imageUri dari database
            if (!uriString.isNullOrEmpty()) {
                selectedImageUri = Uri.parse(uriString)
                loadImage(selectedImageUri!!)
            }
        }

        btnBrowse.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }

        btnSimpan.setOnClickListener {
            val nama = edtNama.text.toString().trim()
            val latin = edtLatin.text.toString().trim()
            val deskripsi = edtDeskripsi.text.toString().trim()
            val uriString = selectedImageUri?.toString() ?: ""

            if (nama.isEmpty() || latin.isEmpty() || deskripsi.isEmpty() || uriString.isEmpty()) {
                Toast.makeText(this, "Semua data wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (hewanId == -1) {
                // Tambah data
                val hewanBaru = Hewan(nama = nama, latin = latin, deskripsi = deskripsi, imageUrl = uriString)
                db.hewanDao().insert(hewanBaru)
                Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            } else {
                // Update data
                val hewanUpdate = Hewan(id = hewanId, nama = nama, latin = latin, deskripsi = deskripsi, imageUrl = uriString)
                db.hewanDao().update(hewanUpdate)
                Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
            }

            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            selectedImageUri?.let { uri ->
                loadImage(uri)
            }
        }
    }

    private fun loadImage(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_placeholder_image)
            .error(R.drawable.ic_error_image)
            .into(imgPreview)
    }
}
