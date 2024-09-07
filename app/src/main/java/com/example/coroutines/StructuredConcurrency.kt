package com.example.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StructuredConcurrency {
    private var count = 0
    lateinit var deferred: Deferred<Int>
    suspend fun StructeredConcurrencyfunction(): Int {


        // It is an suspend function
        coroutineScope {
            launch(Dispatchers.IO) {
                delay(1000)
                count += 50
            }
        }

        // It is an interface
        deferred = CoroutineScope(Dispatchers.IO).async {
            delay(1000)
            count += 50
            return@async count
        }

        return count + deferred.await()
    }
}