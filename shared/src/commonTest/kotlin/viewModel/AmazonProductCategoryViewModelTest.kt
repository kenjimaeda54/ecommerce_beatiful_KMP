package viewModel


import com.ecommerce.beatiful.AmazonProductByCategoryQuery
import com.ecommerce.beatiful.data.client.AmazonProductImplementation
import com.ecommerce.beatiful.data.client.contracts.AmazonProduct
import com.ecommerce.beatiful.data.local.AmazonProductByCategoryResourceImplementation
import com.ecommerce.beatiful.data.local.contracts.AmazonProductByCategoryResource
import com.ecommerce.beatiful.di.coreDatabase
import com.ecommerce.beatiful.di.driverSQLModule
import com.ecommerce.beatiful.di.repositoryModule
import com.ecommerce.beatiful.di.viewModelModule
import com.ecommerce.beatiful.viewModel.AmazonProductCategoryViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import mocks.FakeAmazonProductImplementation
import org.koin.core.context.startKoin
import org.koin.dsl.module
import kotlin.test.Test
import org.koin.test.KoinTest
import kotlin.test.BeforeTest
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import mocks.FakeAmazonProductCategoryResource
import mocks.FakeAmazonProductCategoryResource.Companion.BREADCRUMB_PATH_FROM_DATABASE_BACKPACKS
import mocks.FakeAmazonProductCategoryResource.Companion.BREADCRUMB_PATH_FROM_DATABASE_CLEANING
import mocks.FakeAmazonProductImplementation.Companion.`BREADCRUMB_PATH`
import org.koin.core.context.stopKoin
import org.koin.dsl.binds
import org.koin.test.get
import org.koin.test.inject
import utils.IdsProductsCategory
import kotlin.test.AfterTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue


//https://github.com/Huctor/News/blob/main/composeApp/src/commonTest/kotlin/NewsViewModelTest.kt
class AmazonProductCategoryViewModelTest : KoinTest {
    private val viewModelTest: AmazonProductCategoryViewModel by inject()
    private val data = AmazonProductByCategoryQuery.Data(
        amazonProductCategory = AmazonProductByCategoryQuery.AmazonProductCategory(
            name = "Utensilios em geral",
            breadcrumbPath = BREADCRUMB_PATH_FROM_DATABASE_CLEANING,
            id = IdsProductsCategory.CLEANING.value,
            productResults = AmazonProductByCategoryQuery.ProductResults(
                results = listOf(
                    AmazonProductByCategoryQuery.Result(
                        asin = "",
                        brand = "Moop",
                        imageUrls = listOf(
                            "https://github.com/kenjimaead5.png"
                        ),
                        price = AmazonProductByCategoryQuery.Price(
                            display = "R$ 30.34"
                        ),
                        rating = 4.35,
                        seller = AmazonProductByCategoryQuery.Seller(
                            logoUrl = "https://github.com/kenjimaead5.png",
                            name = "Moop",
                            rating = 4.96
                        ),
                        title = "Moop para limpezza em casa",
                        url = "https://github.com/kenjimaead5.png"
                    )
                )

            )
        )
    )


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

    @BeforeTest
    fun setUp() {
        startKoin {
            modules(
                viewModelModule,
                repositoryModule,
                driverSQLModule,
                coreDatabase,
                testModule
            )
        }
        Dispatchers.setMain(StandardTestDispatcher())
    }

    //precisa desse cara porque cada teste ira iniciar um dispatchs e um koin
    //dai a cada teste abaixo preciso inicilizar um novo
    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }


    @Test
    fun `should return amazonProductCategory if categoryID is correct`() =
        runTest {
            //id precisa ser mesma do android em compose ou seja
            //package com.ecommerce.beatiful.android.util

            //val categoryMap = mapOf(
            //  "360832011" to "Mochilas",
            //"15342831" to "Limpeza",
            //"16227130011" to "Video Games",
            //"172282" to "Eletronicos",
            //"3760901" to "Saude / Limpeza",
            //)
            ///porquqe ao colocar o target android para rodar ele ira
            //no pacote andriodEcommerBeatiful responsavel  pela UI
            viewModelTest
                .getProductByCategory(IdsProductsCategory.BACKPACK.value, 7)

            //runCurrent  e porque estamos lindando com dado assincrono
            //dai preciso colocar o value depois dele se  não o codigo sera exceutado imediamente
            //assim dara efeitos colaterais e gerar erro no teste ja que preciso caputar o results
            //que vem apois api do courtine
            runCurrent()

            val products = viewModelTest.listProductsCategory.value

            assertEquals(products?.first()?.results?.size, 1)
            assertEquals(
                products.first().breadcrumbPath, `BREADCRUMB_PATH`
            )

        }

    @Test
    fun `should return amazonProductCategory from Database if have data`() = runTest {
        //maneira de manipular nosso fake data
        get<FakeAmazonProductCategoryResource>().setHaveData(true)

        viewModelTest
            .getProductByCategory(IdsProductsCategory.BACKPACK.value, 7)

        runCurrent()

        val products = viewModelTest.listProductsCategory.value
        assertEquals(products?.first()?.results?.size, 1)
        assertEquals(
            products.first().breadcrumbPath, BREADCRUMB_PATH_FROM_DATABASE_BACKPACKS
        )

    }

    @Test
    fun `should return array empty if it fails to get the data from internet`() = runTest {
        get<FakeAmazonProductImplementation>().setShouldReturnError(true)

        viewModelTest
            .getProductByCategory(IdsProductsCategory.BACKPACK.value, 7)

        runCurrent()

        viewModelTest.listProductsCategory.value.first().results?.isEmpty()?.let { assertTrue(it) }

    }

    @Test
    fun `should update data from database`() = runTest {
        get<FakeAmazonProductCategoryResource>().setHaveData(true)

        viewModelTest
            .getProductByCategory(IdsProductsCategory.BACKPACK.value, 7)

        get<FakeAmazonProductCategoryResource>().insertAmazonProductByCategory(data)
        viewModelTest.getProductByCategory(IdsProductsCategory.CLEANING.value, 7)

        runCurrent()

        val products = viewModelTest.listProductsCategory.value[1]

        products.results?.let {
            assertTrue(it.isNotEmpty())
        }
        assertEquals(products.id, IdsProductsCategory.CLEANING.value)
        assertEquals(products.breadcrumbPath, BREADCRUMB_PATH_FROM_DATABASE_CLEANING)


    }

    @Test
    fun `should delete data from database`() = runTest {
        get<FakeAmazonProductCategoryResource>().setHaveData(true)

        viewModelTest
            .getProductByCategory(IdsProductsCategory.CLEANING.value, 7)

        get<FakeAmazonProductCategoryResource>().insertAmazonProductByCategory(data)
        get<FakeAmazonProductCategoryResource>().deleteAmazonProductByCategory(data.amazonProductCategory!!.id!!)

        runCurrent()
        val newProduct = viewModelTest.listProductsCategory.value[1]

        newProduct.results?.isEmpty()?.let { assertTrue(it) }

    }

}