package emoji.core

import emoji.core.datasource.EmojiDataSource
import emoji.core.datasource.EmojiDataSourceImpl
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val emojiDataSource: EmojiDataSource = EmojiDataSourceImpl()
    val emojis = emojiDataSource.getAllEmojis()
    println(emojis)
}
