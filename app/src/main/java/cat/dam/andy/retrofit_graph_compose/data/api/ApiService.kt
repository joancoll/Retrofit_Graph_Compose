package cat.dam.andy.retrofit_graph_compose.data.api

import cat.dam.andy.retrofit_graph_compose.model.DataResponse
import retrofit2.http.GET

// Interfície per a les crides a l'API
interface ApiService {
    @GET("api/action/datastore_search?resource_id=e0be5678-0bdd-48e0-99af-05cd5404a9a5&filters={%22CODI_ENS%22:%221701570005%22}")
    suspend fun getData(): DataResponse
}

