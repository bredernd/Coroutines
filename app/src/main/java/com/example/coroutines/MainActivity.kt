package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var layoutManager: LinearLayoutManager? = null
    private var adapter: RecyclerAdapter? = null
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var viewModel: MainViewModel

    private val num = arrayOf(1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000)
    private var randNum = 0
    private var name = ""
    private var text = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        if (savedInstanceState != null) {
            val nameList = savedInstanceState.getStringArrayList("nameList")
            nameList?.let {
                viewModel.restoreList(it)
            }
        }

        binding.addName.setOnClickListener {
            ejectoNameo()
        }

        layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        val existingList = viewModel.getNameList() ?: arrayListOf()
        adapter = RecyclerAdapter(existingList)
        binding.recyclerView.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.getNameList()?.let { list ->
            outState.putStringArrayList("nameList", ArrayList(list))
        }
    }

    private fun ejectoNameo() {
        coroutineScope.launch(Dispatchers.Main) {
            executeDelay()
        }
    }

    private suspend fun executeDelay() {
        name = binding.aName.text.toString()
        randNum = num.random()
        delay(randNum.toLong())
        text = "The name is $name and the delay was $randNum milliseconds"
        viewModel.addToList(text)
        adapter?.notifyDataSetChanged()
    }
}
