package com.ecommerce.beatiful.util

import kotlinx.datetime.Clock


class Helpers {

    fun  shouldUpdateOrNoDatabase(timeStamp: Long, differenceMinutes: Int): Boolean {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val duration = currentTime - timeStamp
        val durationInMinutes = (duration / 1000) / 60
        return  durationInMinutes > differenceMinutes
    }

}