package com.example.ventazapas.ui.fragment.client

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ventazapas.R
import com.example.ventazapas.data.fireStore.FireStoreImp
import com.example.ventazapas.data.model.ResponseShoes
import com.example.ventazapas.databinding.FragmentShoesBinding
import com.example.ventazapas.ui.adapter.ShoesAdapter

class ShoesFragment : Fragment() {

    private val prueba = FireStoreImp()
    private var temp = ""
    private var temp1 = ""
    private val list = listOf("Sin filtro", "Genero", "Tipo", "Precio hasta:")
    private var list1 = listOf<String>()

    private lateinit var binding: FragmentShoesBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoesBinding.inflate(inflater, container, false)

        binding.filter1.isVisible = false/** editar los filtros antes de quitar esta linea*/

        prueba.getAllShoes().observe(viewLifecycleOwner) {
            Log.d("response", it.toString())
            initRecyclerView(it)
        }
        binding.filter1.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            list
        )
        binding.filter1.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    temp = p0?.getItemAtPosition(p2).toString()
                    when (temp) {
                        "Sin filtro" -> {
                            binding.filter2.isVisible = false
                        }
                        "Genero" -> {
                            binding.filter2.isVisible = true
                            list1 = listOf("Masculino", "Femenino", "Unisex")
                            binding.filter2.adapter = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                list1
                            )
                        }
                        "Tipo" -> {
                            binding.filter2.isVisible = true
                            list1 = listOf("")
                            binding.filter2.adapter = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                list1
                            )
                        }
                        "Precio hasta:" -> {
                            binding.filter2.isVisible = true
                            list1 = listOf("10000", "7500", "5000", "2500")
                            binding.filter2.adapter = ArrayAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                list1
                            )
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        binding.filter2.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    temp1 = p0?.getItemAtPosition(p2).toString()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        return binding.root
    }

    private fun initRecyclerView(list: List<ResponseShoes>) {
        val adapter = ShoesAdapter(list)
        binding.rvShoes.adapter = adapter
        adapter.setOnClickListener(object : ShoesAdapter.OnClickListener {
            override fun onItemClick(position: Int) {

                val bundle = Bundle()
                bundle.putString("id", list[position].id.toString())
                val fragment = DetailsShoesFragment()
                fragment.arguments = bundle
                findNavController().navigate(R.id.detailsShoesFragment, bundle)
            }
        })
    }
}