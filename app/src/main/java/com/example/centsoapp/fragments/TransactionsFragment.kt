package com.example.centsoapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.centsoapp.adapters.TransactionAdapter
import com.example.centsoapp.databinding.FragmentTransactionsBinding
import com.example.centsoapp.models.Transaction
import com.example.centsoapp.utils.SharedPrefsHelper
import java.text.SimpleDateFormat
import java.util.*
import com.example.centsoapp.R

class TransactionsFragment : Fragment() {
    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TransactionAdapter
    private var transactions = mutableListOf<Transaction>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        transactions = SharedPrefsHelper.getTransactions(requireContext())
        adapter = TransactionAdapter(
            transactions,
            onEdit = { transaction -> showTransactionDialog(transaction) },
            onDelete = { transaction ->
                transactions.remove(transaction)
                SharedPrefsHelper.saveTransactions(requireContext(), transactions)
                adapter.notifyDataSetChanged()
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.fabAdd.setOnClickListener { showTransactionDialog(null) }
    }

    private fun showTransactionDialog(transaction: Transaction?) {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_transaction, null)
        val etTitle = dialogView.findViewById<EditText>(R.id.etTitle)
        val etAmount = dialogView.findViewById<EditText>(R.id.etAmount)
        val spCategory = dialogView.findViewById<Spinner>(R.id.spCategory)
        val spType = dialogView.findViewById<Spinner>(R.id.spType)
        val etDate = dialogView.findViewById<EditText>(R.id.etDate)

        if (transaction != null) {
            etTitle.setText(transaction.title)
            etAmount.setText(transaction.amount.toString())
            etDate.setText(
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .format(Date(transaction.date))
            )
            // TODO: Set spinner selections for category and type here
        } else {
            etDate.setText(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
        }

        etDate.setOnClickListener {
            // TODO: Implement date picker dialog here
        }

        AlertDialog.Builder(requireContext())
            .setTitle(if (transaction == null) "Add Transaction" else "Edit Transaction")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val title = etTitle.text.toString()
                val amount = etAmount.text.toString().toDoubleOrNull() ?: 0.0
                val category = spCategory.selectedItem.toString()
                val type = spType.selectedItem.toString().lowercase(Locale.getDefault())
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .parse(etDate.text.toString())?.time ?: System.currentTimeMillis()

                if (transaction == null) {
                    val newTransaction = Transaction(
                        title = title,
                        amount = amount,
                        category = category,
                        date = date,
                        type = type
                    )
                    transactions.add(newTransaction)
                } else {
                    transaction.title = title
                    transaction.amount = amount
                    transaction.category = category
                    transaction.type = type
                    transaction.date = date
                }

                SharedPrefsHelper.saveTransactions(requireContext(), transactions)
                adapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
