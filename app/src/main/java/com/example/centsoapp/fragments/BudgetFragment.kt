package com.example.centsoapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.example.centsoapp.R
import com.example.centsoapp.databinding.FragmentBudgetBinding
import com.example.centsoapp.models.Transaction
import com.example.centsoapp.utils.SharedPrefsHelper

class BudgetFragment : Fragment() {
    private var _binding: FragmentBudgetBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val budget = SharedPrefsHelper.getBudget(requireContext())
        val transactions = SharedPrefsHelper.getTransactions(requireContext())
        val totalExpense = transactions.filter { it.type == "expense" }.sumOf { it.amount }
        val currency = SharedPrefsHelper.getCurrency(requireContext())

        binding.tvBudget.text = "$currency %.2f".format(budget)
        binding.tvSpent.text = "$currency %.2f".format(totalExpense)
        val progress = if (budget > 0) ((totalExpense / budget) * 100).toInt() else 0
        binding.progressBar.progress = progress

        if (budget > 0 && totalExpense >= budget) {
            showBudgetNotification("You have exceeded your monthly budget!")
        } else if (budget > 0 && totalExpense >= 0.9 * budget) {
            showBudgetNotification("You are nearing your monthly budget limit.")
        }

        binding.btnSetBudget.setOnClickListener {
            val et = EditText(requireContext())
            et.hint = "Enter monthly budget"
            AlertDialog.Builder(requireContext())
                .setTitle("Set Monthly Budget")
                .setView(et)
                .setPositiveButton("Save") { _, _ ->
                    val newBudget = et.text.toString().toDoubleOrNull() ?: 0.0
                    SharedPrefsHelper.saveBudget(requireContext(), newBudget)
                    onViewCreated(view, savedInstanceState)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun showBudgetNotification(message: String) {
        val builder = NotificationCompat.Builder(requireContext(), "budget_channel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("CentsoApp Budget Alert")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
        val notificationManager = NotificationManagerCompat.from(requireContext())
        notificationManager.notify(1, builder.build())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}