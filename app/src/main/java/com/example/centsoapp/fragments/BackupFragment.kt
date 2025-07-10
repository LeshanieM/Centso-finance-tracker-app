package com.example.centsoapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.example.centsoapp.databinding.FragmentBackupBinding
import com.example.centsoapp.models.Transaction
import com.example.centsoapp.utils.SharedPrefsHelper
import com.example.centsoapp.utils.StorageHelper

class BackupFragment : Fragment() {
    private var _binding: FragmentBackupBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBackupBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBackup.setOnClickListener {
            val transactions = SharedPrefsHelper.getTransactions(requireContext())
            val json = Gson().toJson(transactions)
            val success = StorageHelper.exportData(requireContext(), json)
            Toast.makeText(requireContext(), if (success) "Backup successful" else "Backup failed", Toast.LENGTH_SHORT).show()
        }
        binding.btnRestore.setOnClickListener {
            val json = StorageHelper.importData(requireContext())
            if (json != null) {
                val transactions = Gson().fromJson(json, Array<Transaction>::class.java).toList()
                SharedPrefsHelper.saveTransactions(requireContext(), transactions)
                Toast.makeText(requireContext(), "Restore successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "No backup found", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}