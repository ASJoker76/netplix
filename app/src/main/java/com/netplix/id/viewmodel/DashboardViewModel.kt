package com.netplix.id.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.netplix.id.connection.API
import com.netplix.id.model.ListMovie
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    var _mainListMovie = MutableLiveData<ListMovie>()
    var mainListMovie: LiveData<ListMovie> = _mainListMovie


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

}