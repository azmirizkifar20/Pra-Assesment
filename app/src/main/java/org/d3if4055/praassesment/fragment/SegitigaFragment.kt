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
import org.d3if4055.praassesment.databinding.FragmentSegitigaBinding
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.sqrt

class SegitigaFragment : Fragment() {
    private lateinit var binding: FragmentSegitigaBinding
    private var luas = 0.0
    private var keliling = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        judul()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_segitiga, container, false)

        binding.btnHitung.setOnClickListener { checkInput() }
        binding.btnShare.setOnClickListener { sendEmail() }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun judul() {
        val getActivity = activity!! as MainActivity
        getActivity.supportActionBar?.title = "Segitiga"
    }

    private fun checkInput() {
        when {
            binding.tfAlas.text.isEmpty() -> {
                Toast.makeText(activity, "Alas harus diisi!", Toast.LENGTH_SHORT).show()
            }
            binding.tfTinggi.text.isEmpty() -> {
                Toast.makeText(activity, "Tinggi harus diisi!", Toast.LENGTH_SHORT).show()
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
        val df = DecimalFormat("#.##")
        val alas = binding.tfAlas.text.toString().toDouble()
        val tinggi = binding.tfTinggi.text.toString().toDouble()
        val sisiMiring = sqrt(alas.pow(2) + tinggi.pow(2))
        luas = (alas * tinggi) / 2
        keliling = alas + tinggi + sisiMiring

        // visible result
        visibleResult()

        // inject result to text view
        binding.tvLuas.text = df.format(luas).toString()
        binding.tvKeliling.text = df.format(keliling).toString()
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        val subject = "Hasil Perhitungan Segitiga"
        val message = """
            Alas = ${binding.tfAlas.text}
            Tinggi = ${binding.tfTinggi.text}
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
            val df = DecimalFormat("#.##")
            luas = savedInstanceState.getDouble("luas")
            keliling = savedInstanceState.getDouble("keliling")

            //visible result
            visibleResult()

            // inject result to text view
            binding.tvLuas.text = df.format(luas).toString()
            binding.tvKeliling.text = df.format(keliling).toString()
        }
        super.onViewStateRestored(savedInstanceState)
    }

}
