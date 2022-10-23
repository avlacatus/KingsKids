package ro.azs.kidsdevelopment.models

import com.google.firebase.Timestamp

data class BibleFavoriteText(
    var book: BibleBookType,
    var chapter: Int,
    var date: Timestamp,
    var verse: Int) : FirestoreModel() {
    constructor() : this(BibleBookType.genesis, 0, Timestamp.now(), 0)
}
