package com.moises.rickandmortyserie.modules.epidodes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.moises.rickandmortyserie.Faker
import com.moises.rickandmortyserie.core.arch.DispatcherProvider
import com.moises.rickandmortyserie.core.arch.ScreenState
import com.moises.rickandmortyserie.core.assets.ResourceManager
import com.moises.rickandmortyserie.modules.episodes.domain.repository.EpisodesRepository
import com.moises.rickandmortyserie.modules.episodes.domain.usecase.AllEpisodesUseCase
import com.moises.rickandmortyserie.modules.episodes.framework.presentation.EpisodeViewModel
import com.moises.rickandmortyserie.modules.episodes.framework.presentation.EpisodesScreenState
import com.moises.rickandmortyserie.rules.getOrAwaitValue
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class EpisodeViewModelTest {
    @JvmField
    @Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var episodesRepository: EpisodesRepository

    @Mock
    lateinit var dispatcherProvider: DispatcherProvider

    @Mock
    lateinit var resourceManager: ResourceManager

    private lateinit var allEpisodesUseCase: AllEpisodesUseCase
    private lateinit var episodeViewModel: EpisodeViewModel
    private val allEpisodes = Faker.providesEpisodesSuccessResponse()
    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        allEpisodesUseCase = AllEpisodesUseCase(dispatcherProvider, episodesRepository, resourceManager)
        episodeViewModel = EpisodeViewModel(allEpisodesUseCase, resourceManager)
        whenever(dispatcherProvider.ioDispatcher()).thenReturn(Dispatchers.Main)
        whenever(resourceManager.providesStringMessage(identifier = EPISODES_ERROR)).thenReturn(ERROR)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `test successfully episodes retrieved`() = testCoroutineScope.runBlockingTest {
        //When
        whenever(episodesRepository.retrieveAllEpisodes(ArgumentMatchers.anyInt())).thenReturn(
                flow { emit(allEpisodes) })

        //Then
        episodeViewModel.run {
            retrieveAllEpisodes()
            val state = episodesScreenState.getOrAwaitValue()
            //Assert
            Assert.assertTrue(state is ScreenState.Render)
            Assert.assertTrue((state as ScreenState.Render).data is EpisodesScreenState.Success)
            Assert.assertEquals((state.data as EpisodesScreenState.Success).allEpisodes, allEpisodes.all)
        }
    }

    @Test
    fun `test unsuccessfully characters retrieved`() = testCoroutineScope.runBlockingTest {
        //When
        whenever(episodesRepository.retrieveAllEpisodes(ArgumentMatchers.anyInt())).thenReturn(
                flow { error(NETWORK_ERROR) }
        )
        //Then
        episodeViewModel.run {
            retrieveAllEpisodes()
            val state = episodesScreenState.getOrAwaitValue()
            //Assert
            Assert.assertTrue(state is ScreenState.Render)
            Assert.assertTrue((state as ScreenState.Render).data is EpisodesScreenState.Error)
            Assert.assertEquals((state.data as EpisodesScreenState.Error).message, ERROR)
        }
    }

    companion object {
        const val ERROR = "Couldn't recover all episodes"
        const val NETWORK_ERROR = "Network error has occurred"
        const val EPISODES_ERROR = "error_episodes"
    }
}