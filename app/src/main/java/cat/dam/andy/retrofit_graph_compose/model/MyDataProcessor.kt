package cat.dam.andy.retrofit_graph_compose.model

import MyDataResponse

class MyDataProcessor(private val myDataResponse: MyDataResponse) {

    fun getEvolutionDataForMen(): List<Pair<String, Int>> {
        return myDataResponse.result.records.map { it.ANY to it.HOMES.toInt() }
    }

    fun getEvolutionDataForWomen(): List<Pair<String, Int>> {
        return myDataResponse.result.records.map { it.ANY to it.DONES.toInt() }
    }

    fun getEvolutionDataTotal(): List<Pair<String, Int>> {
        return myDataResponse.result.records.map { it.ANY to it.TOTAL.toInt() }
    }
}
