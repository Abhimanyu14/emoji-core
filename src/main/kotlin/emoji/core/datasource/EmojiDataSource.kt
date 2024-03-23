package emoji.core.datasource

import emoji.core.model.NetworkEmoji
import java.io.File

public interface EmojiDataSource {
    public suspend fun getAllEmojis(
        cacheFile: File? = null,
    ): List<NetworkEmoji>
}
