package com.example.pokdex.api

import com.example.pokdex.model.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiClient {

    @GET("pokemon/{id}")
    fun fetchPokemon(@Path("id") id: Int): Call<Pokemon>
}