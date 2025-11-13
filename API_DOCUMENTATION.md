# API Kiểm Tra Ngôn Từ (Profanity Checking API)

## Tổng quan (Overview)

API kiểm tra ngôn từ không phù hợp trong văn bản tiếng Việt và tiếng Anh. API này cung cấp các phương thức để phát hiện, liệt kê và lọc bỏ các từ ngữ không phù hợp trong văn bản.

This is a Profanity Checking API for Vietnamese and English text. It provides methods to detect, list, and filter inappropriate words in text.

## Tính năng (Features)

- ✅ Kiểm tra văn bản có chứa từ ngữ không phù hợp
- ✅ Tìm tất cả các từ không phù hợp trong văn bản
- ✅ Lọc bỏ từ không phù hợp (thay thế bằng dấu *)
- ✅ Kiểm tra không phân biệt chữ hoa/thường
- ✅ Hỗ trợ cả tiếng Việt và tiếng Anh
- ✅ Trả về thông tin chi tiết về kết quả kiểm tra

## Cấu trúc dự án (Project Structure)

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/baitap1/
│   │   │   ├── ProfanityChecker.kt          # API chính
│   │   │   ├── MainActivity.kt               # Giao diện ứng dụng
│   │   │   └── ui/theme/                     # Theme files
│   │   ├── res/                              # Resources
│   │   └── AndroidManifest.xml
│   └── test/
│       └── java/com/example/baitap1/
│           └── ProfanityCheckerTest.kt       # Unit tests
└── build.gradle.kts
```

## API Reference

### Class: `ProfanityChecker`

#### Methods

##### 1. `containsProfanity(text: String): Boolean`

Kiểm tra xem văn bản có chứa từ ngữ không phù hợp hay không.

**Parameters:**
- `text`: Văn bản cần kiểm tra

**Returns:**
- `true`: Nếu văn bản chứa từ không phù hợp
- `false`: Nếu văn bản sạch

**Example:**
```kotlin
val checker = ProfanityChecker()
val hasProfanity = checker.containsProfanity("Đây là văn bản có từ ngu")
// hasProfanity = true
```

##### 2. `findProfanityWords(text: String): List<String>`

Tìm tất cả các từ không phù hợp trong văn bản.

**Parameters:**
- `text`: Văn bản cần kiểm tra

**Returns:**
- Danh sách các từ không phù hợp được tìm thấy

**Example:**
```kotlin
val checker = ProfanityChecker()
val words = checker.findProfanityWords("Văn bản có từ ngu và đần")
// words = ["ngu", "đần"]
```

##### 3. `filterProfanity(text: String): String`

Lọc bỏ các từ không phù hợp bằng cách thay thế bằng dấu *.

**Parameters:**
- `text`: Văn bản cần lọc

**Returns:**
- Văn bản đã được làm sạch (từ không phù hợp được thay bằng *)

**Example:**
```kotlin
val checker = ProfanityChecker()
val filtered = checker.filterProfanity("Văn bản có từ ngu")
// filtered = "Văn bản có từ ***"
```

##### 4. `checkText(text: String): CheckResult`

Kiểm tra chi tiết văn bản và trả về thông tin đầy đủ.

**Parameters:**
- `text`: Văn bản cần kiểm tra

**Returns:**
- `CheckResult` object chứa:
  - `originalText`: Văn bản gốc
  - `hasProfanity`: Có chứa từ không phù hợp hay không
  - `profanityWords`: Danh sách các từ không phù hợp
  - `filteredText`: Văn bản đã được lọc
  - `profanityCount`: Số lượng từ không phù hợp

**Example:**
```kotlin
val checker = ProfanityChecker()
val result = checker.checkText("Văn bản có từ ngu")
println("Original: ${result.originalText}")
println("Has profanity: ${result.hasProfanity}")
println("Count: ${result.profanityCount}")
println("Found: ${result.profanityWords}")
println("Filtered: ${result.filteredText}")
```

## Sử dụng trong Android App (Usage in Android App)

### 1. Khởi tạo (Initialization)

```kotlin
val profanityChecker = ProfanityChecker()
```

### 2. Kiểm tra văn bản (Check Text)

```kotlin
val text = "Văn bản cần kiểm tra"
val result = profanityChecker.checkText(text)

