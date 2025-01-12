package com.ecommerce.beatiful.android.ui.home

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ecommerce.beatiful.android.ui.screens.home.HomeScreen
import com.ecommerce.beatiful.android.ui.screens.home.TITLE_PRODUCT
import com.ecommerce.beatiful.android.util.TestTags
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenInstrument {

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
        composeTestRule.onNodeWithTag(TestTags.LazyColumnHomeScreen.name)
            .performScrollToNode(hasText("Mochilas")).assertIsDisplayed()



    }




}