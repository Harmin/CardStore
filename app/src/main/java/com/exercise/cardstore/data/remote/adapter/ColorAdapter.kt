package com.exercise.cardstore.data.remote.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class HexColor

/** Converts strings like #ff0000 to the corresponding color ints.  */
class ColorAdapter {
    @ToJson
    fun toJson(@HexColor rgb: Int): String {
        return "#%06x".format(rgb)
    }

    @FromJson
    @HexColor
    fun fromJson(rgb: String): Int {
        return rgb.substring(1).toInt(16)
    }
}