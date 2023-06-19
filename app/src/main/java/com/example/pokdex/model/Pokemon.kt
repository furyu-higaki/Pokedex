package com.example.pokdex.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val weight: Int,
    val height: Int
) : Parcelable {

    @Parcelize
    data class Sprites(
        @SerializedName("front_default")
        val frontDefault: String
    ) : Parcelable
}