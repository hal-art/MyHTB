package com.halsec.myhtb

import com.halsec.myhtb.logger.Logger
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import okio.IOException
import retrofit2.HttpException
import java.lang.Exception

data class RankData(
    val currentRankPoints: String,
    val currentRank: String,
    val nextRank: String,
)

object Utils {
    /**
     * タグ名
     */
    private var TAG = this::class.java.simpleName

    /**
     * ベースURL
     */
    const val BASE_URL = "https://www.hackthebox.com/"

    /**
     * ResponseBodyクラスの情報(responseBody)から指定したエレメント(element)情報を取り出して返却する
     * @param responseBody ResponseBody情報
     * @param parentKeys keyに辿り着くまでに探索する必要のある親キーリスト
     * @param key 取り出したいキー名
     *
     * @return 取り出し結果
     * 取り出し失敗またはエレメント名が無かった場合はnullを返却する
     */
    fun extractSpecifiedValueFromResponseBody(responseBody: ResponseBody, parentKeys: List<String>, key: String) : String? {
        Logger.LogDebug(TAG, "Start extractSpecifiedValueFromResponseBody")
        val responseBodyStr = responseBody.string()
        var jsonObject = JsonParser().parse(responseBodyStr).asJsonObject
        for (parentKey in parentKeys) {
            jsonObject = jsonObject.get(parentKey)?.asJsonObject ?: return null
        }
        Logger.LogDebug(TAG, "Finish extractSpecifiedValueFromResponseBody")
        return jsonObject.get(key)?.asString
    }

    /**
     * Jsonデータを文字列化した情報(responseBodyString)から指定したエレメント(element)情報を取り出して返却する
     * @param responseBodyString Jsonデータを文字列化した情報
     * @param parentKeys keyに辿り着くまでに探索する必要のある親キーリスト
     * @param key 取り出したいキー名
     *
     * @return 取り出し結果
     * 取り出し失敗またはエレメント名が無かった場合はnullを返却する
     */
    fun extractSpecifiedValueFromResponseBodyString(responseBodyString: String, parentKeys: List<String>?, key: String) : String? {
        Logger.LogDebug(TAG, "Start extractSpecifiedValueFromResponseBodyString")
        var jsonObject = JsonParser().parse(responseBodyString).asJsonObject
        if(parentKeys != null){
            for (parentKey in parentKeys) {
                jsonObject = jsonObject.get(parentKey)?.asJsonObject ?: return null
            }
        }
        Logger.LogDebug(TAG, "Finish extractSpecifiedValueFromResponseBodyString")
        return jsonObject.get(key)?.asString
    }

    /**
     * ログエラー処理の共通化
     *
     * @param TAG タグ(各クラスが持つTAG変数を渡せばよい)
     * @param e 例外の種類
     * @param message 表示するエラーメッセージ
     */
    fun PrintLogErrorInfo(TAG: String, e: Exception, message: String) {
        val errorType = when (e) {
            is HttpException -> "HTTP error"
            is IOException -> "Network/timeout error"
            else -> "Unknown error"
        }
        Logger.LogError(TAG, "$message due to $errorType: ${e.message ?: "No error message available"}")
    }
}