package com.example.pemesanantiket

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pemesanantiket.databinding.ActivityBerhasilBinding

class BerhasilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBerhasilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerhasilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nama = intent.getStringExtra("nama")
        val tanggal = intent.getStringExtra("tanggal")
        val jam = intent.getStringExtra("jam")
        val tujuan = intent.getStringExtra("tujuan")

        with(binding) {
            txtNama.text = "Nama       : $nama"
            txtJam.text = "Jam          : $jam"
            txtTanggal.text = "Tanggal   : $tanggal"
            txtTujuan.text = "Tujuan     : $tujuan"
        }
    }
}