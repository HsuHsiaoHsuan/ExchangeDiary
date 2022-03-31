package idv.hsu.exchangediary.ui.utils

import java.text.DateFormat
import java.util.*

fun getNowDateString(): String {
    val df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault())
    return df.format(Date())
}