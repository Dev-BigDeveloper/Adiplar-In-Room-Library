package com.example.adiplar.fragments.itemClickFragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.adiplar.R
import com.example.adiplar.databinding.FragmentClickItemBinding
import com.google.android.material.appbar.AppBarLayout

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ClickItemFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentClickItemBinding
    var toolBarState = true

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClickItemBinding.inflate(inflater, container, false)
        //val user = arguments?.getSerializable("user") as User
//        loadScrollRes()
        return binding.root
    }

    @SuppressLint("ResourceType")
    fun loadScrollRes() {
        var scrollRange = -1
        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if(scrollRange == -1){
                scrollRange = appBarLayout.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0){
                binding.clouse.setBackgroundColor(Color.parseColor("#25303F"))
                binding.cardClouse.setBackgroundColor(Color.parseColor("#25303F"))
            }else{
                binding.clouse.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.clouse.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClickItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}