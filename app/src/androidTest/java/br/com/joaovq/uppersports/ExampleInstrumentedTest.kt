package br.com.joaovq.uppersports

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.joaovq.uppersports.onboarding.presentation.compose.screens.HomeScreen
import br.com.joaovq.uppersports.ui.components.UpperSportsTopBar
import br.com.joaovq.uppersports.ui.theme.UpperSportsTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("br.com.joaovq.uppersports", appContext.packageName)
    }

    @Test
    fun click() {
        composeTestRule.setContent {
            UpperSportsTheme {
                UpperSportsTopBar()
            }
        }
        composeTestRule.onNodeWithText("UpperSports").performClick()
    }
}