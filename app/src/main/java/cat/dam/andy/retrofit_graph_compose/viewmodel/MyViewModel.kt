package cat.dam.andy.retrofit_graph_compose.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.dam.andy.retrofit_graph_compose.data.api.RetrofitClient.apiService
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface MyUiState {
    data class Success(
        val data: Int,
        val totalEvolution: List<Pair<String, Int>>,
        val totalFemaleEvolution: List<Pair<String, Int>>,
        val totalMaleEvolution: List<Pair<String, Int>>
    ) : MyUiState

    data class Error(val errorMessage: String?) : MyUiState
    object Loading : MyUiState
}

class MyViewModel : ViewModel() {
    var myUiState: MyUiState by mutableStateOf(MyUiState.Loading)
        private set

    var isTextMode: Boolean by mutableStateOf(true)
        private set

    init {
        getMyData()
    }

    fun getMyData() {
        viewModelScope.launch {
            myUiState = MyUiState.Loading
            myUiState = try {
                val myDataResponse = apiService.getData()
                val datasetNumber = myDataResponse.result.records.size
                Log.d("MyViewModel", "Data found: ${datasetNumber} datasets")

                val totalEvolution =
                    myDataResponse.result.records.map { it.ANY.toString() to it.TOTAL }
                        .sortedBy { it.first }

                Log.d("MyViewModel", "Total Evolution: $totalEvolution")

                val totalMaleEvolution =
                    myDataResponse.result.records.map { it.ANY.toString() to it.HOMES }
                        .sortedBy { it.first }

                Log.d("MyViewModel", "Total Male Evolution: $totalMaleEvolution")

                val totalFemaleEvolution =
                    myDataResponse.result.records.map { it.ANY.toString() to it.DONES }
                        .sortedBy { it.first }

                Log.d("MyViewModel", "Total Female Evolution: $totalFemaleEvolution")

                MyUiState.Success(
                    datasetNumber,
                    totalEvolution,
                    totalFemaleEvolution,
                    totalMaleEvolution
                )
            } catch (e: IOException) {
                Log.e("MyViewModel", "IOException: ${e.message}")
                MyUiState.Error(e.message)
            } catch (e: HttpException) {
                Log.e("MyViewModel", "HttpException: ${e.message}")
                MyUiState.Error(e.message)
            }
        }
    }

    fun setIsTextMode(isTextMode: Boolean) {
        this.isTextMode = isTextMode
    }
}