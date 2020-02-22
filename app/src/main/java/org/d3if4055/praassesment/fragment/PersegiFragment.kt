package org.d3if4055.praassesment.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.d3if4055.praassesment.MainActivity

import org.d3if4055.praassesment.R
import org.d3if4055.praassesment.databinding.FragmentPersegiBinding

class PersegiFragment : Fragment() {
    private lateinit var binding: FragmentPersegiBinding
    private var luas = 0.0
    private var keliling = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        judul()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_persegi, container, false)

        binding.btnHitung.setOnClickListener { checkInput() }
        binding.btnShare.setOnClickListener { sendEmail() }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun judul() {
        val getActivity = activity!! as MainActivity
        getActivity.supportActionBar?.title = "Persegi Panjang"
    }

    private fun checkInput() {
        when {
            binding.tfPanjang.text.isEmpty() -> {
                Toast.makeText(activity, "Panjang harus diisi!", Toast.LENGTH_SHORT).show()
            }
            binding.tfLebar.text.isEmpty() -> {
                Toast.makeText(activity, "Lebar harus diisi!", Toast.LENGTH_SHORT).show()
            }
            else -> hitungHasil()
        }
    }

    private fun visibleResult() {
        binding.tvJudulKeliling.visibility = View.VISIBLE
        binding.tvJudulLuas.visibility = View.VISIBLE
        binding.tvKeliling.visibility = View.VISIBLE
        binding.tvLuas.visibility = View.VISIBLE
        binding.viewHasil.visibility = View.VISIBLE
        binding.btnShare.visibility = View.VISIBLE
    }

    private fun hitungHasil() {
        var panjang = binding.tfPanjang.text.toString().toDouble()
        var lebar = binding.tfLebar.text.toString().toDouble()
        luas = panjang * lebar
        keliling = 2 * (panjang + lebar)

        // visible result
        visibleResult()

        // inject result to text view
        binding.tvLuas.text = "${luas}"
        binding.tvKeliling.text = "${keliling}"
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        val subject = "Hasil Pehitungan Persegi Panjang"
        val message = """
            Panjang = ${binding.tfPanjang.text}
            Lebar = ${binding.tfLebar.text}
            Luas = ${binding.tvLuas.text}
            Keliling = ${binding.tvKeliling.text}
        """.trimIndent()
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putDouble("luas", luas)
        outState.putDouble("keliling", keliling)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            luas = savedInstanceState.getDouble("luas")
            keliling = savedInstanceState.getDouble("keliling")

            // visible result on rotate
            visibleResult()

            // inject result to text view
            binding.tvLuas.text = "${luas}"
            binding.tvKeliling.text = "${keliling}"
        }
        super.onViewStateRestored(savedInstanceState)
    }
}
