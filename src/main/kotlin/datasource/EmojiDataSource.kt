package datasource

import model.NetworkEmoji

interface EmojiDataSource {
    suspend fun getAllEmojis(): List<NetworkEmoji>
}
