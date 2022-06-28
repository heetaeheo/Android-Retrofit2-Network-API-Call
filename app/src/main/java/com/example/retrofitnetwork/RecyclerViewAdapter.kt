package com.example.retrofitnetwork

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitnetwork.databinding.RecyclerRowBinding

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {


    var userList = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }


    class MyViewHolder(private val binding:RecyclerRowBinding): RecyclerView.ViewHolder(binding.root){

        val textViewName = binding.textViewName
        val textViewEmail = binding.textViewEmail
        val textViewStates =binding.textViewStats

        fun bind(data : User){
            textViewName.text = data.name
            textViewEmail.text = data.email
            textViewStates.text = data.status
        }

    }


}