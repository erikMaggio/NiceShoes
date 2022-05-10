package com.example.ventazapas.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.ventazapas.R
import com.example.ventazapas.data.model.ResponseShoes
import com.example.ventazapas.databinding.ItemShoesBinding
import com.squareup.picasso.Picasso

class ShoesAdapter(private val list: List<ResponseShoes>) : RecyclerView.Adapter<ShoesHolder>() {

    private lateinit var listener: OnClickListener

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(listener2: OnClickListener) {
        listener = listener2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_shoes, parent, false)
        return ShoesHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ShoesHolder, position: Int) {
        holder.render(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class ShoesHolder(view: View, listener: ShoesAdapter.OnClickListener) :
    RecyclerView.ViewHolder(view) {
    private val binding = ItemShoesBinding.bind(view)

    fun render(shoes: ResponseShoes) {
        if (shoes.state_offer) {
            Picasso.get().load(shoes.image[0]).into(binding.ivMoreSeen)
            binding.tvGender.text = shoes.gender
            binding.tvName.text = shoes.name
            binding.tvOldPrice.text = "$ ${shoes.price}"
            binding.tvOldPrice.isVisible = true
            binding.tvPrice.text = "$${shoes.offer_price}"
            binding.tvMoney.text = "${shoes.discount_rate}%"
            binding.tvOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.tvWaist.text = "T: ${shoes.waist}"
        } else {
            Picasso.get().load(shoes.image[0]).into(binding.ivMoreSeen)
            binding.tvGender.text = shoes.gender
            binding.tvName.text = shoes.name
            binding.tvPrice.text = "$ ${shoes.price}"
            binding.tvOldPrice.isVisible = false
            binding.tvMoney.isVisible = false
            binding.tvWaist.text = "T: ${shoes.waist}"
        }
    }

    init {
        view.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
    }
}