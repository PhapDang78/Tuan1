# Ví dụ Sử Dụng API (Usage Examples)

## Mục lục (Table of Contents)

1. [Ví dụ Cơ Bản (Basic Examples)](#basic-examples)
2. [Ví dụ Nâng Cao (Advanced Examples)](#advanced-examples)
3. [Tích Hợp vào Android App (Android Integration)](#android-integration)
4. [Xử Lý Lỗi (Error Handling)](#error-handling)
5. [Best Practices](#best-practices)

---

## Basic Examples

### 1. Kiểm tra đơn giản (Simple Check)

```kotlin
val checker = ProfanityChecker()

val text1 = "Đây là văn bản sạch"
println(checker.containsProfanity(text1))  // false

val text2 = "Đây là văn bản có từ ngu"
println(checker.containsProfanity(text2))  // true
```

### 2. Tìm các từ không phù hợp (Find Profanity Words)

```kotlin
val checker = ProfanityChecker()
val text = "Văn bản có từ ngu và đần"

val foundWords = checker.findProfanityWords(text)
println("Tìm thấy: ${foundWords.joinToString(", ")}")
// Output: Tìm thấy: ngu, đần
```

### 3. Lọc văn bản (Filter Text)

```kotlin
val checker = ProfanityChecker()
val text = "Đây là từ ngu"

val filtered = checker.filterProfanity(text)
println("Gốc: $text")
println("Đã lọc: $filtered")
// Output:
// Gốc: Đây là từ ngu
// Đã lọc: Đây là từ ***
```

### 4. Kiểm tra chi tiết (Detailed Check)

```kotlin
val checker = ProfanityChecker()
val text = "Văn bản có từ ngu và đần"

val result = checker.checkText(text)
println("Văn bản gốc: ${result.originalText}")
println("Có ngôn từ không phù hợp: ${result.hasProfanity}")
println("Số lượng: ${result.profanityCount}")
println("Các từ: ${result.profanityWords}")
println("Đã lọc: ${result.filteredText}")

// Output:
// Văn bản gốc: Văn bản có từ ngu và đần
// Có ngôn từ không phù hợp: true
// Số lượng: 2
// Các từ: [ngu, đần]
// Đã lọc: Văn bản có từ *** và ***
```

---

## Advanced Examples

### 1. Xử lý Danh sách Văn bản (Process List of Texts)

```kotlin
val checker = ProfanityChecker()
val texts = listOf(
    "Văn bản 1 sạch",
    "Văn bản 2 có từ ngu",
    "Văn bản 3 sạch",
    "Văn bản 4 có từ đần"
)

val results = texts.map { text ->
    text to checker.checkText(text)
}

results.forEach { (text, result) ->
    println("$text -> ${if (result.hasProfanity) "❌" else "✅"}")
}

// Output:
// Văn bản 1 sạch -> ✅
// Văn bản 2 có từ ngu -> ❌
// Văn bản 3 sạch -> ✅
// Văn bản 4 có từ đần -> ❌
```

### 2. Thống kê (Statistics)

```kotlin
val checker = ProfanityChecker()
val texts = listOf(
    "Text 1",
    "Text 2 có từ ngu",
    "Text 3 có từ đần và ngu",
    "Text 4"
)

val stats = texts.map { checker.checkText(it) }

val totalTexts = stats.size
val dirtyTexts = stats.count { it.hasProfanity }
val cleanTexts = totalTexts - dirtyTexts
val totalProfanityWords = stats.sumOf { it.profanityCount }

println("Tổng số văn bản: $totalTexts")
println("Văn bản sạch: $cleanTexts")
println("Văn bản có vấn đề: $dirtyTexts")
println("Tổng số từ không phù hợp: $totalProfanityWords")

// Output:
// Tổng số văn bản: 4
// Văn bản sạch: 2
// Văn bản có vấn đề: 2
// Tổng số từ không phù hợp: 3
```

### 3. Lọc và Xuất ra File

```kotlin
val checker = ProfanityChecker()
val inputTexts = listOf(
    "Bình luận 1: Rất hay!",
    "Bình luận 2: Dở quá, ngu thật",
    "Bình luận 3: Tuyệt vời!"
)

val filteredTexts = inputTexts.map { text ->
    val result = checker.checkText(text)
    if (result.hasProfanity) {
        "[Đã lọc] ${result.filteredText}"
    } else {
        text
    }
}

filteredTexts.forEach { println(it) }

// Output:
// Bình luận 1: Rất hay!
// [Đã lọc] Bình luận 2: Dở quá, *** thật
// Bình luận 3: Tuyệt vời!
```

---

## Android Integration

### 1. Sử dụng trong ViewModel

```kotlin
class CommentViewModel : ViewModel() {
    private val profanityChecker = ProfanityChecker()
    
    private val _commentState = MutableStateFlow<CommentState>(CommentState.Initial)
    val commentState: StateFlow<CommentState> = _commentState
    
    fun validateComment(text: String) {
        val result = profanityChecker.checkText(text)
        
        _commentState.value = if (result.hasProfanity) {
            CommentState.Invalid(
                message = "Bình luận chứa ${result.profanityCount} từ không phù hợp",
                profanityWords = result.profanityWords
            )
        } else {
            CommentState.Valid(text)
        }
    }
}

sealed class CommentState {
    object Initial : CommentState()
    data class Valid(val text: String) : CommentState()
    data class Invalid(
        val message: String,
        val profanityWords: List<String>
    ) : CommentState()
}
```

### 2. Sử dụng trong Compose UI

```kotlin
@Composable
fun CommentInputScreen() {
    var text by remember { mutableStateOf("") }
    val checker = remember { ProfanityChecker() }
    var checkResult by remember { mutableStateOf<ProfanityChecker.CheckResult?>(null) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { 
                text = it
                checkResult = checker.checkText(it)
            },
            label = { Text("Nhập bình luận") },
            modifier = Modifier.fillMaxWidth(),
            isError = checkResult?.hasProfanity == true
        )
        
        checkResult?.let { result ->
            if (result.hasProfanity) {
                Text(
                    text = "⚠️ Chứa ${result.profanityCount} từ không phù hợp",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                Text(
                    text = "✅ Bình luận hợp lệ",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        
        Button(
            onClick = { /* Submit comment */ },
            enabled = checkResult?.hasProfanity != true,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Gửi bình luận")
        }
    }
}
```

### 3. Real-time Validation

```kotlin
@Composable
fun RealTimeValidationField() {
    var text by remember { mutableStateOf("") }
    val checker = remember { ProfanityChecker() }
    
    // Debounce the validation
    val validationResult by produceState<ProfanityChecker.CheckResult?>(
        initialValue = null,
        key1 = text
    ) {
        delay(300) // Wait 300ms after user stops typing
        value = if (text.isNotEmpty()) checker.checkText(text) else null
    }
    
    Column {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Nhập văn bản") },
            supportingText = {
                validationResult?.let { result ->
                    if (result.hasProfanity) {
                        Text(
                            "Tìm thấy: ${result.profanityWords.joinToString(", ")}",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        )
    }
}
```

---

## Error Handling

### 1. Xử lý Chuỗi Rỗng (Empty String Handling)

```kotlin
val checker = ProfanityChecker()

fun safeCheck(text: String?): Boolean {
    return text?.let { checker.containsProfanity(it) } ?: false
}

// Usage
println(safeCheck(null))  // false
println(safeCheck(""))    // false
println(safeCheck("   ")) // false
println(safeCheck("ngu")) // true
```

### 2. Xử lý Exception

```kotlin
val checker = ProfanityChecker()

fun checkWithErrorHandling(text: String): Result<ProfanityChecker.CheckResult> {
    return try {
        Result.success(checker.checkText(text))
    } catch (e: Exception) {
        Result.failure(e)
    }
}

// Usage
val result = checkWithErrorHandling("some text")
result.onSuccess { checkResult ->
    println("Check thành công: ${checkResult.hasProfanity}")
}.onFailure { error ->
    println("Lỗi: ${error.message}")
}
```

---

## Best Practices

### 1. Tái sử dụng Instance (Reuse Instance)

❌ **Không nên:**
```kotlin
fun checkText(text: String): Boolean {
    val checker = ProfanityChecker()  // Tạo mới mỗi lần
    return checker.containsProfanity(text)
}
```

✅ **Nên:**
```kotlin
class TextValidator {
    private val checker = ProfanityChecker()  // Tái sử dụng
    
    fun checkText(text: String): Boolean {
        return checker.containsProfanity(text)
    }
}
```

### 2. Validate trước khi lưu (Validate Before Saving)

```kotlin
fun saveComment(comment: String, checker: ProfanityChecker): Result<String> {
    val result = checker.checkText(comment)
    
    return if (result.hasProfanity) {
        Result.failure(
            IllegalArgumentException(
                "Comment chứa từ không phù hợp: ${result.profanityWords}"
            )
        )
    } else {
        // Save to database
        Result.success(comment)
    }
}
```

### 3. Logging để Debug

```kotlin
val checker = ProfanityChecker()

fun checkWithLogging(text: String): ProfanityChecker.CheckResult {
    val result = checker.checkText(text)
    
    if (result.hasProfanity) {
        Log.w("ProfanityCheck", """
            Profanity detected:
            - Text: ${result.originalText}
            - Words: ${result.profanityWords}
            - Count: ${result.profanityCount}
        """.trimIndent())
    }
    
    return result
}
```

### 4. Caching Results

```kotlin
class CachedProfanityChecker {
    private val checker = ProfanityChecker()
    private val cache = mutableMapOf<String, ProfanityChecker.CheckResult>()
    
    fun checkText(text: String): ProfanityChecker.CheckResult {
        return cache.getOrPut(text) {
            checker.checkText(text)
        }
    }
    
    fun clearCache() {
        cache.clear()
    }
}
```

### 5. Batch Processing

```kotlin
fun batchCheck(
    texts: List<String>,
    checker: ProfanityChecker
): Map<String, ProfanityChecker.CheckResult> {
    return texts.associateWith { text ->
        checker.checkText(text)
    }
}

// Usage
val texts = listOf("text1", "text2", "text3")
val results = batchCheck(texts, ProfanityChecker())

results.forEach { (text, result) ->
    println("$text: ${result.hasProfanity}")
}
```

---

## Performance Tips

### 1. Pre-filter Empty or Short Texts

```kotlin
fun smartCheck(text: String, checker: ProfanityChecker): Boolean {
    // Skip checking very short texts
    if (text.length < 3) return false
    
    return checker.containsProfanity(text)
}
```

### 2. Use Coroutines for Large Datasets

```kotlin
suspend fun checkManyTexts(
    texts: List<String>,
    checker: ProfanityChecker
): List<ProfanityChecker.CheckResult> = withContext(Dispatchers.Default) {
    texts.map { text ->
        async {
            checker.checkText(text)
        }
    }.awaitAll()
}
```

---

## Kết luận (Conclusion)

API ProfanityChecker rất linh hoạt và dễ sử dụng. Các ví dụ trên minh họa nhiều cách sử dụng khác nhau từ cơ bản đến nâng cao. Hãy chọn phương pháp phù hợp với nhu cầu của bạn!

The ProfanityChecker API is flexible and easy to use. The examples above demonstrate various usage patterns from basic to advanced. Choose the approach that fits your needs!
