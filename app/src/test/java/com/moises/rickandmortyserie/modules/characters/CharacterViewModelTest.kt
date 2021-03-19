package com.moises.rickandmortyserie.modules.characters

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moises.rickandmortyserie.Faker
import com.moises.rickandmortyserie.core.arch.DispatcherProvider
import com.moises.rickandmortyserie.core.arch.ScreenState
import com.moises.rickandmortyserie.core.assets.ResourceManager
import com.moises.rickandmortyserie.modules.character.domain.repository.CharacterRepository
import com.moises.rickandmortyserie.modules.character.domain.usecase.AllCharactersUseCase
import com.moises.rickandmortyserie.modules.character.framework.presentation.AllCharactersScreenState
import com.moises.rickandmortyserie.modules.character.framework.presentation.CharacterViewModel
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

@ExperimentalCoroutinesApi
class CharacterViewModelTest {

    @JvmField
    @Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var characterRepository: CharacterRepository

    @Mock
    private lateinit var dispatcherProvider: DispatcherProvider

    @Mock
    private lateinit var resourceManager: ResourceManager

    private lateinit var allCharactersUseCase: AllCharactersUseCase
    private lateinit var characterViewModel: CharacterViewModel
    private val allCharacters = Faker.makeMockCharacterSuccessResponse()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        allCharactersUseCase = AllCharactersUseCase(dispatcherProvider, characterRepository, resourceManager)
        characterViewModel = CharacterViewModel(allCharactersUseCase, resourceManager)
        whenever(dispatcherProvider.ioDispatcher()).thenReturn(Dispatchers.Main)
        whenever(resourceManager.providesStringMessage(identifier = CHARACTERS_ERROR)).thenReturn(ERROR)
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
            flow { error(NETWORK_ERROR) }
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
        const val ERROR = "Couldn't recover all characters"
        const val NETWORK_ERROR = "Network error has occurred"
        const val CHARACTERS_ERROR = "characters_error"
    }
}