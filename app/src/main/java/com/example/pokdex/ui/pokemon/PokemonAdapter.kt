package com.example.pokdex.ui.pokemon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokdex.databinding.ItemPokemonBinding
import com.example.pokdex.model.Pokemon

/**
 * ポケモンの一覧を表示するためのAdapter
 */
class PokemonAdapter(private val onClick: (Pokemon) -> Unit) : ListAdapter<Pokemon, PokemonAdapter.PokemonViewHolder>(PokemonDiffUtil) {

    /**
     * ViewHolderは、ポケモンの一覧内の各項目を表示するためのもの
     */
    class PokemonViewHolder(itemView: View, val onClick: (Pokemon) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPokemonBinding.bind(itemView)

        // 各項目がどのように表示されるかを設定する
        fun bind(pokemon: Pokemon) {
            itemView.setOnClickListener { onClick(pokemon) }

            Glide.with(itemView.context).load(pokemon.sprites.frontDefault).into(binding.thumbnailImage)
            binding.nameText.text = pokemon.name
        }
    }

    /**
     * 新しいViewHolderを作成する。ポケモン一覧に新しい項目が追加されたときに呼ばれる
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = ItemPokemonBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return PokemonViewHolder(view.root, onClick)
    }

    /**
     * 既存のViewHolderにデータを設定する。ポケモン一覧の項目が画面に表示されるたびに呼ばれる
     */
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = getItem(position)

        holder.bind(pokemon)
    }

    /**
     * DiffUtilは、一覧が更新されたときに新旧の項目の差分を計算する
     * これにより、RecyclerViewが更新される項目のみを更新することができる
     */
    object PokemonDiffUtil : DiffUtil.ItemCallback<Pokemon>() {
        /**
         * 同じidを持つ項目は同じ項目とみなされる
         */
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.id == newItem.id
        }

        /**
         * 内容が完全に一致しているかを判定する。これにより、内容の変更があった項目だけが更新される
         */
        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }
}
