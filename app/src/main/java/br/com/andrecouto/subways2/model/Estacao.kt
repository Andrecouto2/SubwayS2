package br.com.andrecouto.subways2.model

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.CircleOptions
import com.google.gson.annotations.SerializedName


class Estacao() : Parcelable {

    var nome: String = ""
    var latitude = 0.0
    var longitude = 0.0
    var elevador = ""
    @SerializedName("caixa_sugestao")
    var caixaSugestao = ""
    @SerializedName("telefone_usuario")
    var telefoneUsuario = ""
    var sanitarios = ""
    @SerializedName("balcao_inf")
    var balcaoInf = ""
    @SerializedName("central_serv_atend_pessoal")
    var centralServAtendPessoal = ""
    @SerializedName("achados_perdidos")
    var achadosPerdidos = ""
    var wifi = ""
    var circle: CircleOptions = CircleOptions()

    constructor(parcel: Parcel) : this() {
        nome = parcel.readString()
        latitude = parcel.readDouble()
        longitude = parcel.readDouble()
        elevador = parcel.readString()
        caixaSugestao = parcel.readString()
        telefoneUsuario = parcel.readString()
        sanitarios = parcel.readString()
        balcaoInf = parcel.readString()
        centralServAtendPessoal = parcel.readString()
        achadosPerdidos = parcel.readString()
        wifi = parcel.readString()
    }

    companion object CREATOR : Parcelable.Creator<Estacao> {
        override fun createFromParcel(parcel: Parcel): Estacao {
            return Estacao(parcel)
        }

        override fun newArray(size: Int): Array<Estacao?> {
            return arrayOfNulls(size)
        }
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}