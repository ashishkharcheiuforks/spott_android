package com.avon.spott.EmailLogin

import android.annotation.SuppressLint
import com.avon.spott.Data.Token
import com.avon.spott.Utils.Retrofit
import com.avon.spott.Utils.logd
import com.avon.spott.Utils.loge
import com.google.gson.GsonBuilder
import retrofit2.HttpException

class EmailLoginPresenter(val emailLoginView: EmailLoginContract.View) :
    EmailLoginContract.Presenter {

    private val TAG = "EmailLoginPresenter"

    init {
        emailLoginView.presenter = this
    }

    override fun openFindPW() {
        emailLoginView.showFindPWUi()
    }

    override fun navigateUp() {
        emailLoginView.navigateUp()
    }

    @SuppressLint("CheckResult")
    override fun signIn(baseUrl: String, email: String, password: String) {

        Retrofit(baseUrl).signIn("/spott/token", email, password).subscribe({ response ->
            logd(TAG, "response code: ${response.code()}, response body : ${response.body()}")
            val jsonObj = response.body()
            if(jsonObj != null) {
                val token = GsonBuilder().create().fromJson(jsonObj, Token::class.java)
                emailLoginView.showMainUi(token)
            }
        }, { throwable ->
            loge(TAG, throwable.message)
            if (throwable is HttpException) {
                val exception = throwable
                loge(
                    TAG,
                    "http exception code: ${exception.code()}, http exception message: ${exception.message()}"
                )
            }
        })
    }
}