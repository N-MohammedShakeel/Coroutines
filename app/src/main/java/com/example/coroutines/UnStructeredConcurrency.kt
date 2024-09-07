package com.example.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UnStructeredConcurrency {

    suspend fun UnStructeredConcurrencyfunction(): Int {
        var count = 0

        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            count += 50
        }

        var waitforcount = CoroutineScope(Dispatchers.IO).async {
            delay(1000)
            count += 50
            return@async count
        }

        return count + waitforcount.await()
    }
}