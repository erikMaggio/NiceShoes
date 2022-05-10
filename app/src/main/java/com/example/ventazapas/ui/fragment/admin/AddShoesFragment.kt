package com.example.ventazapas.ui.fragment.admin

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.ventazapas.data.fireStore.FireStoreImp
import com.example.ventazapas.databinding.FragmentAddShoesBinding
import com.example.ventazapas.utils.Globals.OBJECT_USER
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.System.out
import android.os.Environment
import java.lang.Exception


class AddShoesFragment : Fragment() {

    val prueba = FireStoreImp()
    private var responseImage = mutableListOf<String>()

    private lateinit var binding: FragmentAddShoesBinding
    private val list = listOf<String>("Masculino", "Femenino", "Unisex")
    private var temp = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddShoesBinding.inflate(inflater, container, false)

        binding.gender.adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            list
        )
        binding.gender.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    temp = p0?.getItemAtPosition(p2).toString()!!
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        binding.btRegister.setOnClickListener {
            prueba.addShoes(
                createCode(),
                binding.color.text.toString(),
                binding.description.text.toString(),
                "",
                temp,
                binding.group.text.toString(),
                createId().toString(),
                responseImage,
                binding.etName.text.toString(),
                0,
                binding.price.text.toString().toInt(),
                false,
                binding.waist.text.toString(),
                true
            )
            clear()
            message()
        }
        binding.btCancel.setOnClickListener {
           clear()
        }


        binding.image.setOnClickListener {
            selectImage()
        }

        return binding.root
    }


    fun createCode(): String {
        val subName = binding.etName.text.substring(0..2)
        val waist = binding.waist.text.toString()
        val subColor = binding.color.text.substring(0..2)
        val subGroup = binding.group.text.substring(0..2)
        val code = subName + waist + subColor + subGroup
        return code
    }

    fun createId(): Int {
        prueba.addUser(
            OBJECT_USER.email,
            OBJECT_USER.debt,
            OBJECT_USER.direction,
            OBJECT_USER.dni,
            OBJECT_USER.email,
            OBJECT_USER.favorite,
            OBJECT_USER.id_edit + 1,
            OBJECT_USER.name,
            OBJECT_USER.number,
            OBJECT_USER.orders,
            OBJECT_USER.shopping,
            OBJECT_USER.state_account,
            OBJECT_USER.type
        )


        prueba.getUser(OBJECT_USER.email).observe(viewLifecycleOwner, {
            OBJECT_USER.id_edit = it.id_edit
        })
        return OBJECT_USER.id_edit
    }

    fun clear() {
        binding.etName.text.clear()
        binding.color.text.clear()
        binding.description.text.clear()
        binding.group.text.clear()
        binding.price.text.clear()
        binding.waist.text.clear()
        responseImage.clear()

    }

    fun message() {
        Toast.makeText(context, "Calzado agregado exitosamente", Toast.LENGTH_LONG).show()
    }

    var urlPhoto: Uri? = null
    val response =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                urlPhoto = result.data?.data
                prueba.getImage(urlPhoto!!).observe(viewLifecycleOwner,{
                    // poner load por carga de imagen
                   // responseImage.clear()
                    if (responseImage.contains(it)){

                    }else {
                        responseImage.add(it)
                        Log.d("response", responseImage.toString())
                    }
                })
            }
        }

    fun selectImage(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        response.launch(intent)
    }
}