package com.sharkbyteslab.kuiper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.sharkbyteslab.kuiper.Activities.NumberActivity
import com.sharkbyteslab.kuiper.Fragments.*
import com.sharkbyteslab.kuiper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)


        auth = FirebaseAuth.getInstance()

        if(auth.currentUser==null)
        {
            Log.d("User Main","Null");

            startActivity(Intent(this@MainActivity, NumberActivity::class.java))
            finish()
        }

        binding!!.signOut.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this@MainActivity, NumberActivity::class.java))
            finish()
        }

        fragmentstart()

    }

    private fun fragmentstart()
    {
        var fragment : Fragment? = null;
        fragment = Chat();
        val args = Bundle();

        val fragmentTransaction : FragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_framelayout, fragment);
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}