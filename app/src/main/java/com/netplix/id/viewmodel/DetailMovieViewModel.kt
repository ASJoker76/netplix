package com.netplix.id.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.netplix.id.connection.API
import com.netplix.id.model.ResTrailer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class DetailMovieViewModel : ViewModel() {

    var _movieLiveData = MutableLiveData<ResTrailer>()
    var movieLiveData: LiveData<ResTrailer> = _movieLiveData


    fun panggilapilist(id: Int) {
        API.buildService().getTrailer(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : Observer<Response<ResTrailer>> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

                override fun onNext(t: Response<ResTrailer>) {
                    val statusCode: Int = t.code()
                    if (statusCode == 200) {
                        var data = t.body()
                        _movieLiveData.postValue(data!! as ResTrailer)
                    } else if (statusCode == 204) {

                    }
                }
            })
    }

}