package ro.adventist.copiiiregelui.models

data class FavoriteCategory(val type: CategoryType) : FirestoreModel() {
    constructor() : this(CategoryType.bible)
}