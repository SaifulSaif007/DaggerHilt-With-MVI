package com.saiful.moviestvseries.services.network.model


import com.google.gson.annotations.SerializedName

data class SeriesDetailsNetworkEntity(
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("created_by")
    val createdBy: List<CreatedBy?>? = null,
    @SerializedName("episode_run_time")
    val episodeRunTime: List<Int?>? = null,
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    @SerializedName("genres")
    val genres: List<Genre?>? = null,
    @SerializedName("homepage")
    val homepage: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("in_production")
    val inProduction: Boolean? = null,
    @SerializedName("languages")
    val languages: List<String?>? = null,
    @SerializedName("last_air_date")
    val lastAirDate: String? = null,
    @SerializedName("last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("networks")
    val networks: List<Network?>? = null,
    @SerializedName("next_episode_to_air")
    val nextEpisodeToAir: Any? = null,
    @SerializedName("number_of_episodes")
    val numberOfEpisodes: Int? = null,
    @SerializedName("number_of_seasons")
    val numberOfSeasons: Int? = null,
    @SerializedName("origin_country")
    val originCountry: List<String?>? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany?>? = null,
    @SerializedName("seasons")
    val seasons: List<Season?>? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,
    @SerializedName("videos")
    val videos : SeriesDetailsNetworkEntity.Videos? = null

) {
    data class CreatedBy(
        @SerializedName("credit_id")
        val creditId: String? = null,
        @SerializedName("gender")
        val gender: Int? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("profile_path")
        val profilePath: Any? = null
    )

    data class Genre(
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("name")
        val name: String? = null
    )

    data class LastEpisodeToAir(
        @SerializedName("air_date")
        val airDate: String? = null,
        @SerializedName("episode_number")
        val episodeNumber: Int? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("overview")
        val overview: String? = null,
        @SerializedName("production_code")
        val productionCode: String? = null,
        @SerializedName("season_number")
        val seasonNumber: Int? = null,
        @SerializedName("show_id")
        val showId: Int? = null,
        @SerializedName("still_path")
        val stillPath: String? = null,
        @SerializedName("vote_average")
        val voteAverage: Double? = null,
        @SerializedName("vote_count")
        val voteCount: Int? = null
    )

    data class Network(
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("logo_path")
        val logoPath: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("origin_country")
        val originCountry: String? = null
    )

    data class ProductionCompany(
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("logo_path")
        val logoPath: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("origin_country")
        val originCountry: String? = null
    )

    data class Season(
        @SerializedName("air_date")
        val airDate: String? = null,
        @SerializedName("episode_count")
        val episodeCount: Int? = null,
        @SerializedName("id")
        val id: Int? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("overview")
        val overview: String? = null,
        @SerializedName("poster_path")
        val posterPath: String? = null,
        @SerializedName("season_number")
        val seasonNumber: Int? = null
    )
    data class Videos(
        @SerializedName("results")
        val results : List<VideoResult>? = null
    )
    data class VideoResult(
        @SerializedName("id")
        val video_id: String?,
        @SerializedName("key")
        val key: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("site")
        val site: String? = null,
        @SerializedName("size")
        val size: String? = null,
        @SerializedName("type")
        val type: String? = null
    )
}