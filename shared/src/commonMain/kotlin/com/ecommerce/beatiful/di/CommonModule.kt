package com.ecommerce.beatiful.di

import com.ecommerce.beatiful.data.client.AmazonProductImplementation
import com.ecommerce.beatiful.data.client.ApolloClientApiImplementation
import com.ecommerce.beatiful.data.client.contracts.ApolloClientApi
import com.ecommerce.beatiful.data.client.SupabaseImplementaion
import com.ecommerce.beatiful.data.client.contracts.SupabaseClient
import com.ecommerce.beatiful.data.client.SupabaseClientImplementation
import com.ecommerce.beatiful.data.client.contracts.AmazonProduct
import com.ecommerce.beatiful.data.local.AmazonProductByCategoryResourceImplementation
import com.ecommerce.beatiful.data.local.AmazonProductSearchResource
import com.ecommerce.beatiful.data.local.contracts.AmazonProductByCategoryResource
import com.ecommerce.beatiful.data.repository.AmazonProductByCategoryRepository
import com.ecommerce.beatiful.data.repository.AmazonSearchProductRepository
import com.ecommerce.beatiful.db.EcommerceDB
import com.ecommerce.beatiful.mocks.FakeAmazonProductCategoryResource
import com.ecommerce.beatiful.mocks.FakeAmazonProductImplementation
import com.ecommerce.beatiful.viewModel.HomeViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.binds
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration,isTesting: Boolean) = startKoin {
    appDeclaration()

    //cliente e o modulo com os daados reais do backend
    //isso tambem vale para o localResource
    //por isso ambos nao sao comuns
    //tambem se repaaraar estao implmeentado umaa interface que e abstracao para saber
    //se e dado real ou falso
    val commonModules = listOf(
        viewModelModule,
        repositoryModule,
        driverSQLModule,
        coreDatabase,
    )

    modules(
        if (isTesting) {
            commonModules  + testModule
        }else {
            commonModules + clientModule + localResourceModule
        }
    )

}

//https://github.com/hlnstepanova/kmpizza-repo/blob/main/shared/src/commonMain/kotlin/dev/tutorial/kmpizza/local/RecipeLocalSource.kt
//nao esquecer o model Serializer
//para criar o adapter preciso importar la no sq o List que e do kotlin e a String
//ou seja os dados complexos do kotlin
//classe precisa ser no maisculo
val coreDatabase = module {
    single {
        EcommerceDB(
            get(),

        )
    }
}

val clientModule = module {
    single<AmazonProduct> {  AmazonProductImplementation(get()) }
    single { SupabaseImplementaion(get()) }
    single<ApolloClientApi> { ApolloClientApiImplementation() }
    single<SupabaseClient> { SupabaseClientImplementation()  }
}

val localResourceModule = module {
    single { AmazonProductSearchResource(get()) }
    single<AmazonProductByCategoryResource> { AmazonProductByCategoryResourceImplementation() }
}

val repositoryModule = module {
    single { AmazonSearchProductRepository() }
    single { AmazonProductByCategoryRepository() }

}


val viewModelModule = module {
    single { HomeViewModel() }
}

private val testModule = module {
    //precisa todos ser koinComponent
    //preciso que os dados locais tenham a interface implementada
    single<AmazonProduct> {
        FakeAmazonProductImplementation()
    } binds arrayOf(FakeAmazonProductImplementation::class, AmazonProductImplementation::class)

    single<AmazonProductByCategoryResource> {
        FakeAmazonProductCategoryResource()
    } binds arrayOf(
        FakeAmazonProductCategoryResource::class,
        AmazonProductByCategoryResourceImplementation::class
    ) //repositorio que consome o ResouceImplmentation tem que ter a intefce
    // AmazonProductByCategoryResource no inject()
}

fun initKoin(isTesting: Boolean) = initKoin({},isTesting)
