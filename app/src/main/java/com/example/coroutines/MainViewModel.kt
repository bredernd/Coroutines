package com.example.coroutines

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    companion object {
        var namePlace: MutableList<String> = arrayListOf()
    }

    fun addToList(value: String) {
        namePlace.add(value)
    }

    fun getNameList(): List<String>? {
        return if (namePlace.isNotEmpty()) namePlace else null
    }

    fun restoreList(list: ArrayList<String>) {
        namePlace.clear()
        namePlace.addAll(list)
    }
}
