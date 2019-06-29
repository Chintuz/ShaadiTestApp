package com.app.shaditest

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shaditest.api.Api
import com.app.shaditest.api.ApiHelper
import com.app.shaditest.model.ErrorData
import com.app.shaditest.model.ResponseData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MainViewModel : ViewModel() {

    private var api = ApiHelper().init().create(Api::class.java)

    var responseData = MutableLiveData<ResponseData>()

    var errorData = MutableLiveData<ErrorData>()

    fun getData(): Disposable {
        return api.getData(Const.ITEM_COUNT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response: Response<ResponseData>? ->
                    if (response != null && response.isSuccessful && response.body() != null) {
                        responseData.value = response.body()
                    } else {
                        errorData.value = ErrorData(response?.code()!!, Const.ERROR_MESSAGE)
                    }
                },
                { t: Throwable? ->
                    Log.e("error", t?.message)
                    errorData.value = ErrorData(400, Const.ERROR_MESSAGE)
                }
            )
    }

}