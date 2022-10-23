package ro.azs.kidsdevelopment.models

data class FavoriteCategory(val type: CategoryType) : FirestoreModel() {
    constructor() : this(CategoryType.bible)
}