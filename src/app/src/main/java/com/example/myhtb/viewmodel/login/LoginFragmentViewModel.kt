package com.halsec.myhtb.viewmodel.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halsec.myhtb.logger.Logger
import com.halsec.myhtb.model.login.LoginFragmentModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * LoginFragmentのViewModel
 */
class LoginFragmentViewModel : ViewModel(){
    /**
     * タグ名
     */
    private var TAG = this::class.java.simpleName

    /**
     * ログイン用メールアドレス
     */
    val loginEmail = MutableLiveData<String>()

    /**
     * ログイン用パスワード
     */
    val loginPassword = MutableLiveData<String>()

    /**
     * プログレスインディケーターのVisibility
     */
    val displayProgressIndicator = MutableLiveData(false)

    /**
     * 接続状態(未接続：No Connection / 接続 : Connected)
     */
    val connectionStatus = MutableLiveData("No Connection")

    /**
     * ログイン画面ViewModel用のScope
     */
    private val scope = CoroutineScope(Dispatchers.Main)

    /**
     * ログイン実行処理
     */
    fun Login() {
        Logger.LogDebug(TAG, "Start Login")

        val email:String = loginEmail.value ?: return
        val password:String = loginPassword.value ?: return

        if(email.isBlank() || password.isBlank()){
            Logger.LogError(TAG, "Email or password is blank")
            Logger.LogDebug(TAG, "Finish Login")
            return
        }

        updateIndicatorVisible(true)
        scope.launch {
            val result = LoginFragmentModel.LoginToHackTheBox(email, password)
            updateIndicatorVisible(false)
            updateConnectionStatus(result)

            if(result){
                Logger.LogDebug(TAG, "Succeed to login to HackTheBox")
                Logger.LogDebug(TAG, "Succeed to get access token")
            }
            else{
                Logger.LogError(TAG, "Failed to login to HackTheBox")
                Logger.LogError(TAG, "Failed to get access token")
            }
            Logger.LogDebug(TAG, "Finish Login")
        }
    }

    /**
     * インジケーター表示状態更新処理
     *
     * @param showIndicator インジケーターを表示するかどうか
     */
    private fun updateIndicatorVisible(showIndicator: Boolean){
        Logger.LogDebug(TAG, "Start updateIndicatorVisible")
        displayProgressIndicator.value = showIndicator
        Logger.LogDebug(TAG, "Finish updateIndicatorVisible")
    }

    /**
     * 接続状態文字列の更新処理
     *
     * @param isLoginSuccess ログインに成功したかどうか
     */
    private fun updateConnectionStatus(isLoginSuccess: Boolean){
        Logger.LogDebug(TAG, "Start updateConnectionStatus")

        if (isLoginSuccess){
            connectionStatus.value = "Connected"
        }else{
            connectionStatus.value = "No Connection"
        }

        Logger.LogDebug(TAG, "Finish updateConnectionStatus")
    }
}