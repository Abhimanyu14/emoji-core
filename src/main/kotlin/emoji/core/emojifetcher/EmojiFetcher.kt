package emoji.core.emojifetcher

private object EmojiFetcherConstants {
    const val UNICODE_EMOJIS_URL = "https://abhimanyu14.github.io/hosting/emoji_core/emoji.txt"
}

internal interface EmojiFetcher {
    fun fetchEmojiData(
        callback: EmojiFetchCallback,
        url: String = EmojiFetcherConstants.UNICODE_EMOJIS_URL,
    )
}
