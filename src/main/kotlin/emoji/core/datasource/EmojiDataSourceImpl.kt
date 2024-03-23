package emoji.core.datasource

import emoji.core.emojifetcher.EmojiFetchCallback
import emoji.core.emojifetcher.EmojiFetcher
import emoji.core.emojifetcher.EmojiFetcherImpl
import emoji.core.model.NetworkEmoji
import java.io.File
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class EmojiDataSourceImpl : EmojiDataSource {
    override suspend fun getAllEmojis(
        cacheFile: File?,
    ): List<NetworkEmoji> {
        val emojiFetcher: EmojiFetcher = EmojiFetcherImpl(
            cacheFile = cacheFile,
        )
        return suspendCoroutine { continuation ->
            emojiFetcher.fetchEmojiData(
                callback = object : EmojiFetchCallback {
                    override fun onFetchSuccess(
                        emojis: List<NetworkEmoji>,
                    ) {
                        continuation.resume(
                            value = emojis,
                        )
                    }

                    override fun onFetchFailure(
                        ioException: IOException,
                    ) {
                        continuation.resumeWithException(
                            exception = IOException("Fetch failed: ${ioException.message ?: "An error occurred"}"),
                        )
                    }
                },
            )
        }
    }
}
