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


enum class BodyRoutineValue : LocalizedString {
    yes,
    somethimes,
    no
}

data class BodyRoutine(
    var date: Timestamp = Timestamp.now(),
    var teethBrush: BodyRoutineValue = BodyRoutineValue.somethimes,
    var shower: BodyRoutineValue = BodyRoutineValue.somethimes,
    var handWash: BodyRoutineValue = BodyRoutineValue.somethimes,
    var nails: BodyRoutineValue = BodyRoutineValue.somethimes,
    var tease: BodyRoutineValue = BodyRoutineValue.somethimes,
    var dress: BodyRoutineValue = BodyRoutineValue.somethimes) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = toString()
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}

data class HealthMetric(
    var date: Timestamp = Timestamp.now(),
    var height: Double = 0.0,
    var weight: Double = 0.0) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = toString()
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}

data class Book(
    var name: String = "",
    var author: String = "")


enum class HealthDiscoveryType : LocalizedString {
    food,
    rest,
    hygiene,
    physicalActivity,
    water,
    selfControl,
    godTrust
}


data class HealthDiscovery(
    var date: Timestamp = Timestamp.now(),
    var book: Book = Book(),
    var type: HealthDiscoveryType = HealthDiscoveryType.food,
    var discovery: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = "${book.author} ${book.name}(${type.getLocalizedString()})"
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}

data class SportPractice(
    var date: Timestamp = Timestamp.now(),
    var sport: String = "",
    var location: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = sport
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}


enum class KnowledgeFindingType : LocalizedString {
    church,
    school,
    family
}

data class KnowledgeFinding
    (var date: Timestamp = Timestamp.now(),
    var discovery: String = "",
    var type: KnowledgeFindingType = KnowledgeFindingType.church) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = discovery
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}


enum class HobbyItemType : LocalizedString {
    playing,
    singing,
    drawing,
    shooting,
    reading,
    other
}


data class HobbyItem
    (var date: Timestamp = Timestamp.now(),
    var type: HobbyItemType = HobbyItemType.playing,
    var typeDetails: String = "",
    var details: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = "${type.getLocalizedString()}($typeDetails)"
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}


enum class MediaDiscoveryType : LocalizedString {
    TVshow,
    internet
}


data class MediaDiscovery
    (var date: Timestamp = Timestamp.now(),
    var type: MediaDiscoveryType = MediaDiscoveryType.TVshow,
    var discovery: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = discovery + "(" + type.getLocalizedString() + ")FF"
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}


enum class GoodDeedType : LocalizedString {
    volunteering,
    loyalty,
    kindness
}

data class GoodDeed
    (var date: Timestamp = Timestamp.now(),
    var type: GoodDeedType = GoodDeedType.volunteering,
    var details: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = details + "(" + type.getLocalizedString() + ")"
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}

data class DailySchedule
    (var date: Timestamp = Timestamp.now(),
    var god: Double = 0.0,
    var family: Double = 0.0,
    var school: Double = 0.0,
    var friends: Double = 0.0) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = "$god $family $school $friends"
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}


data class PleasantAction
    (var date: Timestamp = Timestamp.now(),
    var action: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = action
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}

enum class UsefulActionType : LocalizedString {
    family,
    colleagues,
    friends,
    church
}


data class UsefulAction
    (var date: Timestamp = Timestamp.now(),
    var type: UsefulActionType = UsefulActionType.family,
    var action: String) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = action + "(" + type.getLocalizedString() + ")"
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}


data class FamilyTask
    (var date: Timestamp = Timestamp.now(),
    var task: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = task
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}

data class SchoolTask
    (var date: Timestamp = Timestamp.now(),
    var task: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = task
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}

data class ChurchTask
    (var date: Timestamp = Timestamp.now(),
    var task: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = task
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}


enum class FinanceType : LocalizedString {
    earning,
    gifts,
    tihte,
    givings,
    spendings,
    savings
}

enum class FinanceCurrency : LocalizedString {
    RON,
    EUR,
    USD
}

data class Finance
    (var date: Timestamp = Timestamp.now(),
    var type: FinanceType = FinanceType.earning,
    var amount: Double = 0.0,
    var amountCurrency: FinanceCurrency = FinanceCurrency.RON) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = type.getLocalizedString() + "(" + amount + ")"
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}

data class Borrows
    (var date: Timestamp = Timestamp.now(),
    var reason: String = "",
    var amount: Double = 0.0,
    var amountCurrency: FinanceCurrency = FinanceCurrency.RON) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = "$reason($amount)"
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}


data class Lends
    (var date: Timestamp = Timestamp.now(),
    var reason: String = "",
    var amount: Double = 0.0,
    var amountCurrency: FinanceCurrency = FinanceCurrency.RON) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getDescriptionLabel(): String = "$reason($amount)"
    override fun getTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
}