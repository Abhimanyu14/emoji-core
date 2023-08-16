package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkEmoji(
    @SerialName("character")
    val character: String,

    @SerialName("code_point")
    val codePoint: String,

    @SerialName("group")
    val group: String,

    @SerialName("subgroup")
    val subgroup: String,

    @SerialName("unicode_name")
    val unicodeName: String,
)
