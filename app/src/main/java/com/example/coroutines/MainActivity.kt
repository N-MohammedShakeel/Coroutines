package com.example.coroutines

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.coroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var count = 0
    private var userviewmodel = MainActivityViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CoroutineScope is a interface and Dispatchers is a object , launcher is the Coroutine builder
        CoroutineScope(Dispatchers.IO).launch {}

        binding.rename.setOnClickListener {
            binding.textView.text = count++.toString()
        }

        // Our First Coroutine
        // Running Coroutines on the BackGround Thread
        binding.download.setOnClickListener {
           CoroutineScope(Dispatchers.IO).launch {
               downloading()
           }
        }

        // Running Coroutines on the Main Thread
        binding.download.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                downloading()
            }
        }

        //Using await and async to run the task parallely in the background thread

        // Method 1
        binding.stock.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                Log.d("TAG", "Calculation started: ${Thread.currentThread().name}")
                val stock1 = async { stock1() }
                val stock2 = async { stock2() }
                val total = stock1.await() + stock2.await()
                Log.d("TAG", "Total is $total")
            }

        }

        // Method 2
        binding.stock.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                Log.d("TAG", "Calculation started: ${Thread.currentThread().name}")
                val stock1 = async(Dispatchers.IO) { stock1() }
                val stock2 = async(Dispatchers.IO) { stock2() }
                val total = stock1.await() + stock2.await()
                binding.tvstock.text = total.toString()
            }

        }

        //Unstructered Concurrency
        binding.download.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                binding.tvDataBackgroundtoMain.text = UnStructeredConcurrency().UnStructeredConcurrencyfunction().toString()
                binding.tvstructured.text = StructuredConcurrency().StructeredConcurrencyfunction().toString()

            }
        }

        userviewmodel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        // Need below line for Method 2
        // userviewmodel.getUserData()
        userviewmodel.user.observe(this, Observer { myuser ->
            for (user in myuser!!) {
                Log.d("TAG", "Users id: ${user.id} and name: ${user.name}")
            }
        })


        // Used for Activity or Fragment
        lifecycleScope.launch {
            delay(5000)
            binding.progressBar.visibility = View.VISIBLE
            delay(10000)
            binding.progressBar.visibility = View.GONE
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){

            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){

            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED){

            }
        }


    }

    // Coroutine can invoke both suspend and non-suspend functions
    // Suspending function can be only invoked by another suspending function and Coroutine

    private suspend fun downloading() { // This function is called by a Background Thread
        for (i in 1..10000) {
            Log.d("TAG", "downloading: $i")

            // We cannot get the data from the background thread directly
            //binding.textView.text = i.toString()

            // But we can switch between threads using withContext
            // To use this we have to suspend the function
            withContext(Dispatchers.Main) {
                binding.tvDataBackgroundtoMain.text = i.toString()
                delay(1000)
            }
        }
    }


    private suspend fun stock1(): Int {
        delay(1000)
        Log.d("TAG", "stock1")
        return 10000
    }

    private suspend fun stock2(): Int {
        delay(2000)
        Log.d("TAG", "stock1")
        return 10000
    }



}