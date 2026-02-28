package com.williamsel.labkeep.features.descripciondispositivo.di

import com.williamsel.labkeep.features.descripciondispositivo.data.datasource.api.JsonPlaceHolderDescripcionDispositivoApi
import com.williamsel.labkeep.features.descripciondispositivo.data.repositories.DescripcionDispositivoImpl
import com.williamsel.labkeep.features.descripciondispositivo.domain.repositories.DescripcionDispositivoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DescripcionDispositivoModule {

    @Provides
    @Singleton
    fun provideDescripcionDispositivoApi(retrofit: Retrofit): JsonPlaceHolderDescripcionDispositivoApi =
        retrofit.create(JsonPlaceHolderDescripcionDispositivoApi::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DescripcionDispositivoBindsModule {

    @Binds
    @Singleton
    abstract fun bindDescripcionDispositivoRepository(
        impl: DescripcionDispositivoImpl
    ): DescripcionDispositivoRepository
}