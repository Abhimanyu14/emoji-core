package emoji.core.emojiparser

import emoji.core.model.NetworkEmoji

internal interface EmojiParser {
    fun parseEmojiData(
        data: String,
        isSkinTonesSupported: Boolean = false,
    ): List<NetworkEmoji>
}
