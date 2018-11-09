package com.hucet.tyler.memo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonDeserializer {
    inline fun <reified T> deserialize(gson: Gson, jsonString: String): T {
        return gson.fromJson(jsonString, object : TypeToken<T>() {}.type)
    }
}