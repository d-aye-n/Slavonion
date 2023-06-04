package com.example.cupcake.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_RUS = 200.00
private const val PRICE_FOR_DELAY = 100.00

class OrderViewModel : ViewModel() {

    private val _rusName = MutableLiveData<String>("")
    val rusName: LiveData<String> = _rusName

    private val _skill = MutableLiveData<String>("")
    val skill: LiveData<String> = _skill

    private val _date = MutableLiveData<String>("")
    val date: LiveData<String> = _date

    private val _price = MutableLiveData<Double>(0.0)
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    val pricePerRus = PRICE_PER_RUS

    private val _quantity = MutableLiveData<Int>(0)
    private val quantity: LiveData<Int> = _quantity

    private val _skillPrice = MutableLiveData<Double>(0.0)
    val skillPrice: LiveData<Double> = _skillPrice

    val dateOptions = getDateOption()

    init {
        resetOrder()
    }

    fun setRusName(chosenRusName: String) {
        _rusName.value = chosenRusName
    }

    fun setQuantity(quantityRus: Int) {
        _quantity.value = quantityRus
        updatePrice()
    }

    fun setSkillAndPrice(desiredSkill: String, skillPrice: Double) {
        _skill.value = desiredSkill
        _skillPrice.value = skillPrice
        updatePrice()
    }


    fun hasNoSkillSet(): Boolean {
        return _skill.value.isNullOrEmpty()
    }

    fun setDate(desiredDate: String) {
        _date.value = desiredDate
        updatePrice()
    }

    private fun getDateOption(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("d MMMM y", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 4)
        }
        return options
    }


    private fun updatePrice() {
        var calculatedPrice = (quantity.value ?: 0) * PRICE_PER_RUS
        calculatedPrice += skillPrice.value ?: 0.0
        if (dateOptions[0] != date.value || dateOptions[1] != date.value) {
            calculatedPrice += delayPenalty * dateOptions.indexOf(date.value)
        } else _delayPenalty = 0.0
        _price.value = calculatedPrice
    }

    private var _delayPenalty = PRICE_FOR_DELAY * (dateOptions.indexOf(date.value) + 1)
    val delayPenalty = _delayPenalty

    fun resetOrder() {
        _rusName.value = ""
        _skill.value = ""
        _skillPrice.value = 0.0
        _delayPenalty = 0.0
        _date.value = dateOptions[0]
        _price.value = 0.0
    }
}