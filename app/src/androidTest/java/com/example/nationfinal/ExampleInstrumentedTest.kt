package com.example.nationfinal

import androidx.activity.viewModels
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nationfinal.viewmodel.SignInViewModel

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
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.nationfinal", appContext.packageName)
    }

    @get:Rule(order = 1) val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun validEmail() {
        // доступ к модели представления
        val myViewModel = composeRule.activity.viewModels<SignInViewModel>().value
        assertTrue(myViewModel.isValidEmail("gallaktio@bk.ru"))
    }

    @Test
    fun validPassword() {
        // доступ к модели представления
        val myViewModel = composeRule.activity.viewModels<SignInViewModel>().value
        assertTrue(myViewModel.isValidPassword("123456"))
    }

    @Test
    fun alertEmail() {
        // доступ к модели представления
        val myViewModel = composeRule.activity.viewModels<SignInViewModel>().value
        assertEquals(myViewModel.alert, myViewModel.isValidEmail("gallak@bk.rU"))
    }

    @Test
    fun alertPassword() {
        val myViewModel = composeRule.activity.viewModels<SignInViewModel>().value
        assertEquals(myViewModel.alert, myViewModel.isValidPassword("12345"))
    }
}