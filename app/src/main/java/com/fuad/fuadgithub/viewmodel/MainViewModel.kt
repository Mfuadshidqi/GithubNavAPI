package com.fuad.fuadgithub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fuad.fuadgithub.config.ApiConfig
import com.fuad.fuadgithub.config.Event
import com.fuad.fuadgithub.response.ItemsUser
import com.fuad.fuadgithub.response.ResponseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class MainViewModel : ViewModel() {

    private val _countUser = MutableLiveData<List<ItemsUser>>()
    val countUser: LiveData<List<ItemsUser>> = _countUser

    private val _responseUser = MutableLiveData<Int?>()
    val responseUser: LiveData<Int?> = _responseUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText


    fun findUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(username)
        client.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(
                call: Call<ResponseUser>,
                response: Response<ResponseUser>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _countUser.value = response.body()?.items as List<ItemsUser>
                    _responseUser.value = response.body()?.totalCount
                    _snackBarText.value = Event("Success")
                } else {
                    Log.e(TAG,
                        StringBuilder().append("On Failure :").append(response.message()).toString()
                    )
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG,StringBuilder().append("On Failure :").append(t.message).toString())
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}