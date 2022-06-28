package com.example.retrofitnetwork

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitnetwork.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var  recyclerViewAdapter: RecyclerViewAdapter
    lateinit var  viewModel : MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        initViewModel()
        searchUser()


        binding.createBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, CreateNewUserActivity::class.java))
        }
    }

    private fun searchUser(){
        binding.searchBtn.setOnClickListener {
            if(!TextUtils.isEmpty(binding.searchEdit.text.toString())){
                viewModel.searchUser(binding.searchEdit.text.toString())
            } else{
                viewModel.getUserList()
            }

        }
    }


    private fun initRecyclerView(){
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(this@MainActivity, DividerItemDecoration.HORIZONTAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = RecyclerViewAdapter()
            adapter = recyclerViewAdapter
        }
    }

    fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getUserListObserverable().observe(this, Observer<UserList> {
            if (it == null) {
                Toast.makeText(this@MainActivity, "no result found...", Toast.LENGTH_LONG).show()

            }else{
                recyclerViewAdapter.userList = it.data.toMutableList()
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })
    }

}