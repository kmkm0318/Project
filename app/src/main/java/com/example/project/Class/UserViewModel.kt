package com.example.project.Class

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class UserViewModelFactory(private val repository: Repository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Viewmodel class")
    }
}
class UserViewModel(private val repository: Repository) : ViewModel() {

    private var _userData = MutableStateFlow<List<UserData>>(emptyList())
    val userData = _userData.asStateFlow()

    fun updateUserData(userData: UserData){
        viewModelScope.launch {
            repository.UpdateUserData(userData)
            readUser()

        }
    }

    private fun readUser(){
        viewModelScope.launch {
            repository.readUser().collect{
                _userData.value = it
            }
        }
    }
}