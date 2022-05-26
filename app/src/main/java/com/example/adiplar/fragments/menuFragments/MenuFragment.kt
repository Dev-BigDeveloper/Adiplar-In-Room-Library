package com.example.adiplar.fragments.menuFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.adiplar.R
import com.example.adiplar.databinding.FragmentMenuBinding
import com.example.adiplar.fragments.SaveFragments.SaveFragment
import com.example.adiplar.fragments.SettingsFragments.SettingsFragment
import com.example.adiplar.fragments.adibsFragments.AdibsListFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MenuFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding:FragmentMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater,container,false)

        val fragmentHome = AdibsListFragment()
        val fm: FragmentManager = parentFragmentManager
        fm.beginTransaction().add(R.id.fragmentContainer, fragmentHome).commit()

        binding.bottomBar.setOnItemSelectedListener {
            when(it){
                0 -> {
                    val fragment1 = AdibsListFragment()
                    fm.beginTransaction().replace(R.id.fragmentContainer,fragment1).commit()
                }
                1 -> {
                    val fragment2 = SaveFragment()
                    fm.beginTransaction().replace(R.id.fragmentContainer,fragment2).commit()
                }
                2 -> {
                    val fragment3 = SettingsFragment()
                    fm.beginTransaction().replace(R.id.fragmentContainer,fragment3).commit()
                }
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}