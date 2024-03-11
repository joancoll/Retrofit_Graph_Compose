import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyDataResponse(
    val help: String,
    val success: Boolean,
    val result: Result
)

@Serializable
data class Result(
    @SerialName("resource_id") val resourceId: String,
    val fields: List<Field>,
    val records: List<Record>,
    @SerialName("_links") val links: Links,
    val filters: Filters,
    val total: Int
)

@Serializable
data class Field(
    val type: String,
    val id: String
)

@Serializable
data class Record(
    val HOMES: String,
    @SerialName("CODI_ENS") val codiEns: String,
    @SerialName("_id") val id: Int,
    val DONES: String,
    @SerialName("NOM_ENS") val nomEns: String,
    val TOTAL: String,
    val ANY: String
)

@Serializable
data class Links(
    val start: String,
    val next: String
)

@Serializable
data class Filters(
    @SerialName("CODI_ENS") val codiEns: String
)
