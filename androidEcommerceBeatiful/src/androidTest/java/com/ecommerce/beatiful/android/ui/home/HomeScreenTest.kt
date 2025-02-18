package com.ecommerce.beatiful.android.ui.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ecommerce.beatiful.android.ui.screens.home.BACKPACK_ICON
import com.ecommerce.beatiful.android.ui.screens.home.CLEAN_ICON
import com.ecommerce.beatiful.android.ui.screens.home.ELECTRONICS_ICON
import com.ecommerce.beatiful.android.ui.screens.home.HomeScreen
import com.ecommerce.beatiful.android.ui.screens.home.VIDEO_GAMES_ICON
import com.ecommerce.beatiful.android.util.TestTags
import com.ecommerce.beatiful.android.util.categoryMap
import com.ecommerce.beatiful.mocks.FakeAmazonProductImplementation
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class HomeScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeAmazonProduct: FakeAmazonProductImplementation by inject()

    /*@Before
    poderia fazer globaal porem tenho if na minha arvore
    fun setup() {
    composeTestRule.setContent {
        HomeScreen()
    }
    }*/

    @Test
    fun should_populate_data_properly_products() {

        composeTestRule.setContent {
            HomeScreen()
        }

        //querer impremir os testes
        //composeTestRule.onNodeWithTag(TestTags.LazyColumnHomeScreen.name).printToLog(TestTags.LazyColumnHomeScreen.name)

        //por ser uma lista e ter mais mochilas so uso o assertExists


        composeTestRule.onNode(hasTestTag(TestTags.LazyColumnHomeScreen.name))
            .performScrollToNode(hasText("Mochilas"))
            .assertExists()

        composeTestRule.onNode(hasTestTag(TestTags.LazyColumnHomeScreen.name))
            .performScrollToNode(hasText("Limpeza"))
            .assertExists()

        composeTestRule.onNode(hasTestTag(TestTags.LazyColumnHomeScreen.name))
            .performScrollToNode(hasText("Video Games"))
            .assertExists()

        composeTestRule.onNode(hasTestTag(TestTags.LazyColumnHomeScreen.name))
            .performScrollToNode(hasText("Eletronicos"))
            .assertExists()

        composeTestRule.onNode(hasTestTag(TestTags.LazyColumnHomeScreen.name))
            .performScrollToNode(hasText("Saude / Limpeza"))
            .assertExists()
    }


    @Test
    fun should_scroll_to_correct_section_when_category_clicked() {

        composeTestRule.setContent {
            HomeScreen()
        }

        categoryMap.forEach { (_, category) ->
            composeTestRule.onNodeWithTag(
                "${TestTags.CategoryButtonsRow.name}_$category"
            ).performClick()

            //aguarda animação
            composeTestRule.waitForIdle()

            composeTestRule.onNodeWithTag(TestTags.LazyColumnHomeScreen.name)
                .performScrollToNode(hasText(category))
                .assertExists()
                .assertIsDisplayed()

        }
    }

    @Test
    fun when_product_results_is_empty_should_not_render_items_categories() {
        //para funcinar corretamente o if preciso renderizaar a tela novamente
        //apos definir ffalos
        fakeAmazonProduct.setReturnDataWithProductsEmpty(true)

        composeTestRule.setContent {
            HomeScreen()
        }

        composeTestRule.waitForIdle()


        composeTestRule
            .onNodeWithTag(TestTags.RowItemsProducts.name)
            .assertDoesNotExist()


    }

    @Test
    fun whenTypeSearchInput_shouldUpdateText() {

        composeTestRule.setContent {
            HomeScreen()
        }

        val testText = "Mochila"

        composeTestRule.onNodeWithTag(TestTags.SearchInput.name)
            .performTextInput(testText)

        composeTestRule.onNodeWithTag(TestTags.SearchInput.name)
            .assertTextEquals(testText)

    }


    @Test
    fun should_populate_correct_icons_and_title_categories() {

        composeTestRule.setContent {
            HomeScreen()
        }

        // composeTestRule.onNodeWithTag(TestTags.CategoryButtonsRow.name).printToLog(TestTags.CategoryButtonsRow.name)

        categoryMap.entries.forEach { (index, value) ->
            composeTestRule.onNodeWithTag("${TestTags.CategoryButtonsRow.name}_${value}")
                .assertTextEquals(value)

        }

        composeTestRule.onNodeWithContentDescription(BACKPACK_ICON)
            .assertIsDisplayed()
            .assertExists()

        composeTestRule.onNodeWithContentDescription(ELECTRONICS_ICON)
            .assertIsDisplayed()
            .assertExists()

        composeTestRule.onNodeWithContentDescription(VIDEO_GAMES_ICON)
            .assertIsDisplayed()
            .assertExists()

        composeTestRule.onNodeWithContentDescription(CLEAN_ICON)
            .assertIsDisplayed()
            .assertExists()
    }

    @Test
    fun should_populate_correct_items_products() {
        fakeAmazonProduct.setReturnDataWithProductsEmpty(false)

        composeTestRule.setContent {
            HomeScreen()
        }

        //esperando aa UI carrear
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TestTags.LazyColumnHomeScreen.name)
            .performScrollToNode(hasText("Mochilas"))

        //apos realizar o scroll agguardar a UI carreggar
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText("Mochila linda para estudos")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("R$ 150.34")
            .assertExists()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(TestTags.ImageRowCategoryItems.name)
            .assertExists()
            .assertIsDisplayed()


    }
}