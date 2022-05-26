package com.example.adiplar.fragments.adibsFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.adiplar.R
import com.example.adiplar.adapters.VpAdapter
import com.example.adiplar.databinding.FragmentAdibsListBinding
import com.google.android.material.tabs.TabLayoutMediator

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AdibsListFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding:FragmentAdibsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private lateinit var vPAdapter: VpAdapter

    private lateinit var tabLayoutMediator: TabLayoutMediator

    private val fragmentList = mutableListOf<Fragment>()
    private val tabTitlesList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdibsListBinding.inflate(inflater,container,false)
        initDate()
        initView()
        return binding.root
    }

    private fun initDate(){
        tabTitlesList.add("Mumtoz adabiyoti")
        val fragment1 = MumtozAdabiyot()
        fragmentList.add(fragment1)

        tabTitlesList.add("O`zbek adabiyoti")
        val fragment2 = OzbekAdabiyotiFragment()
        fragmentList.add(fragment2)

        tabTitlesList.add("Jahon adabiyoti")
        val fragment3 = JahonAdabiyotiFragment()
        fragmentList.add(fragment3)
    }

    private fun initView(){
        binding.viewPager.offscreenPageLimit = 3
        vPAdapter = VpAdapter(requireActivity(), fragmentList)
        binding.viewPager.adapter = vPAdapter

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }

        tabLayoutMediator = TabLayoutMediator(binding.tableLayout,binding.viewPager){tab,positsion ->
            tab.text = tabTitlesList[positsion]
        }

        tabLayoutMediator.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdibsListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}