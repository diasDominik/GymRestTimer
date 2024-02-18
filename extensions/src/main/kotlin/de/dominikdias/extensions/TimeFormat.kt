package de.dominikdias.extensions

fun Long.formatTime(): String {
    val seconds = (this / 1000 % 60).toInt()
    val minutes = ((this / 1000 / 60) % 60).toInt()
    val hours = (this / 1000 / 3600).toInt()

    return buildString {
        if (hours > 0) {
            append("%02d:".format(hours))
        }
        append("%02d:%02d".format(minutes, seconds))
    }
}

