package com.example.qiita.di

import com.example.qiita.api.QiitaApi
import com.example.qiita.api.QiitaApiImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {

    @Binds
    @Singleton
    abstract fun bindQiitaApi(impl: QiitaApiImpl): QiitaApi
}