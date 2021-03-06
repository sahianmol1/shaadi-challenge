package com.example.shaadichallenge.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shaadichallenge.data.local.ShaadiProfileEntity
import com.example.shaadichallenge.repository.ShaadiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShaadiViewModel @Inject constructor (private val repository: ShaadiRepository): ViewModel() {

    private val _userProfiles = MutableLiveData<List<ShaadiProfileEntity>>()
    val userProfiles: LiveData<List<ShaadiProfileEntity>> get() = _userProfiles

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(ShaadiViewModel::class.simpleName, throwable.message ?: "Unknown error")
        triggerErrorEvent()
        _isLoading.postValue(false)
    }

    init {
        getProfiles(false)
    }

    fun getProfiles(isRefresh: Boolean) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _isLoading.postValue(true)
            val response = repository.getProfiles(isRefresh)
            _userProfiles.postValue(response)
            _isLoading.postValue(false)
        }
    }

    fun updateProfile(profile: ShaadiProfileEntity) = viewModelScope.launch {
        _userProfiles.value = repository.updateProfile(profile)
    }

    sealed class CustomEvent{
        object ErrorEvent: CustomEvent()
    }

    private val channel = Channel<CustomEvent>()
    val eventFlow = channel.receiveAsFlow()

    private fun triggerErrorEvent() = viewModelScope.launch {
        channel.send(CustomEvent.ErrorEvent)
    }
}