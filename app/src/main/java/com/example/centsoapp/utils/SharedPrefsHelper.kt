package com.example.centsoapp.utils


import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.centsoapp.models.Transaction

object SharedPrefsHelper {
    private const val PREFS_NAME = "centso_prefs"
    private const val KEY_EMAIL = "user_email"
    private const val KEY_BUDGET = "monthly_budget"
    private const val KEY_CURRENCY = "currency"
    private const val KEY_TRANSACTIONS = "transactions"

    fun saveEmail(context: Context, email: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_EMAIL, email).apply()
    }
    fun getEmail(context: Context): String? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_EMAIL, null)
    }
    fun clearEmail(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().remove(KEY_EMAIL).apply()
    }

    fun saveBudget(context: Context, budget: Double) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putFloat(KEY_BUDGET, budget.toFloat()).apply()
    }
    fun getBudget(context: Context): Double {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getFloat(KEY_BUDGET, 0f).toDouble()
    }

    fun saveCurrency(context: Context, currency: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_CURRENCY, currency).apply()
    }
    fun getCurrency(context: Context): String {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_CURRENCY, "LKR") ?: "LKR"
    }

    fun saveTransactions(context: Context, transactions: List<Transaction>) {
        val json = Gson().toJson(transactions)
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_TRANSACTIONS, json).apply()
    }
    fun getTransactions(context: Context): MutableList<Transaction> {
        val json = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_TRANSACTIONS, null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<Transaction>>() {}.type
            Gson().fromJson(json, type)
        } else mutableListOf()
    }

    fun resetAll(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().clear().apply()
    }
}