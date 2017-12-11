package br.com.andrecouto.easymetro.extensions

import android.content.res.AssetManager
import java.nio.charset.Charset

fun AssetManager.fileAsString(subdirectory: String, filename: String): String {
    return open("$subdirectory/$filename").use {
        it.readBytes().toString(Charset.defaultCharset())
    }
}