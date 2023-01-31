package ro.adventist.copiiiregelui.models

data class CategoryGroup(val type: CategoryGroupType, val order: Long, val categories: List<Category>)
