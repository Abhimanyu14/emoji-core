package emoji.core.emojifetcher

private object EmojiFetcherConstants {
    const val UNICODE_EMOJIS_URL = "https://makeappssimple.com/hosting/emoji_core/emoji.txt"
}

internal interface EmojiFetcher {
    fun fetchEmojiData(
        callback: EmojiFetchCallback,
        url: String = EmojiFetcherConstants.UNICODE_EMOJIS_URL,
    )
}
