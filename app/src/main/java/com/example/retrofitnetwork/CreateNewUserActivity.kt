package com.example.retrofitnetwork

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.retrofitnetwork.databinding.ActivityCreateNewUserBinding

class CreateNewUserActivity : AppCompatActivity() {


    private lateinit var binding : ActivityCreateNewUserBinding
    lateinit var  viewModel : CreateNewUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val user_id = intent.getStringExtra("user_id")
        initViewModel()
        createUserObservable()

        if(user_id != null) {
            loadUserData(user_id)
        }
        binding.createButton.setOnClickListener {
            createUser(user_id)
        }
        binding.deleteButton.setOnClickListener {
            deleteUser(user_id)
        }
    }

    private fun deleteUser(user_id: String?) {
        viewModel.getDeleteUserObservable().observe(this, Observer <UserResponse?>{
            if(it == null) {
                Toast.makeText(this@CreateNewUserActivity, "Failed to delete user...", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@CreateNewUserActivity, "Successfully deleted user...", Toast.LENGTH_LONG).show()
                finish()
            }
        })
        viewModel.deleteUser(user_id)
    }
    private fun loadUserData(user_id: String?) {
        viewModel.getLoadUserObservable().observe(this, Observer <UserResponse?>{
            if(it != null) {
                binding.editTextName.setText(it.data?.name)
                binding.editTextEmail.setText(it.data?.email)
                binding.createButton.setText("Update")
                binding.deleteButton.visibility =  View.VISIBLE
            }
        })
        viewModel.getUserData(user_id)
    }
    private fun createUser(user_id: String?){
        val user = User("", binding.editTextName.text.toString(), binding.editTextEmail.text.toString(), "Active", "Male")

        if(user_id == null)
            viewModel.createUser(user)
        else
            viewModel.updateUser(user_id, user)

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(CreateNewUserViewModel::class.java)

    }

    private fun createUserObservable() {
        viewModel.getCreateNewUserObservable().observe(this, Observer <UserResponse?>{
            if(it == null) {
                Toast.makeText(this@CreateNewUserActivity, "Failed to create/update new user...", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@CreateNewUserActivity, "Successfully created/updated user...", Toast.LENGTH_LONG).show()
                finish()
            }
        })
    }

}