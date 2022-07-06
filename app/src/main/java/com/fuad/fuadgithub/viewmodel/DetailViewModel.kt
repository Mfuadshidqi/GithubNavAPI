package com.fuad.fuadgithub.viewmodel

import com.fuad.fuadgithub.response.ItemsUser
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fuad.fuadgithub.config.ApiConfig
import com.fuad.fuadgithub.config.Event
import com.fuad.fuadgithub.response.ResponseDetailUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class DetailViewModel : ViewModel() {

    private val _userDetail = MutableLiveData<ResponseDetailUser>()
    val userDetail: LiveData<ResponseDetailUser> = _userDetail

    private val _followers = MutableLiveData<List<ItemsUser>>()
    val followers: LiveData<List<ItemsUser>> = _followers

    private val _following = MutableLiveData<List<ItemsUser>>()
    val following: LiveData<List<ItemsUser>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    fun getFollowers(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsUser>> {
            override fun onResponse(
                call: Call<List<ItemsUser>>,
                response: Response<List<ItemsUser>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    _snackBarText.value = Event("Tidak dapat menampilkan user!")
                }
            }
            override fun onFailure(call: Call<List<ItemsUser>>, t: Throwable) {
                _isLoading.value = false
                _snackBarText.value = Event(StringBuilder().append("On Failure").append(t.message).toString())
            }
        })
    }

    fun getFollowing(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsUser>> {
            override fun onResponse(
                call: Call<List<ItemsUser>>,
                response: Response<List<ItemsUser>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: gagal")
                }
            }
            override fun onFailure(call: Call<List<ItemsUser>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, StringBuilder().append("On Failure").append(t.message).toString())
            }
        })
    }

    fun getUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<ResponseDetailUser> {
            override fun onResponse(
                call: Call<ResponseDetailUser>,
                response: Response<ResponseDetailUser>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: gagal")
                }
            }
            override fun onFailure(call: Call<ResponseDetailUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, StringBuilder().append("On Failure").append(t.message).toString())
            }
        })
    }

    companion object{
        private const val TAG = "DetailViewModel"
    }
}