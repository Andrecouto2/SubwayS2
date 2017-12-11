package br.com.andrecouto.easymetro.Utils

import android.app.Activity
import br.com.andrecouto.easymetro.extensions.fileAsString
import br.com.andrecouto.subways2.model.Linha
import br.com.andrecouto.subways2.model.LinhaResponse
import com.google.gson.Gson
import java.io.StringReader

object AssetsUtils {

    fun loadJSONFromAsset(context: Activity): String? {
        val json = context.assets.fileAsString("json", "metro.json")
        return json
    }

    fun loadLinhaFromString(json: String?): LinhaResponse {
        var linha: LinhaResponse
        val gson = Gson()
        linha = gson.fromJson(json, LinhaResponse::class.java)
        return linha
    }
}