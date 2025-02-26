package cat.dam.andy.retrofit_graph_compose.model

import MyDataResponse
import kotlinx.serialization.InternalSerializationApi

class MyDataProcessor @OptIn(InternalSerializationApi::class) constructor(private val myDataResponse: MyDataResponse) {

    @OptIn(InternalSerializationApi::class)
    fun getEvolutionDataForMen(): List<Pair<String, Int>> {
        return myDataResponse.result.records.map { it.ANY.toString() to it.HOMES }
    }

    @OptIn(InternalSerializationApi::class)
    fun getEvolutionDataForWomen(): List<Pair<String, Int>> {
        return myDataResponse.result.records.map { it.ANY.toString() to it.DONES }
    }

    @OptIn(InternalSerializationApi::class)
    fun getEvolutionDataTotal(): List<Pair<String, Int>> {
        return myDataResponse.result.records.map { it.ANY.toString() to it.TOTAL }
    }
}