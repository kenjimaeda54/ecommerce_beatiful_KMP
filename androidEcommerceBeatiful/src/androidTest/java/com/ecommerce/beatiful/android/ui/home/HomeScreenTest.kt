package com.ecommerce.beatiful.android.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ecommerce.beatiful.android.ui.screens.home.BACKPACK_ICON
import com.ecommerce.beatiful.android.ui.screens.home.CLEAN_ICON
import com.ecommerce.beatiful.android.ui.screens.home.ELECTRONICS_ICON
import com.ecommerce.beatiful.android.ui.screens.home.HomeScreen
import com.ecommerce.beatiful.android.ui.screens.home.VIDEO_GAMES_ICON
import com.ecommerce.beatiful.android.util.TestTags
import com.ecommerce.beatiful.android.util.categoryMap
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        composeTestRule.setContent {
            HomeScreen()
        }
    }

    @Test
    fun should_populate_data_properly() {
        //querer impremir os testes
        //composeTestRule.onNodeWithTag(TestTags.LazyColumnHomeScreen.name).printToLog(TestTags.LazyColumnHomeScreen.name)

        categoryMap.entries.forEachIndexed { index, _ ->
            //por ser uma lista e ter mais mochilas so uso o assertExists
            val id = categoryMap.keys.toList()[index]
            composeTestRule.onNodeWithTag(TestTags.LazyColumnHomeScreen.name)
                .performScrollToNode(hasText(categoryMap[id] ?: ""))
                .assertExists()
        }
    }


    @Test
    fun should_scroll_to_correct_section_when_category_clicked() {
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
    fun should_populate_correct_icons_categories() {

        //nao estava encontrando dai adicinei no pai uma tag
        categoryMap.entries.forEachIndexed { index, _ ->
            val id = categoryMap.keys.toList()[index]
            composeTestRule.onNode(
                hasText(
                    categoryMap[id] ?: ""
                ) and hasParent(hasTestTag(TestTags.CategoryButtonsRow.name))
            )
                .assertExists()
                .assertIsDisplayed()
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
        composeTestRule.onNodeWithTag(TestTags.RowItemsProducts.name)
            .performScrollToNode(hasText("Mochila linda para estudos")).assertIsDisplayed()


        composeTestRule.onNodeWithTag(TestTags.RowItemsProducts.name)
            .performScrollToNode(hasText("R$ 150.34")).assertIsDisplayed()

        composeTestRule.onNodeWithTag(TestTags.ImageRowCategoryItems.name)
            .assertExists().assertIsDisplayed()


    }


}