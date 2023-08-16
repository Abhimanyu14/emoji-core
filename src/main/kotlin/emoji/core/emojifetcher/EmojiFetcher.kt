package emoji.core.emojifetcher

private const val UNICODE_EMOJIS_URL = "https://unicode.org/Public/emoji/15.0/emoji-test.txt"

interface EmojiFetcher {
    fun fetchEmojiData(
        callback: EmojiFetchCallback,
        url: String = UNICODE_EMOJIS_URL,
    )
}
