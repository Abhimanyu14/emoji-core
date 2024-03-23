package emoji.core.emojifetcher

import emoji.core.model.NetworkEmoji
import java.io.IOException

internal interface EmojiFetchCallback {
    fun onFetchSuccess(
        emojis: List<NetworkEmoji>
    )

    fun onFetchFailure(
        ioException: IOException,
    )
}
