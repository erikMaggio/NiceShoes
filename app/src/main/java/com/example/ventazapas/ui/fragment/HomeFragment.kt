package com.example.ventazapas.ui.fragment

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ventazapas.AppNiceShoes.Companion.preferences
import com.example.ventazapas.R
import com.example.ventazapas.data.fireStore.FireStoreImp
import com.example.ventazapas.data.model.MockShoes
import com.example.ventazapas.data.model.ResponseShoes
import com.example.ventazapas.databinding.FragmentHomeBinding
import com.example.ventazapas.ui.adapter.ShoesAdapter
import com.example.ventazapas.ui.adapter.ShoesRecommendedAdapter
import com.example.ventazapas.ui.fragment.client.DetailsShoesFragment
import com.example.ventazapas.ui.viewModel.HomeViewModel
import com.example.ventazapas.ui.viewModel.ShoesViewModel
import com.example.ventazapas.utils.Globals.LAST_ID
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    val prueba = FireStoreImp()
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHomeBinding.inflate(inflater,container,false)


        prueba.getAllShoes().observe(viewLifecycleOwner,{
            initRecyclerViewRecommended(it)
        })
        prueba.getListShoesByOffert().observe(viewLifecycleOwner,{
           initRecyclerView(it)
        })
        viewLastShoes()

        return binding.root
    }
    private fun initRecyclerView(list: List<ResponseShoes>) {
        val adapter = ShoesAdapter(list)
        binding.rvMoreSeen.adapter = adapter
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
    private fun initRecyclerViewRecommended(list: List<ResponseShoes>) {
        val adapter = ShoesRecommendedAdapter(list)
        binding.rvRecommended.adapter = adapter
        adapter.setOnClickListener(object : ShoesRecommendedAdapter.OnClickListener {
            override fun onItemClick(position: Int) {

                val bundle = Bundle()
                bundle.putString("id", list[position].id.toString())
                val fragment = DetailsShoesFragment()
                fragment.arguments = bundle
                findNavController().navigate(R.id.detailsShoesFragment, bundle)
            }
        })
    }

    fun viewLastShoes(){
        if(!preferences.getLastID().isNullOrEmpty() && preferences.getLastID()!= ""){
            binding.clLast.isVisible= true
        prueba.getShoesById(preferences.getLastID()).observe(viewLifecycleOwner,{
            if (it.state_offer) {
                Picasso.get().load(it.image[0]).into(binding.ivMoreSeen)
                binding.tvGender.text = it.gender
                binding.tvName.text = it.name
                binding.tvOldPrice.text = "$ ${it.price}"
                binding.tvOldPrice.isVisible = true
                binding.tvPrice.text = "$${it.offer_price}"
                binding.tvMoney.text = "${it.discount_rate}%"
                binding.tvGender.text = it.gender
                binding.tvOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                Picasso.get().load(it.image[0]).into(binding.ivMoreSeen)
                binding.tvName.text = it.name
                binding.tvPrice.text = "$ ${it.price}"
                binding.tvOldPrice.isVisible = false
                binding.tvMoney.isVisible = false
                binding.tvGender.text = it.gender

            }
        })
        }else{
            binding.clLast.isVisible=false
        }
    }
}