if (result.hasProfanity) {
    println("⚠️ Văn bản chứa ${result.profanityCount} từ không phù hợp")
    println("Các từ: ${result.profanityWords.joinToString(", ")}")
    println("Văn bản đã lọc: ${result.filteredText}")
} else {
    println("✅ Văn bản sạch")
}
```

### 3. Sử dụng trong Compose UI

```kotlin
@Composable
fun ProfanityCheckerScreen() {
    var inputText by remember { mutableStateOf("") }
    var checkResult by remember { mutableStateOf<ProfanityChecker.CheckResult?>(null) }
    val profanityChecker = remember { ProfanityChecker() }
    
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Nhập văn bản") }
        )
        
        Button(onClick = {
            checkResult = profanityChecker.checkText(inputText)
        }) {
            Text("Kiểm Tra")
        }
        
        checkResult?.let { result ->
            Text("Kết quả: ${if (result.hasProfanity) "❌ Có ngôn từ không phù hợp" else "✅ Sạch"}")
            Text("Số lượng: ${result.profanityCount}")
            if (result.profanityWords.isNotEmpty()) {
                Text("Các từ: ${result.profanityWords.joinToString(", ")}")
            }
            Text("Văn bản đã lọc: ${result.filteredText}")
        }
    }
}
```

## Testing

### Chạy Unit Tests

```bash
./gradlew test
```

### Test Coverage

API đã được kiểm tra với các test cases:
- ✅ Văn bản sạch (clean text)
- ✅ Văn bản có từ không phù hợp (text with profanity)
- ✅ Văn bản có nhiều từ không phù hợp (multiple profanity words)
- ✅ Kiểm tra không phân biệt chữ hoa/thường (case insensitive)
- ✅ Lọc văn bản (text filtering)
- ✅ Độ dài văn bản được giữ nguyên khi lọc

## Build Instructions

### Prerequisites

- Android Studio Arctic Fox hoặc mới hơn
- JDK 11 hoặc mới hơn
- Android SDK API 24 trở lên
- Gradle 8.9

### Build Steps

1. Clone repository:
```bash
git clone https://github.com/PhapDang78/Tuan1.git
cd Tuan1
```

2. Build project:
```bash
./gradlew build
```

3. Run tests:
```bash
./gradlew test
```

4. Run app:
```bash
./gradlew installDebug
```

## Danh sách từ không phù hợp (Profanity Word List)

API hiện tại hỗ trợ kiểm tra các từ không phù hợp phổ biến trong tiếng Việt và tiếng Anh. Danh sách này có thể được mở rộng theo nhu cầu.

**Lưu ý**: Danh sách từ được lưu trữ trong code và có thể được tùy chỉnh bằng cách kế thừa class `ProfanityChecker`.

## Customization

### Thêm từ mới vào danh sách

Bạn có thể tạo một class kế thừa để thêm từ mới:

```kotlin
class CustomProfanityChecker : ProfanityChecker() {
    private val customWords = setOf("từ1", "từ2", "từ3")
    
    override fun containsProfanity(text: String): Boolean {
        val normalizedText = text.lowercase().trim()
        return super.containsProfanity(text) || 
               customWords.any { normalizedText.contains(it, ignoreCase = true) }
    }
}
```

## Performance

- Độ phức tạp thời gian: O(n*m) với n là độ dài văn bản, m là số từ trong danh sách
- Độ phức tạp không gian: O(m) với m là số từ trong danh sách
- Sử dụng HashSet để tra cứu nhanh
- Tối ưu cho văn bản có độ dài trung bình (< 10000 ký tự)

## Security Considerations

- API không lưu trữ văn bản đầu vào
- Không gửi dữ liệu lên server
- Xử lý hoàn toàn local trên thiết bị
- Phù hợp cho các ứng dụng yêu cầu bảo mật cao

## License

Copyright © 2025 BaiTap1 Team

## Contact

- Repository: https://github.com/PhapDang78/Tuan1
- Issues: https://github.com/PhapDang78/Tuan1/issues

## Changelog

### Version 1.0.0 (2025-11-13)
- ✨ Initial release
- ✨ Vietnamese and English profanity detection
- ✨ Text filtering functionality
- ✨ Comprehensive unit tests
- ✨ Compose UI integration
- ✨ Complete API documentation
