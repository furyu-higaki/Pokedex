package com.example.pokdex.ui.pokemon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.pokdex.databinding.ActivityPokemonDetailBinding
import com.example.pokdex.model.Pokemon

class PokemonDetailActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPokemonDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pokemon = intent.getParcelableExtra(ExtraKey.POKEMON.name) as? Pokemon ?: return

        Glide
            .with(this)
            .load(pokemon.sprites.frontDefault)
            .into(binding.pokemonImage)
    }

    enum class ExtraKey {
        POKEMON
    }

    companion object {
        fun newIntent(context: Context, pokemon: Pokemon): Intent {
            return Intent(context, PokemonDetailActivity::class.java).apply {
                putExtra(ExtraKey.POKEMON.name, pokemon)
            }
        }
    }
}