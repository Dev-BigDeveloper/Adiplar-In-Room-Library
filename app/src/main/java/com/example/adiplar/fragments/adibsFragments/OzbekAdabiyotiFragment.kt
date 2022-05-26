package com.example.adiplar.fragments.adibsFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.adiplar.R
import com.example.adiplar.adapters.UserAdapter
import com.example.adiplar.databinding.FragmentOzbekAdabiyotiBinding
import com.example.adiplar.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OzbekAdabiyotiFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding:FragmentOzbekAdabiyotiBinding

    private lateinit var firebaseFireStore: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var reference: StorageReference

    private lateinit var userAdapter: UserAdapter
    var list: ArrayList<User> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOzbekAdabiyotiBinding.inflate(inflater,container,false)

        firebaseFireStore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        reference = firebaseStorage.getReference("images")

        readData()
        return binding.root
    }

    private fun readData() {
        list = ArrayList()
        firebaseFireStore.collection("users")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    var result = it.result
                    result?.forEach { queryDocumentSnapshot ->
                        val user = queryDocumentSnapshot.toObject(User::class.java)
                        if (user.mType == "O`zbek adabiyoti") {
                            list.add(user)
                        }
                    }
                    userAdapter = UserAdapter(list, object : UserAdapter.OnItemClickListener {
                        override fun onItemClick(user: User) {
                            Toast.makeText(
                                requireContext(),
                                "${user.nameAndLastName}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                    binding.rv.adapter = userAdapter
                }
            }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OzbekAdabiyotiFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}