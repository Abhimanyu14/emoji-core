# Emoji Core

<a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>

Emoji core is a pure Kotlin library that fetches all emojis supported in the [latest Unicode standard 15.0](https://unicode.org/versions/Unicode15.0.0/) (Updated as on 13 September 2022).

# Setup

1. Add it in your root build.gradle at the end of repositories:
```kotlin
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

2. Add dependency to required module
```kotlin
dependencies {
    implementation 'com.github.Abhimanyu14:emoji-core:$latest_version'
}
```

Latest version can be found here </br>
[![](https://jitpack.io/v/Abhimanyu14/emoji-core.svg)](https://jitpack.io/#Abhimanyu14/emoji-core)

# Sample

## Kotlin
The [Main application](/src/main/kotlin/emoji/core/Main.kt)  demonstrates how to use Emoji core to fetch emojis, using coroutines. </br>
For demonstration purposes `runBlocking` is used. For actual projects call `emojiDataSource.getAllEmojis()` in a non-blocking coroutine.

## Sample Android App
[Compose Emoji Picker](https://github.com/Abhimanyu14/compose-emoji-picker) internally uses Emoji Core for fetching the emojis.

# Dependencies

* kotlinx-coroutines-core
* kotlinx-serialization-json
* okhttp3

## Find this repository useful? ♥️

Support it by joining __[stargazers](https://github.com/Abhimanyu14/emoji-core/stargazers)__ for this repository. 🌟  
Also __[follow](https://github.com/Abhimanyu14)__ me for my next creations! 🤗

# License

```
Copyright 2023 Abhimanyu

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
