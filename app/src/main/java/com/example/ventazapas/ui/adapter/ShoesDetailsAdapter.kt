package com.example.ventazapas.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ventazapas.R
import com.example.ventazapas.databinding.ItemDetailsBinding
import com.squareup.picasso.Picasso

class ShoesDetailsAdapter (private val list : List<String>): RecyclerView.Adapter <ShoesDetailsHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoesDetailsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_details, parent, false)
        return ShoesDetailsHolder(view)
    }

    override fun onBindViewHolder(holder: ShoesDetailsHolder, position: Int) {
        holder.render(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
class ShoesDetailsHolder (view:View) : RecyclerView.ViewHolder(view){

    private val binding = ItemDetailsBinding.bind(view)

    fun render(image:String){
        Picasso.get().load(image).into(binding.ivDetails)
    }
}