package cat.dam.andy.retrofit_graph_compose.model

import kotlinx.serialization.Serializable

// Defineix la resposta de les dades amb Gson
@Serializable
data class DataResponse(
   val result: ResultData
)

@Serializable
data class ResultData(
   val records: List<Record>
)

@Serializable
data class Record(
    val ANY: Int,
    val HOMES: Int,
    val DONES: Int,
    val TOTAL: Int
)
