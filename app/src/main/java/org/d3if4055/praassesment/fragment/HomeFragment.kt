package org.d3if4055.praassesment.fragment


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import org.d3if4055.praassesment.MainActivity
import org.d3if4055.praassesment.R
import org.d3if4055.praassesment.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        judul()
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        binding.btnPersegiPanjang.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_persegiFragment)
        }
        binding.btnSegitiga.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_segitigaFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, view!!.findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun judul() {
        val getActivity = activity!! as MainActivity
        getActivity.supportActionBar?.title = "Home"
    }
}
