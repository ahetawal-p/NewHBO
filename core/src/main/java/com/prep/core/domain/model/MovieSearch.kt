package com.prep.core.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieSearchPayload(
    @Json(name = "Search")
    val result: Array<MovieSearch>?,

    val totalResults: String?,

    @Json(name = "Error")
    val error: String?,

    @Json(name = "Response")
    val status: String

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieSearchPayload

        if (!result.contentEquals(other.result)) return false
        if (totalResults != other.totalResults) return false

        return true
    }

    override fun hashCode(): Int {
        var result1 = result.contentHashCode()
        result1 = 31 * result1 + totalResults.hashCode()
        return result1
    }


}


@JsonClass(generateAdapter = true)
data class MovieSearch(
    @Json(name = "imdbID")
    val id: String,

    @Json(name = "Title")
    val title: String,

    @Json(name = "Poster")
    val img: String,

    @Json(name = "Year")
    val year: String
)
