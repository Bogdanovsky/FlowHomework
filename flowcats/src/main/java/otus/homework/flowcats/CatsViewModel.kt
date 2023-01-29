package otus.homework.flowcats

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CatsViewModel(
    private val catsRepository: CatsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LatestUiState>(LatestUiState.Success("Брмяу!"))
    val uiState = _uiState as StateFlow<LatestUiState>

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                catsRepository.listenForCatFacts()
                    .catch {
//                        emit(LatestUiState.Success("Check the network, asshole!"))
                        emit(LatestUiState.Error(it))
//                        throw java.lang.Exception("Shit happened!")
                }
                    .retry(10)
//                    .flowOn(Dispatchers.IO)
                    .collect {it ->
                    withContext(Dispatchers.Main) { _uiState.value = it }
                }
            }
        }
    }
}

class CatsViewModelFactory(private val catsRepository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        CatsViewModel(catsRepository) as T
}

sealed class LatestUiState {
    data class Success(val fact: String): LatestUiState()
    data class Error(val exception: Throwable): LatestUiState()
}