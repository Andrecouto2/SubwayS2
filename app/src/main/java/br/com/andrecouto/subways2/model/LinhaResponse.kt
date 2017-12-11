package br.com.andrecouto.subways2.model

import com.google.gson.annotations.SerializedName


class LinhaResponse {

    @SerializedName("linha")
    lateinit var linha: Linha
}