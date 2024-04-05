package emoji.core.emojifetcher

private object EmojiFetcherConstants {
    const val UNICODE_EMOJIS_URL = "https://unicode.org/Public/emoji/15.1/emoji-test.txt"
}

internal interface EmojiFetcher {
    fun fetchEmojiData(
        callback: EmojiFetchCallback,
        url: String = EmojiFetcherConstants.UNICODE_EMOJIS_URL,
    )
}
