package com.example.ventazapas.ui.fragment.client

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.ventazapas.data.fireStore.FireStoreImp
import com.example.ventazapas.databinding.FragmentDetailsShoesBinding
import com.example.ventazapas.ui.adapter.ShoesDetailsAdapter
import android.graphics.Paint
import androidx.navigation.fragment.findNavController
import com.example.ventazapas.AppNiceShoes.Companion.preferences
import com.example.ventazapas.R
import com.example.ventazapas.utils.Globals.EMAIL


class DetailsShoesFragment : Fragment() {

    private val prueba1 = FireStoreImp()
    private lateinit var binding: FragmentDetailsShoesBinding
    var condition = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsShoesBinding.inflate(inflater, container, false)


        preferences.saveLastID(getBundle())
        binding.iconFavorite.setOnClickListener {
            animationIcon()
        }
        binding.btRegister.setOnClickListener {
            prueba1.addOrders(getBundle(), EMAIL, viewLifecycleOwner)
            prueba1.addMyOrdersUsers(EMAIL,getBundle(),viewLifecycleOwner)
            alert()

        }

        prueba1.getUser(EMAIL).observe(viewLifecycleOwner, {
            if (it.favorite.isNotEmpty() && it.favorite.contains(getBundle())) {
                binding.iconFavorite.setImageResource(R.drawable.icon_favorite_yellow)
                condition = true
            } else {
                binding.iconFavorite.setImageResource(R.drawable.icon_favorite_empty)
                condition = false
            }
        })

        prueba1.getShoesById(getBundle()).observe(viewLifecycleOwner) {

            if (it.state_offer) {
                initRecyclerView(it.image)
                binding.tvGender.text = it.gender
                binding.tvName.text = it.name
                binding.tvOldPrice.text = "$ ${it.price}"
                binding.tvOldPrice.isVisible = true
                binding.tvPrice.text = "$${it.offer_price}"
                binding.tvMoney.text = "${it.discount_rate}%"
                binding.tvDetails.text = it.description
                binding.tvColor.text = it.color
                binding.tvGender.text = it.gender
                binding.tvType.text = it.group
                binding.tvWaist.text = "T: ${it.waist}"

                binding.tvOldPrice.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                initRecyclerView(it.image)
                binding.tvName.text = it.name
                binding.tvPrice.text = "$ ${it.price}"
                binding.tvOldPrice.isVisible = false
                binding.tvMoney.isVisible = false
                binding.tvNameOldPrice.isVisible = false
                binding.tvDetails.text = it.description
                binding.tvColor.text = it.color
                binding.tvGender.text = it.gender
                binding.tvType.text = it.group
                binding.tvWaist.text = "T: ${it.waist}"

            }

            binding.btCancel.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        return binding.root
    }

    private fun getBundle(): String {
        val date = arguments?.getString("id").toString()
        return date
    }

    private fun initRecyclerView(list: List<String>) {
        val adapter = ShoesDetailsAdapter(list)
        binding.rvImages.adapter = adapter
    }

    fun animationIcon() {

        when (condition) {

            true -> {
                binding.iconFavorite.setImageResource(R.drawable.icon_favorite_empty)
                prueba1.deleteFavoriteToUser(EMAIL, getBundle(), viewLifecycleOwner)
            }

            false -> {
                binding.iconFavorite.setImageResource(R.drawable.icon_favorite_yellow)
                prueba1.addFavoriteToUser(EMAIL, getBundle(), viewLifecycleOwner)
            }
        }
    }
    fun alert() {
        val alertDialog = AlertDialog.Builder(context)
            .setMessage("Gracias por Su Compra, en breve se comunicaran con usted")
            .setTitle("Compra Finalizada")
        alertDialog.show()
    }
}