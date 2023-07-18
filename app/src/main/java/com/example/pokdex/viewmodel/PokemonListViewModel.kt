package com.example.pokdex.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokdex.model.Pokemon
import com.example.pokdex.repository.PokemonRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonListViewModel : ViewModel() {

    private val pokemonRepository = PokemonRepository()

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>>
        get() = _pokemonList

    fun fetchPokemons(range: IntRange) {
        val pokemonData = _pokemonList.value?.toMutableList() ?: mutableListOf()

        range.forEach { id ->
            pokemonRepository.fetchPokemon(id).enqueue(object : Callback<Pokemon> {
                override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                    val pokemon = response.body()
                    pokemon?.let {
                        pokemonData.add(it)
                        pokemonData.sortBy { pokemon -> pokemon.id }
                        pokemonData.distinctBy { pokemon -> pokemon.id }
                        _pokemonList.postValue(pokemonData)
                    }
                }

                override fun onFailure(call: Call<Pokemon>, t: Throwable) {

                }
            })
        }
    }
}
