package com.netplix.id.connection

import io.reactivex.Observable
import com.netplix.id.connection.Host.APIKEY
import com.netplix.id.model.ListMovie
import retrofit2.http.GET

interface Service {

    @GET("movie/now_playing?" + APIKEY + "&language=en-US")
    fun getListMovie(): Observable<ListMovie>

}