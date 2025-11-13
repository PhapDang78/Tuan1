# Lập Trình Di Động - Bài Tập 1

## API Kiểm Tra Ngôn Từ (Profanity Checking API)

Ứng dụng Android với API kiểm tra và lọc ngôn từ không phù hợp trong văn bản tiếng Việt và tiếng Anh.

### ✨ Tính năng chính

- ✅ Kiểm tra văn bản có chứa từ ngữ không phù hợp
- ✅ Tìm và liệt kê tất cả các từ không phù hợp
- ✅ Lọc bỏ từ không phù hợp (thay thế bằng dấu *)
- ✅ Kiểm tra không phân biệt chữ hoa/thường
- ✅ Giao diện Material Design 3 hiện đại
- ✅ Hỗ trợ tiếng Việt và tiếng Anh

### 📱 Screenshots

Ứng dụng có giao diện thân thiện với người dùng, hiển thị kết quả kiểm tra theo màu sắc:
- 🟢 Xanh: Văn bản sạch
- 🔴 Đỏ: Văn bản có từ không phù hợp

### 📖 Documentation

Xem [API_DOCUMENTATION.md](API_DOCUMENTATION.md) để biết chi tiết về cách sử dụng API.

### 🚀 Quick Start

```kotlin
val checker = ProfanityChecker()
val result = checker.checkText("Văn bản cần kiểm tra")

if (result.hasProfanity) {
    println("Có ${result.profanityCount} từ không phù hợp")
    println("Văn bản đã lọc: ${result.filteredText}")
}
```

### 🧪 Testing

API đã được kiểm tra kỹ lưỡng với unit tests đầy đủ:
- Kiểm tra văn bản sạch
- Kiểm tra văn bản có từ không phù hợp
- Kiểm tra nhiều từ không phù hợp
- Kiểm tra không phân biệt chữ hoa/thường
- Kiểm tra lọc văn bản

### 📚 Về Bài Tập

#### 1. Mong muốn và định hướng sau khi học xong môn lập trình di động
- Sau khi hoàn thành môn học, em mong muốn có thể thành thạo việc xây dựng ứng dụng di động, từ việc thiết kế giao diện đến xử lý logic và tối ưu hiệu suất.
- Em định hướng phát triển chuyên sâu hơn về lập trình di động, có thể chọn một nền tảng chính như Android (Kotlin) hoặc iOS (Swift), hoặc theo hướng đa nền tảng (Flutter, React Native).
- Mục tiêu là có thể phát triển một ứng dụng hoàn chỉnh, tham gia các dự án thực tế hoặc làm freelancer để tích lũy kinh nghiệm.

#### 2. Lập trình di động trong 10 năm tới có phát triển không?
- Theo em lập trình di động chắc chắn vẫn sẽ tiếp tục phát triển mạnh mẽ. Vì: 
  + Xu hướng sử dụng thiết bị di động tăng cao: Điện thoại thông minh ngày càng phổ biến và là thiết bị không thể thiếu.
  + Sự phát triển của công nghệ: Các công nghệ mới như AI, AR/VR, 5G sẽ thúc đẩy nhu cầu về các ứng dụng di động thông minh hơn.
  + Sự chuyển dịch của các dịch vụ: Ngày càng nhiều doanh nghiệp số hóa dịch vụ của họ thông qua ứng dụng di động thay vì chỉ sử dụng website.
  + Phát triển đa nền tảng: Các công cụ như Flutter, React Native giúp lập trình viên phát triển nhanh hơn với chi phí thấp hơn.

#### 3. Link figma design
https://www.figma.com/design/PQPWReBsHSlwBWm1XLy8Wa/Bai-Tap-1?node-id=0-1&t=TLEdPVzwC8RnfJax-1

### 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Material Design**: Material 3
- **Testing**: JUnit 4
- **Build Tool**: Gradle 8.9
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)

### 📦 Project Structure

```
Tuan1/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/baitap1/
│   │   │   │   ├── ProfanityChecker.kt
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── ui/theme/
│   │   │   └── res/
│   │   └── test/
│   │       └── java/com/example/baitap1/
│   │           └── ProfanityCheckerTest.kt
│   └── build.gradle.kts
├── gradle/
│   └── libs.versions.toml
├── API_DOCUMENTATION.md
└── README.md
```

### 👨‍💻 Author

PhapDang78

### 📄 License

Copyright © 2025 BaiTap1 Team
