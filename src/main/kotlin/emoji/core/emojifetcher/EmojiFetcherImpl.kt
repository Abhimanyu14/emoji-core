package emoji.core.emojifetcher

import emoji.core.emojiparser.EmojiParser
import emoji.core.emojiparser.EmojiParserImpl
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

private object EmojiFetcherImplConstants {
    const val FIVE_MB_IN_BYTES = 5 * 1024 * 1024.toLong()
    const val UNICODE_EMOJIS_URL = "https://unicode.org/Public/emoji/15.0/emoji-test.txt"
}

internal class EmojiFetcherImpl(
    private val cacheFile: File? = null,
    private val emojiParser: EmojiParser = EmojiParserImpl(),
) : EmojiFetcher {
    override fun fetchEmojiData(
        callback: EmojiFetchCallback,
    ) {
        val okHttpClientBuilder = OkHttpClient()
            .newBuilder()
            .cache(
                cache = cacheFile?.run {
                    Cache(
                        directory = cacheFile,
                        maxSize = EmojiFetcherImplConstants.FIVE_MB_IN_BYTES,
                    )
                },
            )

        val okHttpClient = okHttpClientBuilder.build()
        val request = Request.Builder()
            .cacheControl(
                cacheControl = CacheControl.Builder()
                    .minFresh(
                        minFresh = 3,
                        timeUnit = TimeUnit.DAYS,
                    )
                    .maxStale(
                        maxStale = 30,
                        timeUnit = TimeUnit.DAYS,
                    )
                    .build(),
            )
            .url(
                url = EmojiFetcherImplConstants.UNICODE_EMOJIS_URL,
            )
            .build()

        okHttpClient
            .newCall(
                request = request,
            ).enqueue(
                responseCallback = object : Callback {
                    override fun onFailure(
                        call: Call,
                        e: IOException,
                    ) {
                        callback.onFetchFailure(
                            ioException = e,
                        )
                    }

                    override fun onResponse(
                        call: Call,
                        response: Response,
                    ) {
                        callback.onFetchSuccess(
                            emojis = emojiParser.parseEmojiData(
                                data = response.body?.string().orEmpty(),
                            ),
                        )
                    }
                },
            )
    }
}
