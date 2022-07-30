package com.sharkbyteslab.kuiper.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.sharkbyteslab.kuiper.R
import com.sharkbyteslab.kuiper.databinding.ActivityOtpactivityBinding
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOtpactivityBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var verificationId : String
    private lateinit var dialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val builder = AlertDialog.Builder(this)

        builder.setMessage("Please Wait...")
        builder.setTitle("Verifying")
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()

        val phoneNumber = "+91"+intent.getStringExtra("number")
        Log.d("Firebase Number",phoneNumber)
        binding.textView4.setText("+91 "+intent.getStringExtra("number"))

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(120L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential)
                {
                    Log.d("Verify", "Success!");
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                    Toast.makeText(this@OTPActivity, "Please try again!!   ${p0}", Toast.LENGTH_SHORT).show()
                    Log.d("Firebase Error : ", "$p0" )

                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)

                    dialog.dismiss()
                    verificationId = p0

                }

            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)

        binding.button2.setOnClickListener{

            if(binding.otp.text!!.isEmpty())
            {
                binding.otp.error = "Type OTP.."
            }
            else
            {
                dialog.show()
                val credential = PhoneAuthProvider.getCredential(verificationId, binding.otp.text!!.toString())

                auth.signInWithCredential(credential)
                    .addOnCompleteListener{

                        if(it.isSuccessful)
                        {
                            dialog.dismiss()
                            startActivity(Intent(this@OTPActivity, ProfileActivity::class.java))
                            finish()
                        }
                        else
                        {
                            dialog.dismiss()
                            Toast.makeText(this, "Error : ${it.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }


    }
}