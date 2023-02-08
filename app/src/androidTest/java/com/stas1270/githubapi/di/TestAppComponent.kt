package com.stas1270.githubapi.di

import com.stas1270.githubapi.MainActivityTest
import com.stas1270.githubapi.data.di.AppModule
import com.stas1270.githubapi.data.di.ApplicationComponent
import com.stas1270.githubapi.data.di.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FakeNetworkModule::class,
        FakeLocalDataModule::class,
        RepositoryModule::class,
        AppModule::class]
)
interface TestAppComponent : ApplicationComponent {
    fun inject(mainActivityTest: MainActivityTest)

}
