package com.williamsel.labkeep.features.editardispositivo.di

import com.williamsel.labkeep.features.editardispositivo.data.datasource.api.JsonPlaceHolderEditarDispositivoApi
import com.williamsel.labkeep.features.editardispositivo.data.repositories.EditarDispositivoImpl
import com.williamsel.labkeep.features.editardispositivo.domain.repositories.EditarDisposivoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EditarDispositivoModule {

    @Provides
    @Singleton
    fun provideEditarDispositivoApi(retrofit: Retrofit): JsonPlaceHolderEditarDispositivoApi =
        retrofit.create(JsonPlaceHolderEditarDispositivoApi::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class EditarDispositivoBindsModule {

    @Binds
    @Singleton
    abstract fun bindEditarDispositivoRepository(
        impl: EditarDispositivoImpl
    ): EditarDisposivoRepository
}