package com.abhishek.chatapp.common

import java.util.Locale
import kotlin.math.log10
import kotlin.math.pow

object FileUtil {
    fun formatFileSize(bytes: Long): String {
        if (bytes <= 0) return "0 B"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(bytes.toDouble()) / log10(1024.0)).toInt()
        val size = bytes / 1024.0.pow(digitGroups.toDouble())
        return String.format(Locale.getDefault(),"%.1f %s", size, units[digitGroups])
    }
}