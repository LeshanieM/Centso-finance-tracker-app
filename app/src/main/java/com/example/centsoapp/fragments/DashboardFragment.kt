package com.example.centsoapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.example.centsoapp.databinding.FragmentDashboardBinding
import com.example.centsoapp.models.Transaction
import com.example.centsoapp.utils.SharedPrefsHelper

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val transactions = SharedPrefsHelper.getTransactions(requireContext())
        val currency = SharedPrefsHelper.getCurrency(requireContext())

        val totalIncome = transactions.filter { it.type == "income" }.sumOf { it.amount }
        val totalExpense = transactions.filter { it.type == "expense" }.sumOf { it.amount }
        val balance = totalIncome - totalExpense

        binding.tvBalance.text = "$currency %.2f".format(balance)
        binding.tvIncome.text = "$currency %.2f".format(totalIncome)
        binding.tvExpense.text = "$currency %.2f".format(totalExpense)

        // Category-wise spending
        val categoryMap = mutableMapOf<String, Double>()
        transactions.filter { it.type == "expense" }.forEach {
            categoryMap[it.category] = (categoryMap[it.category] ?: 0.0) + it.amount
        }
        val entries = categoryMap.map { PieEntry(it.value.toFloat(), it.key) }
        val dataSet = PieDataSet(entries, "Category-wise Spending")
        dataSet.setColors(*com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS)
        val data = PieData(dataSet)
        binding.pieChart.data = data
        binding.pieChart.description.isEnabled = false
        binding.pieChart.invalidate()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}