package com.example.pokdex.repository

import com.example.pokdex.api.PokemonApiClient
import com.example.pokdex.model.Pokemon
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonRepository {

    private val apiClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PokemonApiClient::class.java)

    fun fetchPokemon(id: Int): Call<Pokemon> {
        return apiClient.fetchPokemon(id)
    }

    companion object {
        private const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}