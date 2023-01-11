package com.netplix.id.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.netplix.id.connection.API
import com.netplix.id.model.ListGenre
import com.netplix.id.model.ListMovie
import com.netplix.id.model.ListMovieByGendre
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    var _mainListMovie = MutableLiveData<ListMovie>()
    var mainListMovie: LiveData<ListMovie> = _mainListMovie

    var _mainListGendre = MutableLiveData<ListGenre>()
    var mainListGendre: LiveData<ListGenre> = _mainListGendre

    var _mainListByGendre = MutableLiveData<ListMovieByGendre>()
    var mainListByGendre: LiveData<ListMovieByGendre> = _mainListByGendre


    fun setListMovie() {
        API.buildService().getListMovie()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : Observer<Response<ListMovie>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

                override fun onNext(t: Response<ListMovie>) {
                    val statusCode: Int = t.code()
                    if (statusCode == 200) {
                        var data = t.body()
                        _mainListMovie.postValue(data!! as ListMovie?)
                    } else if (statusCode == 204) {

                    }
                }
            })
    }

    fun setListGenre() {
        API.buildService().getListGendre()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : Observer<Response<ListGenre>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

                override fun onNext(t: Response<ListGenre>) {
                    val statusCode: Int = t.code()
                    if (statusCode == 200) {
                        var data = t.body()
                        _mainListGendre.postValue(data!! as ListGenre?)
                    } else if (statusCode == 204) {

                    }
                }
            })
    }

    fun setListbygenre(id : Int) {
        API.buildService().getListMovieByGendre(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : Observer<Response<ListMovieByGendre>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

                override fun onNext(t: Response<ListMovieByGendre>) {
                    val statusCode: Int = t.code()
                    if (statusCode == 200) {
                        var data = t.body()
                        _mainListByGendre.postValue(data!! as ListMovieByGendre?)
                    } else if (statusCode == 204) {

                    }
                }
            })
    }

}