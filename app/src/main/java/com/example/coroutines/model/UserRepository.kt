package com.example.coroutines.model

import kotlinx.coroutines.delay

class UserRepository {

    suspend fun getuser(): List<User>{
        delay(1000)
        val user : List<User> = listOf(
            User(1,"A"),
            User(2,"B"),
            User(3,"C"),
            User(4,"D"),
            User(5,"E"),
        )
        return user

    }
}