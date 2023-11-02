package com.josejordan.chooseyourownadventure

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

// Esta función ahora devuelve un List<Page>? que puede ser nulo si falla la carga.
fun loadStory(context: Context): List<Page>? {
    return try {
        val jsonString = context.assets.open("story1.json").bufferedReader().use { it.readText() }
        val listType = object : TypeToken<List<Page>>() {}.type
        Gson().fromJson(jsonString, listType)
    } catch (e: IOException) {
        // Log the error to the console
        Log.e("loadStory", "Error loading story from JSON", e)
        null
    }
}
