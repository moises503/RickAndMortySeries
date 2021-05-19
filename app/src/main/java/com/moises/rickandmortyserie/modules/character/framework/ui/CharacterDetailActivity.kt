package com.moises.rickandmortyserie.modules.character.framework.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.moises.rickandmortyserie.core.arch.ScreenState
import com.moises.rickandmortyserie.core.ui.toast
import com.moises.rickandmortyserie.databinding.ActivityCharacterDetailBinding
import com.moises.rickandmortyserie.modules.character.domain.model.Character
import com.moises.rickandmortyserie.modules.character.framework.presentation.SingleCharacterViewModel
import com.moises.rickandmortyserie.modules.character.framework.presentation.SingleCharacterViewState
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding
    private val characterViewModel: SingleCharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        attachObservers()
        intent.apply {
            getStringExtra(CHARACTER_ID)?.let {
                characterViewModel.retrieveCharacter(it)
            }
        }
    }

    private fun attachObservers() {
        characterViewModel.characterState.observe(this, Observer {
            renderScreenState(it)
        })
    }

    private fun renderScreenState(screenState: ScreenState<SingleCharacterViewState>) {
        when(screenState) {
            is ScreenState.Loading -> showLoader()
            is ScreenState.Render -> renderCharacter(screenState.data)
        }
    }

    private fun renderCharacter(singleCharacterState: SingleCharacterViewState) {
        hideLoader()
        when(singleCharacterState) {
            is SingleCharacterViewState.Success -> showCharacterInfo(singleCharacterState.character)
            is SingleCharacterViewState.Error -> showError(singleCharacterState.message)
        }
    }

    private fun showCharacterInfo(character: Character) {
        with(binding) {
            Picasso.get().load(character.image).into(characterImageView)
            characterNameTextView.text = character.name
            characterGenderTextView.text = character.gender
        }
    }

    private fun showLoader()  {

    }

    private fun hideLoader() {

    }

    private fun showError(message: String) {
        toast(message)
    }

    companion object {
        const val CHARACTER_ID = "character_id"
    }
}