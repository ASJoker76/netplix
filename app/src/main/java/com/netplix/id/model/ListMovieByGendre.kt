package com.netplix.id.model

data class ListMovieByGendre(
    val page: Int,
    val results: List<ResultX>,
    val total_pages: Int,
    val total_results: Int
)