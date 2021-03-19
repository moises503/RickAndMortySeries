package com.moises.rickandmortyserie.modules.characters

import com.moises.rickandmortyserie.Faker
import com.moises.rickandmortyserie.core.arch.DispatcherProvider
import com.moises.rickandmortyserie.core.assets.ResourceManager
import com.moises.rickandmortyserie.modules.character.domain.repository.CharacterRepository
import com.moises.rickandmortyserie.modules.character.domain.usecase.AllCharactersUseCase
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class AllCharactersUseCaseTest {

    @Mock
    private lateinit var dispatcherProvider: DispatcherProvider

    @Mock
    private lateinit var characterRepository: CharacterRepository

    @Mock
    private lateinit var resourceManager: ResourceManager

    private lateinit var allCharactersUseCase : AllCharactersUseCase
    private val allCharacters = Faker.makeMockCharacterSuccessResponse()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        allCharactersUseCase = AllCharactersUseCase(dispatcherProvider, characterRepository, resourceManager)
        whenever(dispatcherProvider.ioDispatcher()).thenReturn(Dispatchers.Main)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test if use case returns a flow AllCharacters object`() = testCoroutineScope.runBlockingTest {
        //When
        whenever(characterRepository.retrieveAllCharacters(anyInt())).thenReturn(
                flow { emit(allCharacters) })
        //Then
        allCharactersUseCase.run {
            val allCharactersResponse = execute(AllCharactersUseCase.Params(anyInt())).first()
            //Assert
            assertEquals(allCharactersResponse, allCharacters)
        }
    }


    @Test(expected = Exception::class)
    fun `test if send null params then throw an exception`() {
        //Then
        allCharactersUseCase.run {
            execute(null)
        }
    }
}