package com.abhishek.chatapp.common

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object TimestampUtil {

    fun formatSmartTimestamp(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val days = TimeUnit.MILLISECONDS.toDays(diff)

        return when {
            seconds < 60 -> "Just now"
            minutes < 2 -> "1 minute ago"
            minutes < 60 -> "$minutes minutes ago"
            hours < 1 -> "$minutes minutes ago"
            isToday(timestamp) -> "Today at ${formatTime(timestamp)}"
            isYesterday(timestamp) -> "Yesterday at ${formatTime(timestamp)}"
            days < 7 -> "${getDayOfWeek(timestamp)} at ${formatTime(timestamp)}"
            else -> formatFullDate(timestamp)
        }
    }

    private fun formatTime(timestamp: Long): String {
        val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    private fun formatFullDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("MMM d, yyyy 'at' h:mm a", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    private fun getDayOfWeek(timestamp: Long): String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format(Date(timestamp))
    }

    private fun isToday(timestamp: Long): Boolean {
        val messageCalendar = Calendar.getInstance().apply { timeInMillis = timestamp }
        val todayCalendar = Calendar.getInstance()

        return messageCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR) &&
                messageCalendar.get(Calendar.DAY_OF_YEAR) == todayCalendar.get(Calendar.DAY_OF_YEAR)
    }

    private fun isYesterday(timestamp: Long): Boolean {
        val messageCalendar = Calendar.getInstance().apply { timeInMillis = timestamp }
        val yesterdayCalendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }
        return messageCalendar.get(Calendar.YEAR) == yesterdayCalendar.get(Calendar.YEAR) &&
                messageCalendar.get(Calendar.DAY_OF_YEAR) == yesterdayCalendar.get(Calendar.DAY_OF_YEAR)
    }
}