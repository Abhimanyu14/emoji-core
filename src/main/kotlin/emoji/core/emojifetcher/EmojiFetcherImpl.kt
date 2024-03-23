package emoji.core.emojifetcher

import emoji.core.model.NetworkEmoji
import okhttp3.Cache
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.IOException

internal class EmojiFetcherImpl(
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

        val okHttpClientBuilder = OkHttpClient()
            .newBuilder()
            .cache(
                cache = cache,
            )

        val okHttpClient = okHttpClientBuilder.build()
        val request = Request.Builder()
            .url(
                url = url,
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
                            errorMessage = e.message ?: "An error occurred",
                        )
                    }

                    override fun onResponse(
                        call: Call,
                        response: Response,
                    ) {
                        val emojis: List<NetworkEmoji> = parseEmojiData(
                            data = response.body?.string().orEmpty(),
                        )
                        callback.onFetchSuccess(
                            emojis = emojis,
                        )
                    }
                },
            )
    }
}

private fun parseEmojiData(
    data: String,
    isSkinTonesSupported: Boolean = false,
): MutableList<NetworkEmoji> {
    val emojis = mutableListOf<NetworkEmoji>()
    val lines = data.trim().split("\n")
    var group = ""
    var subgroup = ""
    val skinTonesCodePoints = hashSetOf(
        "1F3FB", "1F3FC", "1F3FD", "1F3FE", "1F3FF",
    )

    for (line in lines) {
        if (line.isNotBlank()) {
            // Save group and subgroup info from comments and ignore other comments
            if (line[0] == '#') {
                if (line.contains("# subgroup: ")) {
                    subgroup = line.replace("# subgroup: ", "")
                } else if (line.contains("# group: ")) {
                    group = line.replace("# group: ", "")
                }
            } else {
                val fields = line.trim().split(";").map { it.trim() }
                val subfields = fields[1].trim().split("#").map { it.trim() }
                val status = subfields[0]

                if (status == "fully-qualified") {
                    val characterAndName = subfields[1].trim().split(" ").map { it.trim() }

                    val codePointSplit = fields[0].split(" ")
                    val codePoint = codePointSplit.joinToString(" ")

                    val emojiHasSkinTone = codePointSplit.any {
                        skinTonesCodePoints.contains(it)
                    }
                    if (!isSkinTonesSupported && !emojiHasSkinTone) {
                        val character = characterAndName[0]

                        val unicodeNameBuilder = StringBuilder()
                        for (i in 2..characterAndName.lastIndex) {
                            unicodeNameBuilder.append("${characterAndName[i]} ")
                        }
                        val unicodeName = unicodeNameBuilder.toString().trim()

                        val emoji = NetworkEmoji(
                            character = character,
                            codePoint = codePoint,
                            group = group,
                            subgroup = subgroup,
                            unicodeName = unicodeName,
                        )
                        emojis.add(emoji)
                    }
                }
            }
        }
    }
    return emojis
}
