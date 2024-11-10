package com.ecommerce.beatiful.di

import com.ecommerce.beatiful.data.client.AmazonProduct
import com.ecommerce.beatiful.data.client.Apollo.ApolloImplementation
import com.ecommerce.beatiful.data.client.Apollo.IApolloClient
import com.ecommerce.beatiful.data.client.Supabase
import com.ecommerce.beatiful.data.client.SupabaseClient.SupabaseClient
import com.ecommerce.beatiful.data.client.SupabaseClientImplementation.SupabaseClientImplementation
import com.ecommerce.beatiful.data.local.AmazonProductByCategoryResource
import com.ecommerce.beatiful.data.local.AmazonProductSearchResource
import com.ecommerce.beatiful.data.repository.AmazonProductByCategoryRepository
import com.ecommerce.beatiful.data.repository.AmazonSearchProductRepository
import com.ecommerce.beatiful.db.EcommerceDB
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
private val coreDatabase = module {
    single {
        EcommerceDB(
            get(),

        )
    }
}

private val clientModule = module {
    single {  AmazonProduct(get()) }
    single { Supabase(get()) }
    factory<IApolloClient> { ApolloImplementation() }
    factory<SupabaseClient> { SupabaseClientImplementation()  }
}

private val localResourceModule = module {
    single { AmazonProductSearchResource(get()) }
    single { AmazonProductByCategoryResource(get()) }
}

private val repositoryModule = module {
    single { AmazonSearchProductRepository() }
    single { AmazonProductByCategoryRepository() }

}


private  val viewModelModule = module {
    single { AmazonSearchProductViewModel() }
}
fun initKoin() = initKoin {

}