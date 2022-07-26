package com.sharkbyteslab.kuiper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sharkbyteslab.kuiper.Adapters.ViewPagerAdapter
import com.sharkbyteslab.kuiper.Fragments.Call
import com.sharkbyteslab.kuiper.Fragments.Chat
import com.sharkbyteslab.kuiper.Fragments.Status
import com.sharkbyteslab.kuiper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val fragmentArrayList = ArrayList<Fragment>()

        fragmentArrayList.add(Chat())
        fragmentArrayList.add(Status())
        fragmentArrayList.add(Call())

        val adapter = ViewPagerAdapter(this, supportFragmentManager, fragmentArrayList)

        binding!!.viewPager.adapter = adapter
        binding!!.tabs.setupWithViewPager(binding!!.viewPager)


    }
}