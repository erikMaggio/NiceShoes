package com.example.ventazapas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ventazapas.data.fireStore.FireStoreImp
import com.example.ventazapas.databinding.ActivityOmboardingBinding
import com.example.ventazapas.utils.Globals.EMAIL
import com.example.ventazapas.utils.Globals.NAME
import com.example.ventazapas.utils.Globals.OBJECT_USER

class OmboardingActivity : AppCompatActivity() {


    private val prueba = FireStoreImp()

    private lateinit var binding: ActivityOmboardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOmboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prueba.registerOrder(EMAIL)
        binding.btRegister.setOnClickListener {
            prueba.addUser(
                EMAIL,
                0,
                binding.etDirection.text.toString(),
                binding.etDni.text.toString(),
                EMAIL,
                listOf(""),
                0,
                NAME,
                binding.etWhatsapp.text.toString(),
                listOf(""),
                listOf(""),
                0,
                "client"
            )
            prueba.getUser(EMAIL).observe(this,{
                OBJECT_USER= it
                startActivity(Intent(this,DrawerActivity::class.java))
                finish()
            })
        }
        binding.btCancel.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}