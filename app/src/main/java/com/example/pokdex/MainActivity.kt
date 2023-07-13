package com.example.pokdex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pokdex.databinding.ActivityMainBinding
import com.example.pokdex.viewmodel.PokemonListViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(PokemonListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.pokemonList.observe(this) {
            if (it.isEmpty()) return@observe

            binding.pokemonsLinearLayout.removeAllViews()
            
            it.forEach { pokemon ->
                val horizontalLayout = LinearLayout(this)
                horizontalLayout.orientation = LinearLayout.HORIZONTAL

                val imageView = ImageView(this)
                Glide.with(this).load(pokemon.sprites.frontDefault).into(imageView)
                horizontalLayout.addView(imageView, 300, 300)

                val textView = TextView(this)
                textView.text = pokemon.name
                textView.gravity = android.view.Gravity.CENTER_VERTICAL
                horizontalLayout.addView(textView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

                horizontalLayout.setOnClickListener {
                    startActivity(PokemonDetailActivity.newIntent(this, pokemon))
                }

                binding.pokemonsLinearLayout.addView(horizontalLayout)
            }
        }

        binding.addButton.setOnClickListener {
            val lastId = viewModel.pokemonList.value?.lastOrNull()?.id ?: 0
            viewModel.fetchPokemons((lastId + 1)..(lastId + 15))
        }

        viewModel.fetchPokemons(1..15)
    }
}