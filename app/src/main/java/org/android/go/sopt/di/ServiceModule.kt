package org.android.go.sopt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.go.sopt.data.service.FollowerService
import org.android.go.sopt.di.RetrofitModule.Retrofit2
import org.android.go.sopt.util.BaseUrlType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesFollowerService(@Retrofit2(BaseUrlType.REQRES) retrofit: Retrofit): FollowerService =
        retrofit.create(FollowerService::class.java)
}