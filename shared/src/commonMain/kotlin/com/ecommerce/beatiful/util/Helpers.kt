package com.ecommerce.beatiful.util

import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json


class Helpers {
    val json =  Json{
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true

    }
    fun  shouldUpdateOrNoDatabase(timeStamp: Long, differenceMinutes: Int): Boolean {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val duration = currentTime - timeStamp
        val durationInMinutes = (duration / 1000) / 60
        return  durationInMinutes > differenceMinutes
    }

}