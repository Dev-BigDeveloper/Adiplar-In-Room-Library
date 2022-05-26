package com.example.adiplar.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.adiplar.R
import com.example.adiplar.databinding.ItemRvAdibMumtozBinding
import com.example.adiplar.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class UserAdapter(private var list: List<User>, var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<UserAdapter.Vh>() {
    inner class Vh(private var binding: ItemRvAdibMumtozBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "ResourceAsColor", "ClickableViewAccessibility")
        fun onBind(user: User) {
            firebaseFireStore = FirebaseFirestore.getInstance()
            var name = user.nameAndLastName
            var mName = ""
            if (user.imageUrl == null) {
                binding.image.scaleType = ImageView.ScaleType.CENTER_INSIDE
            } else {
                binding.image.scaleType = ImageView.ScaleType.CENTER_CROP
            }
            for (i in name!!.indices) {
                val ch = name[i]
                if (ch.code == 32) {
                    break
                }
                mName += ch.toString()
            }
            name = name.replace(mName, "").trim()
            binding.nameUser.text = "$mName\n$name"
            if (user.deadAge != 0) {
                binding.ageDataAndDeadData.text = "${user.bornAge} - ${user.deadAge}"
            } else if (user.deadAge == 0) {
                binding.ageDataAndDeadData.text = "${user.bornAge}"
            }
            if (user.imageUrl != null) {
                Picasso.get().load(user.imageUrl).into(binding.image)
            }
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(user)
            }

            binding.saveDelete.setOnClickListener {
                val listIdRead:ArrayList<String> = readData()
                if (user.saveAndDelete == false){
                    binding.saveDelete.setBackgroundColor(R.color.purple_500)
                    binding.cardSave.setBackgroundColor(R.color.purple_500)
                }
            }
        }
    }

    private lateinit var firebaseFireStore: FirebaseFirestore
    private var list1: ArrayList<Boolean> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemRvAdibMumtozBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onItemClick(user: User)
    }


    private fun writeData(user:User) {
        firebaseFireStore.collection("user")
            .add(user)
            .addOnSuccessListener {

            }
            .addOnFailureListener {

            }
    }

    private fun readDataBoolean(): ArrayList<Boolean> {
        list = ArrayList()
        firebaseFireStore.collection("usersSave")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result
                    result?.forEach { queryDocumentSnapshot ->
                        val user = queryDocumentSnapshot.toObject(Boolean::class.java)
                        list1.add(user)
                    }
                }
            }
        return list1
    }

    private fun readData():ArrayList<String> {
        var listId = ArrayList<String>()
        firebaseFireStore.collection("users")
            .get()
            .addOnSuccessListener { result ->
                    for (document in result) {
                    listId.add(document.id)
                }
            }
//        for (document in documents) {
//            Log.d(TAG, "${document.id} => ${document.data}")
//        }
        return listId
    }
}