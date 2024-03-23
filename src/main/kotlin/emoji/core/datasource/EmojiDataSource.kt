package emoji.core.datasource

import emoji.core.model.NetworkEmoji

public interface EmojiDataSource {
    public suspend fun getAllEmojis(): List<NetworkEmoji>
}
