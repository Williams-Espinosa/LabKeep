package com.williamsel.labkeep.features.login.di

import com.williamsel.labkeep.core.network.RetrofitClient
import com.williamsel.labkeep.features.login.data.repositories.LoginRepositoryImpl
import com.williamsel.labkeep.features.login.data.datasource.api.JsonPlaceHolderLoginApi
import com.williamsel.labkeep.features.login.domain.repositories.LoginRepository
import com.williamsel.labkeep.features.login.domain.usescases.PostLoginUseCase

class LoginProvider {
    private val retrofit = RetrofitClient.retrofit

    private val apiService : JsonPlaceHolderLoginApi by lazy {
        retrofit.create(JsonPlaceHolderLoginApi::class.java)
    }

    private val myRepository : LoginRepository by lazy {
        LoginRepositoryImpl(apiService)
    }

    private val postLoginUseCase : PostLoginUseCase by lazy {
        PostLoginUseCase(myRepository)
    }

    val loginViewModelFactory : LoginViewModelFactory by lazy {
        LoginViewModelFactory(postLoginUseCase)

    }
}