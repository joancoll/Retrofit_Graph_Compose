import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@InternalSerializationApi @Serializable
data class MyDataResponse(
    val help: String,
    val success: Boolean,
    val result: Result
)

@InternalSerializationApi @Serializable
data class Result(
    val filters: Filters,
    @SerialName("include_total") val includeTotal: Boolean,
    val limit: Int,
    @SerialName("records_format") val recordsFormat: String,
    @SerialName("resource_id") val resourceId: String,
    @SerialName("total_estimation_threshold") val totalEstimationThreshold: String?,
    val records: List<Record>,
    val fields: List<Field>,
    @SerialName("_links") val links: Links,
    val total: Int,
    @SerialName("total_was_estimated") val totalWasEstimated: Boolean
)

@InternalSerializationApi @Serializable
data class Field(
    val id: String,
    val type: String
)

@InternalSerializationApi @Serializable
data class Record(
    @SerialName("_id") val id: Int,
    val ANY: Int,
    val HOMES: Int,
    val DONES: Int,
    val TOTAL: Int,
    @SerialName("CODI_ENS") val codiEns: Long,
    @SerialName("NOM_ENS") val nomEns: String
)

@InternalSerializationApi @Serializable
data class Links(
    val start: String,
    val next: String
)

@InternalSerializationApi @Serializable
data class Filters(
    @SerialName("CODI_ENS") val codiEns: String
)
