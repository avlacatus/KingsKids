package ro.azs.kidsdevelopment.models

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize
import ro.azs.kidsdevelopment.KidsDevelopmentApplication
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.utils.ConversionUtils


@Parcelize
@IgnoreExtraProperties
open class FirestoreModel : Parcelable {

    @DocumentId
    @Exclude
    var id: String? = null

    fun <T : FirestoreModel> withId(id: String?): T {
        this.id = id
        return this as T
    }
}

interface WithDescriptionTimestamp {
    fun getShortDescLabel(): String
    fun getShortTimestampLabel(): String

    fun getLongDescTitle(): String = ""
    fun getLongDescLabel(): String = getShortDescLabel()
    fun getLongTimestampLabel(): String = getShortTimestampLabel()
}

@Parcelize
data class BibleDiscovery(
    var book: BibleBookType = BibleBookType.genesis,
    var chapter: Int = 0,
    var date: Timestamp = Timestamp.now(),
    var discovery: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = ConversionUtils.getBibleReferenceDescription(book, chapter)
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = ConversionUtils.getBibleReferenceDescription(book, chapter)
    override fun getLongDescLabel(): String = discovery
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

@Parcelize
data class BibleFavoriteText(
    var book: BibleBookType = BibleBookType.genesis,
    var chapter: Int = 0,
    var date: Timestamp = Timestamp.now(),
    var verse: Int = 0) : FirestoreModel(), WithDescriptionTimestamp {

    override fun getShortDescLabel(): String = ConversionUtils.getBibleReferenceDescription(book, chapter, verse)
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = ConversionUtils.getBibleReferenceDescription(book, chapter, verse)
    override fun getLongDescLabel(): String = ""
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

@Parcelize
data class BiblePrayerText(
    var book: BibleBookType = BibleBookType.genesis,
    var chapter: Int = 0,
    var date: Timestamp = Timestamp.now(),
    var discovery: String = "",
    var prayerType: PrayerType = PrayerType.praise,
    var verse: Int = 0) : FirestoreModel(), WithDescriptionTimestamp {

    override fun getShortDescLabel(): String = ConversionUtils.getBiblePrayerTextDescription(this)
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = prayerType.getLocalizedString()
    override fun getLongDescLabel(): String = "${
        ConversionUtils.getBibleReferenceDescription(book, chapter, verse)
    }\n$discovery"

    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

@Parcelize
data class PrayerSubject(
    var date: Timestamp = Timestamp.now(),
    var subject: String = "",
    var description: String = "",
    var answer: PrayerSubjectAnswer? = null) : FirestoreModel(), WithDescriptionTimestamp {

    override fun getShortDescLabel(): String = subject
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = subject
    override fun getLongDescLabel(): String = description
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)

}

@Parcelize
data class PrayerSubjectAnswer(
    var date: Timestamp = Timestamp.now(),
    var description: String = "") : FirestoreModel() {

}

@Parcelize
data class PrayerPeople(
    var date: Timestamp = Timestamp.now(),
    var person: String = "",
    var reason: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = person
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
    override fun getLongDescLabel(): String = "${getShortDescLabel()}\n$reason"
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

enum class BodyRoutineValue : LocalizedString {
    yes,
    somethimes,
    no
}

@Parcelize
data class BodyRoutine(
    var date: Timestamp = Timestamp.now(),
    var teethBrush: BodyRoutineValue = BodyRoutineValue.somethimes,
    var shower: BodyRoutineValue = BodyRoutineValue.somethimes,
    var handWash: BodyRoutineValue = BodyRoutineValue.somethimes,
    var nails: BodyRoutineValue = BodyRoutineValue.somethimes,
    var tease: BodyRoutineValue = BodyRoutineValue.somethimes,
    var dress: BodyRoutineValue = BodyRoutineValue.somethimes) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = "Rutină zilnică"
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
    override fun getLongDescLabel(): String = KidsDevelopmentApplication.appContext.resources.let {
        it.getString(R.string.teethBrushLabel) + ": " + teethBrush.getLocalizedString() + "\n" +
                it.getString(R.string.showerLabel) + ": " + shower.getLocalizedString() + "\n" +
                it.getString(R.string.handWashLabel) + ": " + handWash.getLocalizedString() + "\n" +
                it.getString(R.string.nailsLabel) + ": " + nails.getLocalizedString() + "\n" +
                it.getString(R.string.teaseLabel) + ": " + tease.getLocalizedString() + "\n" +
                it.getString(R.string.dressedLabel) + ": " + dress.getLocalizedString()
    }

    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

@Parcelize
data class HealthMetric(
    var date: Timestamp = Timestamp.now(),
    var height: Double = 0.0,
    var weight: Double = 0.0) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = "Valoare de sănătate"
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
    override fun getLongDescLabel(): String = KidsDevelopmentApplication.appContext.resources.let {
        it.getString(R.string.dateLabel) + ": " + ConversionUtils.getTimestampShortValue(date) + "\n" +
                it.getString(R.string.heightLabel) + ": " + height + "\n" +
                it.getString(R.string.weightLabel) + ": " + weight
    }

    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

@Parcelize
data class Book(
    var name: String = "",
    var author: String = "") : Parcelable

enum class HealthDiscoveryType : LocalizedString {
    food,
    rest,
    hygiene,
    physicalActivity,
    water,
    selfControl,
    godTrust
}

@Parcelize
data class HealthDiscovery(
    var date: Timestamp = Timestamp.now(),
    var book: Book = Book(),
    var type: HealthDiscoveryType = HealthDiscoveryType.food,
    var discovery: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = "${book.author} ${book.name}(${type.getLocalizedString()})"
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = "${book.author}-${book.name}(${type.getLocalizedString()})"
    override fun getLongDescLabel(): String = discovery
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

@Parcelize
data class SportPractice(
    var date: Timestamp = Timestamp.now(),
    var sport: String = "",
    var location: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = sport
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = sport
    override fun getLongDescLabel(): String = location
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}


enum class KnowledgeFindingType : LocalizedString {
    church,
    school,
    family
}

@Parcelize
data class KnowledgeFinding
    (var date: Timestamp = Timestamp.now(),
    var discovery: String = "",
    var type: KnowledgeFindingType = KnowledgeFindingType.church) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = discovery
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = discovery
    override fun getLongDescLabel(): String = type.getLocalizedString()
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

enum class HobbyItemType : LocalizedString {
    playing,
    singing,
    drawing,
    shooting,
    reading,
    other
}

@Parcelize
data class HobbyItem
    (var date: Timestamp = Timestamp.now(),
    var type: HobbyItemType = HobbyItemType.playing,
    var typeDetails: String = "",
    var details: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = "${type.getLocalizedString()} ($typeDetails)"
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = getShortDescLabel()
    override fun getLongDescLabel(): String = details
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

enum class MediaDiscoveryType : LocalizedString {
    TVshow,
    internet
}

@Parcelize
data class MediaDiscovery
    (var date: Timestamp = Timestamp.now(),
    var type: MediaDiscoveryType = MediaDiscoveryType.TVshow,
    var discovery: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = type.getLocalizedString()
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = getShortDescLabel()
    override fun getLongDescLabel(): String = discovery
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}


enum class GoodDeedType : LocalizedString {
    volunteering,
    loyalty,
    kindness
}

@Parcelize
data class GoodDeed
    (var date: Timestamp = Timestamp.now(),
    var type: GoodDeedType = GoodDeedType.volunteering,
    var details: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = type.getLocalizedString()
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = getShortDescLabel()
    override fun getLongDescLabel(): String = details
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

@Parcelize
data class DailySchedule
    (var date: Timestamp = Timestamp.now(),
    var god: Double = 0.0,
    var family: Double = 0.0,
    var school: Double = 0.0,
    var friends: Double = 0.0) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = "$god $family $school $friends"
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)
    override fun getLongDescLabel(): String = getShortDescLabel()
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

@Parcelize
data class PleasantAction
    (var date: Timestamp = Timestamp.now(),
    var action: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = action
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = getShortDescLabel()
    override fun getLongDescLabel(): String = ""
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

enum class UsefulActionType : LocalizedString {
    family,
    colleagues,
    friends,
    church
}

@Parcelize
data class UsefulAction
    (var date: Timestamp = Timestamp.now(),
    var type: UsefulActionType = UsefulActionType.family,
    var action: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = type.getLocalizedString()
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = getShortDescLabel()
    override fun getLongDescLabel(): String = action
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

@Parcelize
data class FamilyTask
    (var date: Timestamp = Timestamp.now(),
    var task: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = task
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = getShortDescLabel()
    override fun getLongDescLabel(): String = ""
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

@Parcelize
data class SchoolTask
    (var date: Timestamp = Timestamp.now(),
    var task: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = task
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = getShortDescLabel()
    override fun getLongDescLabel(): String = ""
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

@Parcelize
data class ChurchTask
    (var date: Timestamp = Timestamp.now(),
    var task: String = "") : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = task
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = getShortDescLabel()
    override fun getLongDescLabel(): String = ""
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
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

@Parcelize
data class Finance
    (var date: Timestamp = Timestamp.now(),
    var type: FinanceType = FinanceType.earning,
    var amount: Double = 0.0,
    var amountCurrency: FinanceCurrency = FinanceCurrency.RON) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = type.getLocalizedString()
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = getShortDescLabel()
    override fun getLongDescLabel(): String = "$amount ${amountCurrency.getLocalizedString()}"
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

@Parcelize
data class Borrows
    (var date: Timestamp = Timestamp.now(),
    var reason: String = "",
    var amount: Double = 0.0,
    var amountCurrency: FinanceCurrency = FinanceCurrency.RON) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = "$amount ${amountCurrency.getLocalizedString()}"
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = getShortDescLabel()
    override fun getLongDescLabel(): String = reason
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}

@Parcelize
data class Lends
    (var date: Timestamp = Timestamp.now(),
    var reason: String = "",
    var amount: Double = 0.0,
    var amountCurrency: FinanceCurrency = FinanceCurrency.RON) : FirestoreModel(), WithDescriptionTimestamp {
    override fun getShortDescLabel(): String = "$amount ${amountCurrency.getLocalizedString()}"
    override fun getShortTimestampLabel(): String = ConversionUtils.getTimestampShortValue(date)

    override fun getLongDescTitle(): String = getShortDescLabel()
    override fun getLongDescLabel(): String = reason
    override fun getLongTimestampLabel(): String = ConversionUtils.getTimestampLongValue(date)
}