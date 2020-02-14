package com.avon.spott.EditMyinfo

import com.avon.spott.BaseView
import com.avon.spott.Data.UserInfo
import com.avon.spott.Utils.MySharedPreferences

interface EditMyInfoContract {

    interface View: BaseView<Presenter> {
        fun navigateUp() // 뒤로가기
        fun getUserInfo(userInfo: UserInfo)// 유저 정보 세팅하기
        // 프로필 이미지 편집
        fun getNickname(result:Boolean)// 닉네임 수정
        // 비밀번호 변경
        // 회원 탈퇴
        fun loginActivity() // 로그아웃
    }

    interface Presenter {
        fun navigateUp() // 뒤로가기
        fun getUser(baseUrl:String, access:String) // 유저 정보 가져오기
        fun setNickname(baseUrl:String, token:String, nickname:String)// 닉네임 수정
        fun signOut(pref:MySharedPreferences) // 로그아웃
    }
}