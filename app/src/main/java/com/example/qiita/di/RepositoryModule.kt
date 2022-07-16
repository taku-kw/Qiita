package com.example.qiita.di

import com.example.qiita.repository.ArticleListRepository
import com.example.qiita.repository.ArticleListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindArticleListRepository(impl: ArticleListRepositoryImpl): ArticleListRepository
}