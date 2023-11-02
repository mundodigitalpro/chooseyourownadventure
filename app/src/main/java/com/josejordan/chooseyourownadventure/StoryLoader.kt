package com.josejordan.chooseyourownadventure

// StoryLoader.kt
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

fun loadStoryFromJson(context: Context): List<Page> {
    val json: String = try {
        context.assets.open("story.json").bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return emptyList()
    }

    val listType = object : TypeToken<List<Page>>() {}.type
    return Gson().fromJson(json, listType)
}

class StoryLoader {
    companion object {
        fun loadStory(context: Context): List<Page> {
            return loadStoryFromJson(context)
        }
    }
}
