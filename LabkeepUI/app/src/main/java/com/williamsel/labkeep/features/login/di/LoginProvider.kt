package com.williamsel.labkeep.features.login.di

import com.williamsel.labkeep.features.login.data.datasource.api.JsonPlaceHolderLoginApi
import com.williamsel.labkeep.features.login.data.repositories.LoginRepositoryImpl
import com.williamsel.labkeep.features.login.domain.repositories.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): JsonPlaceHolderLoginApi =
        retrofit.create(JsonPlaceHolderLoginApi::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginBindsModule {

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository
}