package emoji.core.emojifetcher

import emoji.core.model.NetworkEmoji

internal interface EmojiFetchCallback {
    fun onFetchSuccess(
        emojis: List<NetworkEmoji>
    )

    fun onFetchFailure(
        errorMessage: String,
    )
}
