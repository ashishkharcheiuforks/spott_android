package com.avon.spott.Email

import com.avon.spott.BaseView
import com.avon.spott.Data.Number

interface EmailContract {
    interface View : BaseView<Presenter> {
        fun showPasswordUi()
        fun navigateUp()
        fun isEmail(valid: Boolean)
        fun isNumber(bool:Boolean)
        fun getNumber(number:Number)
        fun showError(msg:String)
    }

    interface Presenter {
        fun openPassword()
        fun navigateUp()
        fun isEmail(email: String)
        fun isNumber(number:String)
        fun sendEmail(isEmail:Boolean, baseUrl:String, email: String)
        fun confirm(number:Number, str:String)
    }
}