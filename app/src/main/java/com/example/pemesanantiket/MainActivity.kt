package com.example.pemesanantiket

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.DialogFragment
import com.example.pemesanantiket.databinding.ActivityMainBinding
import com.example.pemesanantiket.databinding.DialogKonfirmasiBinding
import java.util.Calendar

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tujuan: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tujuan = resources.getStringArray(R.array.tujuan)

        with(binding) {
            val adapterTujuan = ArrayAdapter(this@MainActivity,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item , tujuan)
            spinnerTujuan.adapter = adapterTujuan

            var harga: Int = 0
            spinnerTujuan.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val kotaTujuan = spinnerTujuan.selectedItem.toString()
                    harga = getHargaTiket(kotaTujuan)
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }

            pilihTanggal.setOnClickListener {
                val datePicker = DatePicker()
                datePicker.show(supportFragmentManager, "datePicker")
            }

            pilihJam.setOnClickListener {
                val timePicker = TimePicker()
                timePicker.show(supportFragmentManager, "timePicker")
            }

            btnPesanTiket.setOnClickListener {
                val name = editUsernameRegister.text.toString()
                val tanggal = pilihTanggal.text.toString()
                val jam = pilihJam.text.toString()
                val tujuan = spinnerTujuan.selectedItem.toString()

                if (name.isEmpty() || tanggal.isEmpty() || jam.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                } else {
                    val bundle = Bundle().apply {
                        putString("nama", name)
                        putString("tanggal", tanggal)
                        putString("jam", jam)
                        putString("tujuan", tujuan)
                        putInt("harga", harga)
                    }
                    val dialog = DialogKonfirmasi().apply {
                        arguments = bundle
                    }
                    dialog.show(supportFragmentManager, "dialogKonfirmasi")
                }
            }
        }
    }

    private fun getHargaTiket(tujuan: String): Int {
        return when (tujuan) {
            "Jakarta" -> 200000
            "Bandung" -> 150000
            "Banten" -> 190000
            "Bekasi" -> 210000
            "Cilacap" -> 170000
            "Karawang" -> 110000
            "Semarang" -> 200000
            "Solo" -> 180000
            "Surabaya" -> 250000
            "Yogyakarta" -> 180000
            else -> 0
        }
    }

    override fun onDateSet(view: android.widget.DatePicker?,
                           year: Int,
                           month:Int,
                           day: Int) {
        val tanggal = "$day/${month + 1}/$year"
        binding.pilihTanggal.setText(tanggal)
    }

    override fun onTimeSet(view: android.widget.TimePicker?, hour: Int, minute: Int) {
        val jam = String.format("%02d:%02d", hour, minute)
        binding.pilihJam.setText(jam)
    }
}

class DatePicker: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val monthOfYear = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireActivity(),
            activity as DatePickerDialog.OnDateSetListener,
            year,
            monthOfYear,
            dayOfMonth
        )
    }
}

class TimePicker: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(
            requireActivity(),
            activity as TimePickerDialog.OnTimeSetListener,
            hourOfDay,
            minute,
            DateFormat.is24HourFormat(activity)
        )
    }
}

class DialogKonfirmasi : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val binding = DialogKonfirmasiBinding.inflate(inflater)

        val nama = arguments?.getString("nama")
        val tanggal = arguments?.getString("tanggal")
        val jam = arguments?.getString("jam")
        val tujuan = arguments?.getString("tujuan")
        val harga = arguments?.getInt("harga")

        with(binding){
            txtHarga.text = "Rp$harga"
            btnYes.setOnClickListener {
                val intent = Intent(requireActivity(), BerhasilActivity::class.java).apply {
                    putExtra("nama", nama)
                    putExtra("tanggal", tanggal)
                    putExtra("jam", jam)
                    putExtra("tujuan", tujuan)
                }
                startActivity(intent)
            }
            btnNo.setOnClickListener {
                dismiss()
            }
        }
        builder.setView(binding.root)
        return builder.create()
    }
}
