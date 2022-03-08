package br.com.example.pedro.spaceapi.domain

import com.google.gson.annotations.SerializedName

data class SpaceTripulation(
    @SerializedName("message") val message: String,
    @SerializedName("number") val number: Int,
    @SerializedName("people") val people: List<PersonInSpace>
)