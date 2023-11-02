package com.josejordan.chooseyourownadventure

// StoryManager.kt
class StoryManager(pages: List<Page>) {
    private val pagesMap = pages.associateBy { it.id }
    var currentPage: Page = pagesMap[0] ?: throw IllegalArgumentException("Story must start with page id 0")

    fun goToPage(pageId: Int) {
        currentPage = pagesMap[pageId] ?: throw IllegalArgumentException("No page with id $pageId")
    }
}
