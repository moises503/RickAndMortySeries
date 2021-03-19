package com.moises.rickandmortyserie.modules.epidodes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moises.rickandmortyserie.Faker
import com.moises.rickandmortyserie.core.arch.DispatcherProvider
import com.moises.rickandmortyserie.core.assets.ResourceManager
import com.moises.rickandmortyserie.modules.episodes.domain.repository.EpisodesRepository
import com.moises.rickandmortyserie.modules.episodes.domain.usecase.AllEpisodesUseCase
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class AllEpisodesUseCaseTest {

    @JvmField
    @Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var dispatcherProvider: DispatcherProvider

    @Mock
    private lateinit var episodesRepository: EpisodesRepository

    @Mock
    private lateinit var resourceManager: ResourceManager

    private lateinit var allEpisodesUseCase: AllEpisodesUseCase
    private val allEpisodes = Faker.providesEpisodesSuccessResponse()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        allEpisodesUseCase = AllEpisodesUseCase(dispatcherProvider, episodesRepository, resourceManager)
        whenever(dispatcherProvider.ioDispatcher()).thenReturn(Dispatchers.Main)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test if use case returns a flow AllEpisodes object`() = testCoroutineScope.runBlockingTest {
        //When
        whenever(episodesRepository.retrieveAllEpisodes(ArgumentMatchers.anyInt())).thenReturn(
                flow { emit(allEpisodes) })
        //Then
        allEpisodesUseCase.run {
            val allCharactersResponse = execute(AllEpisodesUseCase.Params(ArgumentMatchers.anyInt())).first()
            //Assert
            Assert.assertEquals(allCharactersResponse, allEpisodes)
        }
    }

    @Test(expected = Exception::class)
    fun `test if send null params then throw an exception`() {
        //Then
        allEpisodesUseCase.run {
            execute(null)
        }
    }
}