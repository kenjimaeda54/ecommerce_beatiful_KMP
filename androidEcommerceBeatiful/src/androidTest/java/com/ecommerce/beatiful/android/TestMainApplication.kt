package com.ecommerce.beatiful.android

import android.app.Application
import com.ecommerce.beatiful.data.client.AmazonProductImplementation
import com.ecommerce.beatiful.data.client.contracts.AmazonProduct
import com.ecommerce.beatiful.data.local.AmazonProductByCategoryResourceImplementation
import com.ecommerce.beatiful.data.local.contracts.AmazonProductByCategoryResource
import com.ecommerce.beatiful.di.coreDatabase
import com.ecommerce.beatiful.di.driverSQLModule
import com.ecommerce.beatiful.di.initKoin
import com.ecommerce.beatiful.di.repositoryModule
import com.ecommerce.beatiful.di.viewModelModule
import com.ecommerce.beatiful.mocks.FakeAmazonProductCategoryResource
import com.ecommerce.beatiful.mocks.FakeAmazonProductImplementation
import org.koin.core.context.startKoin
import org.koin.dsl.binds
import org.koin.dsl.module

class TestMainApplication: Application() {

//    private val testModule = module {
//        //precisa todos ser koinComponent
//        //preciso que os dados locais tenham a interface implementada
//        single<AmazonProduct> {
//            FakeAmazonProductImplementation()
//        } binds arrayOf(FakeAmazonProductImplementation::class, AmazonProductImplementation::class)
//
//        single<AmazonProductByCategoryResource> {
//            FakeAmazonProductCategoryResource()
//        } binds arrayOf(
//            FakeAmazonProductCategoryResource::class,
//            AmazonProductByCategoryResourceImplementation::class
//        ) //repositorio que consome o ResouceImplmentation tem que ter a intefce
//        // AmazonProductByCategoryResource no inject()
//    }

    override fun onCreate() {
        super.onCreate()
        initKoin(true)
//        startKoin {
//            modules(
//                viewModelModule,
//                repositoryModule,
//                driverSQLModule,
//                coreDatabase,
//                testModule
//            )
//        }
    }

}