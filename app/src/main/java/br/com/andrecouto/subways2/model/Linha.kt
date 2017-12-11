package br.com.andrecouto.subways2.model

import android.os.Parcel
import android.os.Parcelable

class Linha(numero: Int, cor: String, empresa: String, estacoes: ArrayList<Estacao>) : Parcelable {

    var numero = 0
    var cor = ""
    var empresa = ""
    var estacoes : ArrayList<Estacao> = ArrayList()

    constructor(parcel: Parcel) : this(
            TODO("numero"),
            TODO("cor"),
            TODO("empresa"),
            TODO("estacoes")) {
        numero = parcel.readInt()
        cor = parcel.readString()
        empresa = parcel.readString()
    }

    init {
        this.numero = numero
        this.cor = cor
        this.empresa = empresa
        this.estacoes = estacoes
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Linha

        if (numero != other.numero) return false
        if (cor != other.cor) return false
        if (empresa != other.empresa) return false
        if (estacoes != other.estacoes) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numero
        result = 31 * result + cor.hashCode()
        result = 31 * result + empresa.hashCode()
        result = 31 * result + estacoes.hashCode()
        return result
    }

    companion object CREATOR : Parcelable.Creator<Linha> {
        override fun createFromParcel(parcel: Parcel): Linha {
            return Linha(parcel)
        }

        override fun newArray(size: Int): Array<Linha?> {
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