package com.example.baitap1

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for ProfanityChecker API
 */
class ProfanityCheckerTest {
    
    private lateinit var profanityChecker: ProfanityChecker
    
    @Before
    fun setUp() {
        profanityChecker = ProfanityChecker()
    }
    
    @Test
    fun `containsProfanity should return true for text with profanity`() {
        val text = "Đây là một đoạn văn bản có từ ngu"
        assertTrue(profanityChecker.containsProfanity(text))
    }
    
    @Test
    fun `containsProfanity should return false for clean text`() {
        val text = "Đây là một đoạn văn bản sạch"
        assertFalse(profanityChecker.containsProfanity(text))
    }
    
    @Test
    fun `findProfanityWords should find all profanity words`() {
        val text = "Văn bản có từ ngu và đần"
        val foundWords = profanityChecker.findProfanityWords(text)
        assertEquals(2, foundWords.size)
        assertTrue(foundWords.contains("ngu"))
        assertTrue(foundWords.contains("đần"))
    }
    
    @Test
    fun `filterProfanity should replace profanity with asterisks`() {
        val text = "Văn bản có từ ngu"
        val filtered = profanityChecker.filterProfanity(text)
        assertTrue(filtered.contains("***"))
        assertFalse(filtered.contains("ngu"))
    }
    
    @Test
    fun `checkText should return complete result`() {
        val text = "Văn bản có từ ngu"
        val result = profanityChecker.checkText(text)
        
        assertTrue(result.hasProfanity)
        assertEquals(1, result.profanityCount)
        assertEquals(text, result.originalText)
        assertFalse(result.filteredText.contains("ngu"))
        assertTrue(result.profanityWords.contains("ngu"))
    }
    
    @Test
    fun `checkText should handle clean text correctly`() {
        val text = "Văn bản hoàn toàn sạch"
        val result = profanityChecker.checkText(text)
        
        assertFalse(result.hasProfanity)
        assertEquals(0, result.profanityCount)
        assertEquals(text, result.originalText)
        assertEquals(text, result.filteredText)
        assertTrue(result.profanityWords.isEmpty())
    }
    
    @Test
    fun `containsProfanity should be case insensitive`() {
        val textLower = "có từ ngu trong đây"
        val textUpper = "có từ NGU trong đây"
        val textMixed = "có từ Ngu trong đây"
        
        assertTrue(profanityChecker.containsProfanity(textLower))
        assertTrue(profanityChecker.containsProfanity(textUpper))
        assertTrue(profanityChecker.containsProfanity(textMixed))
    }
    
    @Test
    fun `filterProfanity should preserve text length`() {
        val text = "ngu"
        val filtered = profanityChecker.filterProfanity(text)
        assertEquals(text.length, filtered.length)
    }
}
