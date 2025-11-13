package com.example.baitap1

/**
 * ProfanityChecker - API kiểm tra ngôn từ không phù hợp (Profanity Checker API)
 * 
 * Lớp này cung cấp các phương thức để kiểm tra và lọc các từ ngữ không phù hợp
 * trong văn bản tiếng Việt và tiếng Anh.
 */
class ProfanityChecker {
    
    // Danh sách các từ ngôn từ không phù hợp (Vietnamese and English profanity)
    private val profanityWords = setOf(
        // Vietnamese profanity words
        "đồ", "ngu", "ngốc", "khốn", "đần", "điên", "chết", "ma", "quỷ",
        "đĩ", "đéo", "đụ", "cặc", "lồn", "buồi", "vãi", "shit", "fuck",
        
        // English profanity words (common ones)
        "damn", "hell", "bastard", "idiot", "stupid", "moron"
    )
    
    /**
     * Kiểm tra xem văn bản có chứa từ ngữ không phù hợp hay không
     * 
     * @param text Văn bản cần kiểm tra
     * @return true nếu văn bản chứa từ không phù hợp, false nếu sạch
     */
    fun containsProfanity(text: String): Boolean {
        val normalizedText = text.lowercase().trim()
        return profanityWords.any { word ->
            normalizedText.contains(word, ignoreCase = true)
        }
    }
    
    /**
     * Tìm tất cả các từ không phù hợp trong văn bản
     * 
     * @param text Văn bản cần kiểm tra
     * @return Danh sách các từ không phù hợp được tìm thấy
     */
    fun findProfanityWords(text: String): List<String> {
        val normalizedText = text.lowercase().trim()
        return profanityWords.filter { word ->
            normalizedText.contains(word, ignoreCase = true)
        }
    }
    
    /**
     * Lọc bỏ các từ không phù hợp bằng cách thay thế bằng dấu *
     * 
     * @param text Văn bản cần lọc
     * @return Văn bản đã được làm sạch
     */
    fun filterProfanity(text: String): String {
        var filteredText = text
        profanityWords.forEach { word ->
            val regex = Regex(word, RegexOption.IGNORE_CASE)
            filteredText = regex.replace(filteredText) { matchResult ->
                "*".repeat(matchResult.value.length)
            }
        }
        return filteredText
    }
    
    /**
     * Kiểm tra chi tiết văn bản và trả về thông tin đầy đủ
     * 
     * @param text Văn bản cần kiểm tra
     * @return CheckResult chứa thông tin chi tiết về kết quả kiểm tra
     */
    fun checkText(text: String): CheckResult {
        val hasProfanity = containsProfanity(text)
        val foundWords = findProfanityWords(text)
        val filteredText = filterProfanity(text)
        
        return CheckResult(
            originalText = text,
            hasProfanity = hasProfanity,
            profanityWords = foundWords,
            filteredText = filteredText,
            profanityCount = foundWords.size
        )
    }
    
    /**
     * Data class chứa kết quả kiểm tra
     */
    data class CheckResult(
        val originalText: String,
        val hasProfanity: Boolean,
        val profanityWords: List<String>,
        val filteredText: String,
        val profanityCount: Int
    )
}
