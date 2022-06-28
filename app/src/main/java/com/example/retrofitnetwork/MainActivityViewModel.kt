package com.example.retrofitnetwork

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivityViewModel : ViewModel() {

    lateinit var recyclerListData : MutableLiveData<UserList>
    lateinit var createNewUserLiveData: MutableLiveData<UserResponse?>
    lateinit var loadUserData: MutableLiveData<UserResponse?>
    lateinit var deleteUserLiveData: MutableLiveData<UserResponse?>

    init {
        recyclerListData = MutableLiveData()
        createNewUserLiveData = MutableLiveData()
        loadUserData = MutableLiveData()
        deleteUserLiveData = MutableLiveData()
    }
    fun getUserListObserverable(): MutableLiveData<UserList>{
        return recyclerListData
    }

    fun getCreateNewUserObservable(): MutableLiveData<UserResponse?> {
        return  createNewUserLiveData
    }

    fun getDeleteUserObservable(): MutableLiveData<UserResponse?> {
        return  deleteUserLiveData
    }

    fun getLoadUserObservable(): MutableLiveData<UserResponse?> {
        return  loadUserData
    }

    fun createUser(user: User) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.createUser(user)
        call.enqueue(object : Callback<UserResponse?> {
            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                if(response.isSuccessful) {
                    createNewUserLiveData.postValue(response.body())
                } else {
                    createNewUserLiveData.postValue(null)
                }
            }
        })
    }

    fun updateUser(user_id: String, user: User) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.updateUser(user_id, user)
        call.enqueue(object : Callback<UserResponse?> {
            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                if(response.isSuccessful) {
                    createNewUserLiveData.postValue(response.body())
                } else {
                    createNewUserLiveData.postValue(null)
                }
            }
        })
    }

    fun deleteUser(user_id: String?) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.deleteUser(user_id!!)
        call.enqueue(object : Callback<UserResponse?> {
            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                deleteUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                if(response.isSuccessful) {
                    deleteUserLiveData.postValue(response.body())
                } else {
                    deleteUserLiveData.postValue(null)
                }
            }
        })
    }


    fun getUserList(){
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.getUsersList()
        call.enqueue(object : Callback<UserList>{
            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if(response.isSuccessful){
                    recyclerListData.postValue(response.body())
                }else recyclerListData.postValue(null)
            }
            override fun onFailure(call: Call<UserList>, t: Throwable) {
                recyclerListData.postValue(null)
            }
        })
    }

    fun searchUser(searchText : String){
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.searchUsers(searchText)
        call.enqueue(object : Callback<UserList>{
            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if(response.isSuccessful){
                    recyclerListData.postValue(response.body())
                }else recyclerListData.postValue(null)
            }
            override fun onFailure(call: Call<UserList>, t: Throwable) {
                recyclerListData.postValue(null)
            }
        })
    }
}