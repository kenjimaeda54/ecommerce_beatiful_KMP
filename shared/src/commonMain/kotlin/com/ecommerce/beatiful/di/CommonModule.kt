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
import com.ecommerce.beatiful.viewModel.AmazonProductCategoryViewModel
import com.ecommerce.beatiful.viewModel.AmazonSearchProductViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(
        clientModule,
        viewModelModule,
        repositoryModule,
        localResourceModule,
        driverSQLModule,
        coreDatabase
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
    single { AmazonSearchProductViewModel() }
    single { AmazonProductCategoryViewModel()}
}

fun initKoin() = initKoin {

}