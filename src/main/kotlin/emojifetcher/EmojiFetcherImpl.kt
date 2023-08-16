package emojifetcher

import okhttp3.Cache
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException

class EmojiFetcherImpl(
    private val cacheFile: File? = null,
) : EmojiFetcher {
    override fun fetchEmojiData(
        callback: EmojiFetchCallback,
        url: String,
    ) {
        val cache = cacheFile?.run {
            Cache(
                directory = cacheFile,
                maxSize = (5 * 1024 * 1024).toLong(),
            )
        }

        val okHttpClientBuilder = OkHttpClient().newBuilder().cache(
            cache = cache,
        )

        val okHttpClient = okHttpClientBuilder.build()
        val request = Request.Builder()
            .url(
                url = url,
            )
            .build()

        okHttpClient.newCall(
            request = request,
        ).enqueue(
            responseCallback = object : Callback {
                override fun onFailure(
                    call: Call,
                    e: IOException,
                ) {
                    callback.onFetchFailure(
                        errorMessage = e.message ?: "An error occurred",
                    )
                }

                override fun onResponse(
                    call: Call,
                    response: Response,
                ) {
                    callback.onFetchSuccess(
                        data = response.body?.string().orEmpty(),
                    )
                }
            },
        )
    }
}
