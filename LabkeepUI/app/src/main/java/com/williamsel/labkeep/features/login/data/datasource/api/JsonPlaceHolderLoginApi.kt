package com.williamsel.labkeep.features.login.data.datasource.api

import com.williamsel.labkeep.features.login.data.datasource.models.LoginDto
import retrofit2.http.Body
import retrofit2.http.POST

interface JsonPlaceHolderLoginApi {
    @POST("/login")
    suspend fun login(@Body loginRequest: LoginDto): LoginDto
}


