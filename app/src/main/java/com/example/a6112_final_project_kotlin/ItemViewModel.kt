package com.example.a6112_final_project_kotlin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {
    private val _items = MutableLiveData<List<Item>>(mutableListOf())
    val items: LiveData<List<Item>> get() = _items

    private val _lowStockPriceSum = MutableLiveData<Int>(0)
    val lowStockPriceSum: LiveData<Int> get() = _lowStockPriceSum

    fun updateItem(updatedItem: Item) {
        val updatedList = _items.value.orEmpty().toMutableList()
        val index = updatedList.indexOfFirst { it.id == updatedItem.id }
        if (index != -1) {
            updatedList[index] = updatedItem
            _items.value = updatedList
        }
        calculateLowStockPriceSum()
    }

    fun addItem(newItem: Item) {
        val updatedList = _items.value.orEmpty().toMutableList()
        updatedList.add(newItem)
        _items.value = updatedList
        calculateLowStockPriceSum()
    }

    private fun calculateLowStockPriceSum() {
        _lowStockPriceSum.value = _items.value.orEmpty()
            .filter { it.currQuantity <= it.lowStock }
            .sumOf { it.price }
    }

    init {
        _items.value = listOf(
            Item(1, "Item 1", "Description 1", "kitchen", 1, 10, 5, 800),
            Item(2, "Item 2", "Description 2", "living room", 2, 10, 5, 700),
            Item(3, "Item 3", "Description 3", "bedroom", 3, 10, 5, 600),
            Item(4, "Item 4", "Description 4", "bathroom", 4, 10, 5, 500)
        )
    }
}
