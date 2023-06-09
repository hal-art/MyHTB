package com.halsec.myhtb.view.login

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.By
import com.halsec.myhtb.R
import com.halsec.myhtb.common.uiUtils
import com.halsec.myhtb.view.main.MainActivityView
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginPageTests {


    @get:Rule
    var mActivityTestRule = ActivityTestRule(MainActivityView::class.java)

    /**
     * 本番環境で使用するEmailアドレス情報をconfig.propertiesファイルから取得する
     */
    //TODO issue-#5
    private val correctEmailAddress
        get() = ""

    /**
     * 本番環境で使用するパスワード情報をconfig.propertiesファイルから取得する
     */
    //TODO issue-#5
    private val correctPassword
        get() = ""

    /**
     * 不正メールアドレスセットによるログイン失敗
     */
    @Test
    fun loginFailedIncorrectEmail(){
        val loginPageOperation = LoginPageOperations()
        loginPageOperation
            .setEmail("test@gmail.com")
            .setPassword(correctPassword)
            .login()

        onView(withId(R.id.ConnectionStatusTextView))
            .check(matches(isDisplayed()))
            .check(matches(withText("No Connection")))
    }

    /**
     * 不正パスワードセットによるログイン失敗
     */
    @Test
    fun loginFailedIncorrectPassword(){
        val loginPageOperation = LoginPageOperations()
        loginPageOperation
            .setEmail(correctEmailAddress)
            .setPassword("hogehoge")
            .login()

        onView(withId(R.id.ConnectionStatusTextView))
            .check(matches(isDisplayed()))
            .check(matches(withText("No Connection")))
    }

    /**
     * ログイン成功
     */
    @Test
    fun loginSuccessful(){
        val loginPageOperation = LoginPageOperations()
        loginPageOperation
            .setEmail(correctEmailAddress)
            .setPassword(correctPassword)
            .login()

        uiUtils.waitForSpecifiedWidget(By.text("Connected"))
    }
}