package com.moises.rickandmortyserie.modules.character.framework.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moises.rickandmortyserie.R
import com.moises.rickandmortyserie.core.ui.inflate
import com.moises.rickandmortyserie.databinding.CharacterViewItemBinding
import com.moises.rickandmortyserie.modules.character.domain.model.Character
import com.squareup.picasso.Picasso


class CharactersAdapter(
    private var characters: MutableList<Character> = mutableListOf(),
    private val characterSelected: (Character) -> Unit
) : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.character_view_item))

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(characters[position], characterSelected)

    fun updateDataSet(charactersList: MutableList<Character>) {
        characters = charactersList
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val characterViewItemBinding = CharacterViewItemBinding.bind(view)
        fun bind(character: Character, characterSelected: (Character) -> Unit) =
            with(characterViewItemBinding) {
                tvCharacterName.text = character.name
                Picasso.get().load(character.image).into(imvCharacter)
                root.setOnClickListener {
                    characterSelected(character)
                }
            }
    }
}