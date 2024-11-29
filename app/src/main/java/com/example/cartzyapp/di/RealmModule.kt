package com.example.cartzyapp.di

import com.example.cartzyapp.model.CartItem
import com.example.cartzyapp.model.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides
    @Singleton
    fun provideRealm(): Realm {
        val config = RealmConfiguration.Builder(schema = setOf(User::class, CartItem::class))
            .schemaVersion(4)
            .compactOnLaunch { totalBytes, usedBytes ->
                (totalBytes > 50 * 1024 * 1024) && (usedBytes.toDouble() / totalBytes.toDouble() < 0.5)
            }
            .build()
        return Realm.open(config)
    }
}
