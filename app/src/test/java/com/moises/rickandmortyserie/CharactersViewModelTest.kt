package com.moises.rickandmortyserie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moises.rickandmortyserie.core.arch.DispatcherProvider
import com.moises.rickandmortyserie.core.arch.ScreenState
import com.moises.rickandmortyserie.core.arch.model.Pagination
import com.moises.rickandmortyserie.modules.character.domain.model.AllCharacters
import com.moises.rickandmortyserie.modules.character.domain.repository.CharacterRepository
import com.moises.rickandmortyserie.modules.character.domain.usecase.AllCharactersUseCase
import com.moises.rickandmortyserie.modules.character.framework.presentation.AllCharactersScreenState
import com.moises.rickandmortyserie.modules.character.framework.presentation.CharacterViewModel
import com.moises.rickandmortyserie.modules.character.framework.res.StringResources
import com.moises.rickandmortyserie.rules.getOrAwaitValue
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.MockitoAnnotations

typealias CharacterTest = com.moises.rickandmortyserie.modules.character.domain.model.Character

@ExperimentalCoroutinesApi
class CharactersViewModelTest {

    @JvmField
    @Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var characterRepository: CharacterRepository

    @Mock
    private lateinit var dispatcherProvider: DispatcherProvider

    @Mock
    private lateinit var stringResources: StringResources

    private lateinit var allCharactersUseCase: AllCharactersUseCase
    private lateinit var characterViewModel: CharacterViewModel
    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        allCharactersUseCase = AllCharactersUseCase(dispatcherProvider, characterRepository)
        characterViewModel = CharacterViewModel(allCharactersUseCase, stringResources)
        whenever(dispatcherProvider.ioDispatcher()).thenReturn(Dispatchers.IO)
        whenever(stringResources.getCharactersErrorMessage()).thenReturn(ERROR)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test successfully characters retrieved`() = testCoroutineScope.runBlockingTest {
        val allCharacters = makeMockCharacterSuccessResponse()
        whenever(characterRepository.retrieveAllCharacters(anyInt())).thenReturn(
            flow { emit(allCharacters) })
        characterViewModel.run {
            retrieveAllCharacters()
            when (val state = allCharactersScreenState.getOrAwaitValue()) {
                is ScreenState.Render -> {
                    assertTrue(state.data is AllCharactersScreenState.Success)
                    assertFalse(state.data is AllCharactersScreenState.Error)
                    assertEquals(
                        allCharacters.all,
                        (state.data as AllCharactersScreenState.Success).allCharacters
                    )
                }
            }
        }
    }


    @Test
    fun `test unsuccessfully characters retrieved`() = testCoroutineScope.runBlockingTest {
        whenever(characterRepository.retrieveAllCharacters(anyInt())).thenReturn(
            flow { error("Network error") }
        )
        characterViewModel.run {
            retrieveAllCharacters()
            when (val state = allCharactersScreenState.getOrAwaitValue()) {
                is ScreenState.Render -> {
                    assertFalse(state.data is AllCharactersScreenState.Success)
                    assertTrue(state.data is AllCharactersScreenState.Error)
                    assertEquals(
                        ERROR,
                        (state.data as AllCharactersScreenState.Error).message
                    )
                }
            }
        }
    }


    private fun makeMockCharacterSuccessResponse(): AllCharacters {
        return AllCharacters(
            pagination = Pagination(pages = 10),
            all = listOf(
                CharacterTest(
                    name = "Rick Sanchez",
                    status = "Alive",
                    species = "Human",
                    type = "",
                    gender = "Male"
                ),
                CharacterTest(
                    name = "Morty Smith",
                    status = "Alive",
                    species = "Human",
                    type = "",
                    gender = "Male"
                )
            )
        )
    }

    companion object {
        const val ERROR = "No fue posible cargar personajes"
    }


}