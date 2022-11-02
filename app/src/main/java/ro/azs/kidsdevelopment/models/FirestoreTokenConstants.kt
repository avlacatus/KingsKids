package ro.azs.kidsdevelopment.models

import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.utils.GenericUtils

interface Labeled {
    val name: String
}

interface FirestoreCollection : Labeled {
}

interface LocalizedString : Labeled {

    fun getLocalizedString(): String {
        return GenericUtils.getStringByResourceIdentifier(name, name)
    }
}


enum class CategorySectionType(
    val widgetTitleRes: Int,
    val widgetHintRes: Int = -1,
    val widgetEmptyRes: Int = -1,
    val widgetAddLabelRes: Int=-1,
    val modelClass: Class<out FirestoreModel> = BibleDiscovery::class.java) : FirestoreCollection {
    bibleDiscoveries(R.string.bibleDiscoveriesTitle, R.string.bibleDiscoveriesHint, R.string.noDiscoveriesMessage, R.string.addNewDiscovery, BibleDiscovery::class.java),
    bibleFavoriteTexts( R.string.bibleFavoriteTextsTitle, R.string.bibleFavoriteTextsHint, R.string.noFavoriteTextsMessage, R.string.addNewFavoriteText, BibleFavoriteText::class.java),
    biblePrayerTexts( R.string.biblePrayerTextsTitle, R.string.biblePrayerTextsHint, R.string.noPrayerTextsMessage, R.string.addNewPrayerText, BiblePrayerText::class.java),
    prayerSubjects(R.string.prayerSubjectsTitle, R.string.prayerSubjectsHint, R.string.noPrayerSubjectsMessage, R.string.addNewPrayerSubject, PrayerSubject::class.java),
    prayerPeople(R.string.prayerPeopleTitle, R.string.prayerPeopleHint, R.string.noPrayerPeopleMessage, R.string.addPrayerPeople, PrayerPeople::class.java),
    bodyRoutines(R.string.bodyRoutinesTitle),
    healthMetrics(R.string.healthMetricsTitle),
    healthDiscoveries(R.string.healthDiscoveriesTitle),
    sportPractice(R.string.sportPracticeTitle),
    knowledgeFindings(R.string.knowledgeFindingsTitle),
    hobbies(R.string.hobbiesTitle),
    mediaDiscoveries(R.string.mediaDiscoveriesTitle),
    goodDeeds(R.string.goodDeedsTitle),
    dailySchedule(R.string.dailyScheduleTitle),
    pleasantActions(R.string.pleasantActionsTitle),
    usefulActions(R.string.usefulActionsTitle),
    familyTasks(R.string.familyTasksTitle),
    schoolTasks(R.string.schoolTasksTitle),
    churchTasks(R.string.churchTasksTitle),
    finances(R.string.financesTitle),
    borrows(R.string.borrowsTitle),
    lends(R.string.lendsTitle);
}

enum class PrayerType : LocalizedString {
    worship,
    confession,
    gratefulness,
    request,
    intercession,
    praise,
    gratitude
}

enum class FirestoreConstants : FirestoreCollection {
    categories,
    categoriesGroups,
    users,
    bibleBooks,
    favoriteCategories,
    history,
    reports,
    findings
}

enum class FirestoreFields : Labeled {
    order,
    date
}

