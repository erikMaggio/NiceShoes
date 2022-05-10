package com.example.ventazapas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ventazapas.AppNiceShoes.Companion.preferences
import com.example.ventazapas.data.fireStore.FireStoreImp
import com.example.ventazapas.databinding.ActivityLoginBinding
import com.example.ventazapas.utils.Globals.EMAIL
import com.example.ventazapas.utils.Globals.NAME
import com.example.ventazapas.utils.Globals.OBJECT_USER
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    val prueba = FireStoreImp()

    private val GOOGLE_SIGN = 100
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btLogin.setOnClickListener {
           // loginToGoogle()

            //mock Admin
            EMAIL= "kevinmaggio57@gmail.com"
            NAME = "usuario test"
            loginFireStore(EMAIL)

            //mock Client
//            EMAIL= "kevinmaggio5@gmail.com"
//            NAME = "usuario test"
//            loginFireStore(EMAIL)

        }
    }

    private fun loginToGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("285370486898-mp0mjk23rtot1sb4dae63jmud7qhhb99.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        startActivityForResult(googleSignInClient.signInIntent, GOOGLE_SIGN)
    }

    //conection whit Google Acount
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN) {
            val tast = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = tast.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                Log.d("succes", account.email.toString())

                                preferences.saveUserEmail(account.email.toString())
                                EMAIL= account.email.toString()
                                NAME=account.givenName.toString()

                                loginFireStore(preferences.getUserEmail())

                            } else {
                                Toast.makeText(this, "Error login", Toast.LENGTH_LONG).show()
                            }
                        }
                }
            } catch (e: Exception) {
                Log.d("error", "Internet Conection")
            }
        }
    }

    private fun loginFireStore(email:String){
        prueba.getUser(email).observe(this,{
            if (it.name != "empty") {
                OBJECT_USER = it
                startActivity(Intent(this, DrawerActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, OmboardingActivity::class.java))
                finish()
            }
        })

    }
}