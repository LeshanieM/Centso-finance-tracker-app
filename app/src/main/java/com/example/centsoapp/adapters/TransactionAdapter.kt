package com.example.centsoapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.centsoapp.databinding.ItemTransactionBinding
import com.example.centsoapp.models.Transaction
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(
    private val transactions: List<Transaction>,
    private val onEdit: (Transaction) -> Unit,
    private val onDelete: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    inner class TransactionViewHolder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.binding.tvTitle.text = transaction.title
        holder.binding.tvAmount.text = "%.2f".format(transaction.amount)
        holder.binding.tvCategory.text = transaction.category
        holder.binding.tvDate.text = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(transaction.date))
        holder.binding.tvType.text = transaction.type.capitalize()
        holder.binding.btnEdit.setOnClickListener { onEdit(transaction) }
        holder.binding.btnDelete.setOnClickListener { onDelete(transaction) }
    }

    override fun getItemCount() = transactions.size
}