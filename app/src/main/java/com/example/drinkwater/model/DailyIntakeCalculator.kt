package com.example.drinkwater.model

interface DailyIntakeCalculator {
    fun calculateTotalMl(weight: Double, age: Int): Double
}
