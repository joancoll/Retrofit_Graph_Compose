package cat.dam.andy.retrofit_graph_compose.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.dam.andy.retrofit_graph_compose.network.MyApi
import cat.dam.andy.retrofit_graph_compose.model.MyDataProcessor
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

    data class Error(val errorMessage: String? = null) : MyUiState
    object Loading : MyUiState
}

class MyViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var myUiState: MyUiState by mutableStateOf(MyUiState.Loading)
        private set

    // Add a new property for the processed data
    var evolutionData: MyDataProcessor? by mutableStateOf(null)
        private set

    var isTextMode: Boolean by mutableStateOf(true)
        private set

    /**
     * Call getMyData() on init so we can display status immediately.
     */
    init {
        getMyData()
    }

    /**
     * Gets information from the API Retrofit service and updates the
     * [MyDataModel] [List] [MutableList].
     */
    fun getMyData() {
        viewModelScope.launch {
            myUiState = MyUiState.Loading
            myUiState = try {
                val myDataResponse = MyApi.retrofitService.getData()
                // Process the data using MyDataProcessor
                val datasetNumber = myDataResponse.result.records.size
                Log.d("MyViewModel", "Data found: ${datasetNumber} datasets")
                evolutionData = MyDataProcessor(myDataResponse)

                if (evolutionData != null) {
                    val totalEvolution = evolutionData!!.getEvolutionDataTotal()
                        .sortedBy { it.first }

                    Log.d("MyViewModel", "Total Evolution: $totalEvolution")

                    val totalMaleEvolution = evolutionData!!.getEvolutionDataForMen()
                        .sortedBy { it.first }

                    Log.d("MyViewModel", "Total Male Evolution: $totalMaleEvolution")

                    val totalFemaleEvolution = evolutionData!!.getEvolutionDataForWomen()
                        .sortedBy { it.first }

                    Log.d("MyViewModel", "Total Female Evolution: $totalFemaleEvolution")

                    MyUiState.Success(
                        datasetNumber,
                        totalEvolution,
                        totalFemaleEvolution,
                        totalMaleEvolution
                    )
                } else {
                    Log.e("MyViewModel", "Data is null")
                    MyUiState.Error("Data is null")
                }
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
