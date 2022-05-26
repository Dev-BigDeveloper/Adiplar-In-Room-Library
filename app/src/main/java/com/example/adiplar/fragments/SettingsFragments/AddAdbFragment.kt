package com.example.adiplar.fragments.SettingsFragments

import android.Manifest
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.adiplar.databinding.FragmentAddAdbBinding
import com.example.adiplar.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddAdbFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding:FragmentAddAdbBinding

    private lateinit var firebaseFireStore: FirebaseFirestore
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var reference: StorageReference

    private  var mSpinner = ""

    private var imgUrl:String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAdbBinding.inflate(inflater, container, false)
        firebaseFireStore = FirebaseFirestore.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        reference = firebaseStorage.getReference("images")

        //Rasm olishga perrmession
        askPermission()
        //

        val mTypesAdb = arrayOf("Mumtoz adabiyoti","O`zbek adabiyoti","Jahon adabiyoti")
        val arrayAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,mTypesAdb)
        binding.spinner.adapter = arrayAdapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                mSpinner = mTypesAdb[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }



        binding.imageAddBtn.setOnClickListener {
            loadImageInGallery()
        }

        binding.saveBtn.setOnClickListener {
            writeData()
        }

        return binding.root
    }

    private fun writeData() {
        val name = binding.nameLastName.text.toString()
        val bornAge = binding.age.text.toString().toInt()
        val deadAge = binding.ageDead.text.toString().toInt()
        val info = binding.infoAdb.text.toString()

        val user = User("",name,bornAge,deadAge,imgUrl,mSpinner,info,null)

        firebaseFireStore.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), it.id, Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun askPermission() {
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                )  {
                }
            }).check()
    }

    private fun loadImageInGallery() {
        getImageContent.launch("image/*")
    }

    private var getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()){ uri ->
        binding.imageAdb.setImageURI(uri)
        val m = System.currentTimeMillis()
        val uploadTask = reference.child(m.toString()).putFile(uri)
        uploadTask.addOnSuccessListener {
            if (it.task.isSuccessful){
                Log.d(TAG, ": ${it.task}")
                val downloadUrl = it.metadata?.reference?.downloadUrl
                downloadUrl?.addOnSuccessListener { imgeUrl ->
                    imgUrl = imgeUrl.toString()
                }
            }
        }.addOnFailureListener{
            binding.infoAdb.setText(it.message)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddAdbFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}