package com.avon.spott.Email

import com.avon.spott.Data.Number
import com.avon.spott.Data.User
import com.avon.spott.Utils.Parser
import com.avon.spott.Utils.Retrofit
import com.avon.spott.Utils.Validator
import com.avon.spott.Utils.logd
import retrofit2.HttpException

class EmailPresenter(val emailView: EmailContract.View) : EmailContract.Presenter {

    private val TAG = "EMAILPRESENTER"

    init {
        emailView.presenter = this
    }

    override fun openPassword() {
        emailView.showPasswordUi()
    }

    override fun navigateUp() {
        emailView.navigateUp()
    }

    override fun isEmail(email: String) {
        emailView.isEmail(Validator.validEmail(email))
    }

    override fun isNumber(number: String) {
        emailView.isNumber(Validator.validNumber(number))
    }

    override fun confirm(number: Number, str: String) {
        if (number.code.equals(str)) {
            emailView.showPasswordUi()
        } else {
            emailView.showError("인증번호를 확인해주세요")
        }
    }

    override fun sendEmail(isEmail:Boolean, baseUrl: String, email: String) {
        if (isEmail) {
            emailView.showError("이메일을 확인해주세요")
        } else {
            Retrofit(baseUrl).get("/spott/email-authen", Parser.toJson(User(email)))
                .subscribe({ response ->
                    logd(TAG, response.body())
                    val number = response.body()?.let { Parser.fromJson<Number>(it) }
                    if (!number?.code?.equals("")!!) {
                        emailView.getNumber(number)
                    }
                }, { throwable ->
                    logd(TAG, throwable.message)
                    if (throwable is HttpException) {
                        logd(
                            TAG,
                            "http exception code : ${throwable.code()}, http exception message: ${throwable.message()}"
                        )
                    }
                })
        }
    }
}