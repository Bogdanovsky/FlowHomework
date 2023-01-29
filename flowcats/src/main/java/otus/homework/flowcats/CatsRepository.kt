package otus.homework.flowcats

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class CatsRepository(
    private val catsService: CatsService,
    private val refreshIntervalMs: Long = 3000
) {

    fun listenForCatFacts(): Flow<LatestUiState> = flow {
        while (true) {
            val latestNews = catsService.getCatFact()
            emit(LatestUiState.Success(latestNews.text))
            delay(refreshIntervalMs)
        }
    }
}