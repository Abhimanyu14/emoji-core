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

class EmojiDataSourceImpl(
    private val cacheFile: File? = null,
    private val emojiFetcher: EmojiFetcher = EmojiFetcherImpl(
        cacheFile = cacheFile,
    ),
) : EmojiDataSource {
    override suspend fun getAllEmojis(): List<NetworkEmoji> {
        return suspendCoroutine { continuation ->
            emojiFetcher.fetchEmojiData(
                callback = object : EmojiFetchCallback {
                    override fun onFetchSuccess(
                        data: String,
                    ) {
                        val emojis = parseEmojiData(
                            data = data,
                        )
                        continuation.resume(
                            value = emojis,
                        )
                    }

                    override fun onFetchFailure(
                        errorMessage: String,
                    ) {
                        continuation.resumeWithException(
                            exception = IOException("Fetch failed: $errorMessage"),
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
}
