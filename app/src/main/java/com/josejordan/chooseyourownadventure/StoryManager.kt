package com.josejordan.chooseyourownadventure


class StoryManager(private val pages: List<Page>) {
    private val pagesMap: Map<Int, Page> = pages.associateBy { it.id }
    var currentPage: Page = pagesMap[0] ?: throw IllegalArgumentException("Story must start with page id 0")

    var isValidStory: Boolean = true
        private set

    init {
        isValidStory = validateStory()
    }

    private fun validateStory(): Boolean {
        if (pages.isEmpty()) {
            System.err.println("The story cannot be empty")
            return false
        }

        // Check that all choices point to existing pages
        pages.forEach { page ->
            page.choices.forEach { choice ->
                if (choice.nextPageId != 0 && !pagesMap.containsKey(choice.nextPageId)) {
                    System.err.println("Page id ${choice.nextPageId} referenced by page id ${page.id} does not exist")
                    return false
                }
            }
        }
        return true
    }

    fun goToPage(pageId: Int): Boolean {
        val nextPage = pagesMap[pageId]
        return if (nextPage != null) {
            currentPage = nextPage
            true
        } else {
            System.err.println("Attempted to go to a non-existent page id: $pageId")
            false
        }
    }
}


