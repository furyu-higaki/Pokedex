package com.example.pokdex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokdex.databinding.ActivityMainBinding
import com.example.pokdex.ui.pokemon.PokemonAdapter
import com.example.pokdex.ui.pokemon.PokemonDetailActivity
import com.example.pokdex.viewmodel.PokemonListViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(PokemonListViewModel::class.java)
    }

    private val pokemonAdapter by lazy {
        PokemonAdapter { startActivity(PokemonDetailActivity.newIntent(this, it)) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.apply {
            adapter = pokemonAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.pokemonList.observe(this) {
            if (it.isEmpty()) return@observe

            pokemonAdapter.submitList(it)
        }

        binding.addButton.setOnClickListener {
            val lastId = viewModel.pokemonList.value?.lastOrNull()?.id ?: 0
            viewModel.fetchPokemons((lastId + 1)..(lastId + 15))
        }

        viewModel.fetchPokemons(1..15)
    }
}