package com.example.coroutines

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.coroutines.model.User
import com.example.coroutines.model.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel: ViewModel() {

    // Method 3
    // Live Data Builder
    private var userRepository = UserRepository()
    var user = liveData(Dispatchers.IO) {
        val result = userRepository.getuser()
        emit(result)
    }

    // Method 2
//    private var userRepository = UserRepository()
//    var user = MutableLiveData<List<User>?>()
//
//
//    // Using viewModelScope is simple and less lines of code for same operation as below
//    fun getUserData(){
//        viewModelScope.launch {
//            var result : List<User>? = null
//            withContext(Dispatchers.IO){
//                result = userRepository.getuser()
//
//            }
//
//            user.value = result
//        }
//    }




    //Method 1

//    private val myjob = Job()
//    private val scope = CoroutineScope(Dispatchers.IO + myjob)
//
//    fun getUserData(){
//        scope.launch {
//            //code
//        }
//    }
//
//
//    override fun onCleared() {
//        super.onCleared()
//        myjob.cancel()
//    }


}