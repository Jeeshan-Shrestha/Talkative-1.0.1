package com.example.talkative.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Helpers for the chat screen timestamps.
 * Backend sends ISO local date-time like "2026-09-04T06:32:51.635" (millis optional, may be null).
 * minSdk is 24 without core library desugaring, so java.time is avoided here.
 */
object ChatTimeUtils {

    private val isoParser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
    private val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    private val nowFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.US)

    /** Formats a backend timestamp into a short "6:32 AM" style label. Returns "" when unavailable. */
    fun formatToTime(timestamp: String?): String {
        if (timestamp.isNullOrBlank()) return ""
        return try {
            val withoutMillis = timestamp.substringBefore(".")
            val date = isoParser.parse(withoutMillis) ?: return ""
            timeFormat.format(date)
        } catch (e: Exception) {
            //fallback: slice HH:mm straight out of the ISO string
            if (timestamp.length >= 16) timestamp.substring(11, 16) else ""
        }
    }

    /** Current local time in the same format the backend uses, for messages we send. */
    fun nowIso(): String = nowFormat.format(Date())
}
