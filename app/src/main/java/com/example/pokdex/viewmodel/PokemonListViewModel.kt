package com.example.pokdex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokdex.model.Pokemon
import com.example.pokdex.repository.PokemonRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonListViewModel : ViewModel() {

    private val pokemonRepository = PokemonRepository()

    val pokemonList = MutableLiveData<List<Pokemon>>()

    fun fetchPokemons(range: IntRange) {
        val pokemonData = pokemonList.value?.toMutableList() ?: mutableListOf()

        range.forEach { id ->
            pokemonRepository.fetchPokemon(id).enqueue(object : Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    val pokemon = response.body()
                    pokemon?.let {
                        pokemonData.add(it)
                        pokemonData.sortBy { pokemon -> pokemon.id }
                        pokemonList.postValue(pokemonData)
                    }
                }

                override fun onFailure(call: Call<Pokemon>, t: Throwable) {

                }
            })
        }
    }
}
