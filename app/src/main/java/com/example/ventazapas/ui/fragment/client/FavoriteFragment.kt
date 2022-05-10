package com.example.ventazapas.ui.fragment.client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ventazapas.R
import com.example.ventazapas.data.fireStore.FireStoreImp
import com.example.ventazapas.data.model.ResponseShoes
import com.example.ventazapas.databinding.FragmentFavoriteBinding
import com.example.ventazapas.ui.adapter.ShoesAdapter
import com.example.ventazapas.utils.Globals.EMAIL

class FavoriteFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteBinding
    val prueba= FireStoreImp()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)


        prueba.getUser(EMAIL).observe(viewLifecycleOwner,{
            var temp = mutableListOf<String>()
            for (i in it.favorite){
                if (!temp.contains(i) && i.isNotEmpty()){
                    temp.add(i)
                }
            }
            if( !temp.isNullOrEmpty()){
                prueba.getListShoesByIDs(temp).observe(viewLifecycleOwner,{
                    initRecyclerView(it)
                })
            }

        })


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