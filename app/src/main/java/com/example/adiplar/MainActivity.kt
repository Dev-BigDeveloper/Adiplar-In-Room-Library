package com.example.adiplar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import com.example.adiplar.databinding.ActivityMainBinding
import com.example.adiplar.fragments.SaveFragments.SaveFragment
import com.example.adiplar.fragments.SettingsFragments.SettingsFragment
import com.example.adiplar.fragments.adibsFragments.AdibsListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        val fragmentHome = AdibsListFragment()
//        val fm: FragmentManager = supportFragmentManager
//        fm.beginTransaction().add(R.id.fragmentContainer, fragmentHome).commit()
//
//        binding.bottomBar.setOnItemSelectedListener {
//            when(it){
//                0 -> {
//                    val fragment1 = AdibsListFragment()
//                    fm.beginTransaction().replace(R.id.fragmentContainer,fragment1).commit()
//                }
//                1 -> {
//                    val fragment2 = SaveFragment()
//                    fm.beginTransaction().replace(R.id.fragmentContainer,fragment2).commit()
//                }
//                2 -> {
//                    val fragment3 = SettingsFragment()
//                    fm.beginTransaction().replace(R.id.fragmentContainer,fragment3).commit()
//                }
//            }
//        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this,R.id.container).navigateUp()
    }


}