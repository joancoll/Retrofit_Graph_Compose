package cat.dam.andy.retrofit_graph_compose.network

import MyDataResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://dadesobertes.seu-e.cat/"

// Configura el Json per ignorar claus desconegudes
private val json = Json {
    ignoreUnknownKeys = true
}

// Configuració de Retrofit
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .build()

// Interfície per a les crides a l'API
interface MyApiService {
    @OptIn(InternalSerializationApi::class)
    @GET("api/action/datastore_search?resource_id=e0be5678-0bdd-48e0-99af-05cd5404a9a5&filters={%22CODI_ENS%22:%221701570005%22}")
    suspend fun getData(): MyDataResponse
}

// Objecte per accedir al servei de Retrofit
object MyApi {
    val retrofitService: MyApiService by lazy {
        retrofit.create(MyApiService::class.java)
    }
}