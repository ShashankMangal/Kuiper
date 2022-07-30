package com.sharkbyteslab.kuiper

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.sharkbyteslab.kuiper.Activities.NumberActivity
import com.sharkbyteslab.kuiper.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    private var index = 0
    private val handler = Handler()
    private var charSequence: CharSequence? = null
    private val delay: Long = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        animateText("KUIPER")

        Handler().postDelayed({
            if (user == null) {
                Log.d("User", "Not Registered");
                startActivity(Intent(this@SplashActivity, NumberActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
            finish()
        }, 2500)

    }

    var runnable: Runnable = object : Runnable {
        override fun run() {
            binding.splashText.setText(charSequence!!.subSequence(0, index++))
            if (index <= charSequence!!.length) {
                handler.postDelayed(this, delay)
            }
        }
    }

    fun animateText(cs: CharSequence) {
        charSequence = cs
        index = 0
        binding.splashText.text = ""
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, delay)
    }

}