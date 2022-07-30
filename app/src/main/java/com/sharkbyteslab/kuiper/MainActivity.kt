package com.sharkbyteslab.kuiper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.sharkbyteslab.kuiper.Activities.NumberActivity
import com.sharkbyteslab.kuiper.Adapters.ViewPagerAdapter
import com.sharkbyteslab.kuiper.Fragments.Call
import com.sharkbyteslab.kuiper.Fragments.Chat
import com.sharkbyteslab.kuiper.Fragments.Status
import com.sharkbyteslab.kuiper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val fragmentArrayList = ArrayList<Fragment>()

        fragmentArrayList.add(Chat())
        fragmentArrayList.add(Status())
        fragmentArrayList.add(Call())

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser==null)
        {
            Log.d("User Main","Null");

            startActivity(Intent(this@MainActivity, NumberActivity::class.java))
            finish()
        }

        val adapter = ViewPagerAdapter(this, supportFragmentManager, fragmentArrayList)

        binding!!.viewPager.adapter = adapter
        binding!!.tabs.setupWithViewPager(binding!!.viewPager)


    }
}