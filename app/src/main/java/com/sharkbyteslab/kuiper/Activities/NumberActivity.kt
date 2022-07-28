package com.sharkbyteslab.kuiper.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.sharkbyteslab.kuiper.MainActivity
import com.sharkbyteslab.kuiper.R
import com.sharkbyteslab.kuiper.databinding.ActivityNumberBinding

class NumberActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNumberBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser!=null)
        {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.button.setOnClickListener{

            if(binding.phoneNumber.text!!.isEmpty())
            {
                binding.phoneNumber.error = "Phone Number Required"
            }
            else
            {
                var intent = Intent(this, OTPActivity::class.java)
                intent.putExtra("number", binding.phoneNumber.text!!.toString())
                startActivity(intent)
            }

        }

    }
}