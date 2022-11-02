package ro.azs.kidsdevelopment.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import ro.azs.kidsdevelopment.utils.ConversionUtils

@IgnoreExtraProperties
open class FirestoreModel {
    @Exclude
    @DocumentId
    var id: String? = null

    fun <T : FirestoreModel> withId(id: String?): T {
        this.id = id
        return this as T
    }
}

interface WithDescriptionTimestamp {
    fun getDescriptionLabel(): String
    fun getTimestampLabel(): String
}



data class BibleDiscovery(
    var book: BibleBookType = BibleBookType.genesis,
    var chapter: Int = 0,
    var date: Timestamp = Timestamp.now(),
    var discovery: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = ConversionUtils.getBibleReferenceDescription(book, chapter, 0)
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}

data class BibleFavoriteText(
    var book: BibleBookType = BibleBookType.genesis,
    var chapter: Int = 0,
    var date: Timestamp = Timestamp.now(),
    var verse: Int = 0) : FirestoreModel(), WithDescriptionTimestamp {

    override fun getDescriptionLabel(): String = ConversionUtils.getBibleReferenceDescription(book, chapter, verse)
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}

data class BiblePrayerText(
    var book: BibleBookType = BibleBookType.genesis,
    var chapter: Int = 0,
    var date: Timestamp = Timestamp.now(),
    var discovery: String = "",
    var prayerType: PrayerType = PrayerType.praise,
    var verse: Int = 0) : FirestoreModel(), WithDescriptionTimestamp {

    override fun getDescriptionLabel(): String = ConversionUtils.getBiblePrayerTextDescription(this)
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}


data class PrayerSubject(
    var date: Timestamp = Timestamp.now(),
    var subject: String = "",
    var description: String = "",
    var answer: PrayerSubjectAnswer? = null) : FirestoreModel(), WithDescriptionTimestamp {

    override fun getDescriptionLabel(): String = subject
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

}

data class PrayerSubjectAnswer(
    var date: Timestamp = Timestamp.now(),
    var description: String = "") : FirestoreModel() {

}

data class PrayerPeople(
    var date: Timestamp = Timestamp.now(),
    var person: String = "",
    var reason: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = person
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}