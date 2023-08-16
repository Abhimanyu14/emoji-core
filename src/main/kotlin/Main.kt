import datasource.EmojiDataSource
import datasource.EmojiDataSourceImpl
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val emojiDataSource: EmojiDataSource = EmojiDataSourceImpl()

    val emojis = emojiDataSource.getAllEmojis()
    println(emojis)
}
