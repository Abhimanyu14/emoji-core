package emoji.core.emojifetcher

internal interface EmojiFetcher {
    fun fetchEmojiData(
        callback: EmojiFetchCallback,
    )
}
