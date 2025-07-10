package com.example.centsoapp.utils
import android.content.Context
import java.io.File

object StorageHelper {
    private const val FILE_NAME = "centso_backup.json"

    fun exportData(context: Context, data: String): Boolean {
        return try {
            val file = File(context.filesDir, FILE_NAME)
            file.writeText(data)
            true
        } catch (e: Exception) { false }
    }

    fun importData(context: Context): String? {
        return try {
            val file = File(context.filesDir, FILE_NAME)
            file.readText()
        } catch (e: Exception) { null }
    }
}