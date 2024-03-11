package cat.dam.andy.retrofit_graph_compose.network

import MyDataResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
// Font: https://seu-e.cat/ca/web/banyoles/dades-obertes/-/dadesobertes/showConjuntsDadesByTag?p_auth=Ai43Mcuw&_aoctransparenciadadesobertes_WAR_aoctransparenciaportlet_offset=40&_aoctransparenciadadesobertes_WAR_aoctransparenciaportlet_max=10#apiModal_resourcee0be5678-0bdd-48e0-99af-05cd5404a9a5
// http://dadesobertes.seu-e.cat/api/aoc/action/package_show?id=iio-ii-padro-municipal-habitants-per-municipi-any-i-sexe
    private const val BASE_URL = "https://dadesobertes.seu-e.cat/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    interface MyApiService {
        @GET("api/action/datastore_search?resource_id=e0be5678-0bdd-48e0-99af-05cd5404a9a5&filters={%22CODI_ENS%22:%221701570005%22}")
        suspend fun getData(): MyDataResponse
    }

    /**
     * A public Api object that exposes the lazy-initialized Retrofit service
     */
    object MyApi {
        val retrofitService: MyApiService by lazy {
            retrofit.create(MyApiService::class.java)
        }
}
