package ro.azs.kidsdevelopment.models

import com.google.firebase.Timestamp

data class BibleDiscovery(
    var book: BibleBookType,
    var chapter: Int,
    var date: Timestamp,
    var discovery: String) : FirestoreModel() {

    constructor() : this(BibleBookType.genesis, 0, Timestamp.now(), "")
}