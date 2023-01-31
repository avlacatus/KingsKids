package ro.adventist.copiiiregelui.models

import ro.adventist.copiiiregelui.R

enum class CategoryGroupType(val categoryGroupTypeId: String, val labelRes: Int) {
    spiritual("spiritual", R.string.spiritual),
    physical("physical", R.string.physical),
    intellectual("intellectual", R.string.intellectual),
    skills("skills", R.string.skills),
    social("social", R.string.social),
    finance("finance", R.string.finance);

    companion object {
        fun getCategoryGroupById(categoryGroupTypeId: String): CategoryGroupType {
            return values()
                .firstOrNull { categoryGroupType -> categoryGroupType.categoryGroupTypeId == categoryGroupTypeId }
                ?: finance
        }
    }
}