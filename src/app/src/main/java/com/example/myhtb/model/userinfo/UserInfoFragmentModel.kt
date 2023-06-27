package com.example.myhtb.model.userinfo

import com.example.myhtb.Utils
import com.example.myhtb.logger.Logger
import com.example.myhtb.model.base.repository.HtbRepository
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.HttpException

/**
 * UserInfoFragmentModel
 *
 * Modelクラス。
 * 他Modelクラスからのアクセスも考慮してシングルトンこう制とする
 */
object UserInfoFragmentModel {
    /**
     * タグ名
     */
    private var TAG = this::class.java.simpleName

    /**
     * HackTheBox APIを用いて基本的なユーザー情報を取得する。
     *
     * @return ユーザー情報
     *
     * @see
     * 取得可能な情報は、
     * HtbRepository.ktソース内のGetBasicUserInfoメソッドのコメント文を読むこと
     *
     */
    suspend fun GetBasicUserInfo(): ResponseBody?{
        Logger.LogDebug(TAG, "Start GetBasicUserInfo")
        val authToken = HtbRepository.AuthToken
        return try{
            val responseBody = HtbRepository.GetBasicUserInfo(authToken)

            if(responseBody != null && !responseBody.isSuccessful){
                Logger.LogError(TAG, "responseBody is null or responseBody.isSuccessful is false")
                null
            }
            Logger.LogDebug(TAG, "Succeed to get basic user info")
            responseBody!!.body()
        }
        catch (e: Exception){
            Utils.PrintLogErrorInfo(TAG, e, "Failed to fetch get user info")
            null
        }
        finally {
            Logger.LogDebug(TAG, "Finish GetBasicUserInfo")
        }
    }
}