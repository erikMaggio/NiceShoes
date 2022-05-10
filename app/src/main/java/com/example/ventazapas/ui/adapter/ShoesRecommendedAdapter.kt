package com.example.ventazapas.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ventazapas.R
import com.example.ventazapas.data.model.ResponseShoes
import com.example.ventazapas.databinding.ItemRecomendedBinding
import com.squareup.picasso.Picasso

class ShoesRecommendedAdapter(private val list: List<ResponseShoes>) :
    RecyclerView.Adapter<ShoesRecommendedHolder>() {

    private lateinit var listener: OnClickListener

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(listener2: OnClickListener) {
        listener = listener2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoesRecommendedHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recomended, parent, false)
        return ShoesRecommendedHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ShoesRecommendedHolder, position: Int) {
        holder.render(list[position])
    }
}

class ShoesRecommendedHolder(view: View, listener: ShoesRecommendedAdapter.OnClickListener) :
    RecyclerView.ViewHolder(view) {

    val binding = ItemRecomendedBinding.bind(view)

    fun render(shoes: ResponseShoes) {

        binding.tvName.text= shoes.name
        binding.tvPrice.text= "$ ${shoes.price}"
        binding.tvWaist.text= "T: ${shoes.waist}"
        Picasso.get().load(shoes.image[0]).into(binding.ivImage)
    }

    init {
        view.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
    }
}