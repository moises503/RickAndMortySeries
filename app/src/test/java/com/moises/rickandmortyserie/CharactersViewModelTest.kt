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
    private val allCharacters = Faker.makeMockCharacterSuccessResponse()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        allCharactersUseCase = AllCharactersUseCase(dispatcherProvider, characterRepository)
        characterViewModel = CharacterViewModel(allCharactersUseCase, stringResources)
        whenever(dispatcherProvider.ioDispatcher()).thenReturn(Dispatchers.Main)
        whenever(stringResources.getCharactersErrorMessage()).thenReturn(ERROR)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test successfully characters retrieved`() = testCoroutineScope.runBlockingTest {
        //When
        whenever(characterRepository.retrieveAllCharacters(anyInt())).thenReturn(
            flow { emit(allCharacters) })
        //Then
        characterViewModel.run {
            retrieveAllCharacters()
            val state = allCharactersScreenState.getOrAwaitValue()
            //Assert
            assertTrue(state is ScreenState.Render)
            assertTrue((state as ScreenState.Render).data is AllCharactersScreenState.Success)
            assertEquals((state.data as AllCharactersScreenState.Success).allCharacters, allCharacters.all)
        }
    }

    @Test
    fun `test unsuccessfully characters retrieved`() = testCoroutineScope.runBlockingTest {
        //When
        whenever(characterRepository.retrieveAllCharacters(anyInt())).thenReturn(
            flow { error("Network error") }
        )
        //Then
        characterViewModel.run {
            retrieveAllCharacters()
            val state = allCharactersScreenState.getOrAwaitValue()
            //Assert
            assertTrue(state is ScreenState.Render)
            assertTrue((state as ScreenState.Render).data is AllCharactersScreenState.Error)
            assertEquals((state.data as AllCharactersScreenState.Error).message, ERROR)
        }
    }

    companion object {
        const val ERROR = "No fue posible cargar personajes"
    }

}