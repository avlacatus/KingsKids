package ro.azs.kidsdevelopment.models

import com.google.firebase.Timestamp

data class BiblePrayerText(
    var book: BibleBookType,
    var chapter: Int,
    var date: Timestamp,
    var discovery: String,
    var prayerType: PrayerType,
    var verse: Int) : FirestoreModel() {
    constructor() : this(BibleBookType.genesis, 0, Timestamp.now(), "", PrayerType.confession, 0)
}
