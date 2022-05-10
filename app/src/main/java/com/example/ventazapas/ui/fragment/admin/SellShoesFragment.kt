package com.example.ventazapas.ui.fragment.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ventazapas.R
import com.example.ventazapas.databinding.FragmentSellShoesBinding

class SellShoesFragment : Fragment() {
     private lateinit var binding: FragmentSellShoesBinding
      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSellShoesBinding.inflate(inflater, container, false)





          return binding.root
    }
}