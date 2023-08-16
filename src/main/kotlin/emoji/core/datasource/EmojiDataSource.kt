package emoji.core.datasource

import emoji.core.model.NetworkEmoji

interface EmojiDataSource {
    suspend fun getAllEmojis(): List<NetworkEmoji>
}
