package com.netplix.id.connection

import io.reactivex.Observable
import com.netplix.id.connection.Host.APIKEY
import com.netplix.id.model.ListGenre
import com.netplix.id.model.ListMovie
import com.netplix.id.model.ListMovieByGendre
import com.netplix.id.model.ResTrailer
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    @GET("movie/now_playing?" + APIKEY + "&language=en-US")
    fun getListMovie(): Observable<Response<ListMovie>>

    @GET("genre/movie/list?" + APIKEY + "&language=en-US")
    fun getListGendre(): Observable<Response<ListGenre>>

    @GET("discover/movie?" + APIKEY + "&language=en-US" + "&")
    fun getListMovieByGendre(@Query("with_genres") id:Int): Observable<Response<ListMovieByGendre>>

    @GET("movie/{id}/videos?" + APIKEY + "&language=en-US")
    fun getTrailer(@Path("id") id:Int): Observable<Response<ResTrailer>>
}