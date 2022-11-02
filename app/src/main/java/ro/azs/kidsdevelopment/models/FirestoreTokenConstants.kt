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
    val widgetAddLabelRes: Int = -1,
    val modelClass: Class<out FirestoreModel> = BibleDiscovery::class.java) : FirestoreCollection {
    bibleDiscoveries(R.string.bibleDiscoveriesTitle, R.string.bibleDiscoveriesHint, R.string.noDiscoveriesMessage, R.string.addNewDiscovery, BibleDiscovery::class.java),
    bibleFavoriteTexts(R.string.bibleFavoriteTextsTitle, R.string.bibleFavoriteTextsHint, R.string.noFavoriteTextsMessage, R.string.addNewFavoriteText, BibleFavoriteText::class.java),
    biblePrayerTexts(R.string.biblePrayerTextsTitle, R.string.biblePrayerTextsHint, R.string.noPrayerTextsMessage, R.string.addNewPrayerText, BiblePrayerText::class.java),
    prayerSubjects(R.string.prayerSubjectsTitle, R.string.prayerSubjectsHint, R.string.noPrayerSubjectsMessage, R.string.addNewPrayerSubject, PrayerSubject::class.java),
    prayerPeople(R.string.prayerPeopleTitle, R.string.prayerPeopleHint, R.string.noPrayerPeopleMessage, R.string.addPrayerPeople, PrayerPeople::class.java),
    bodyRoutines(R.string.bodyRoutinesTitle, R.string.bodyRoutinesHint, R.string.noBodyRoutinesMessage, R.string.addNewBodyRoutine, BodyRoutine::class.java),
    healthMetrics(R.string.healthMetricsTitle, R.string.healthMetricsHint, R.string.noHealthMetricMessage, R.string.addNewHealthMetrics, HealthMetric::class.java),
    healthDiscoveries(R.string.healthDiscoveriesTitle, R.string.healthDiscoveriesHint, R.string.noHealthDiscoveriesMessage, R.string.addNewHealthDiscovery, HealthDiscovery::class.java),
    sportPractice(R.string.sportPracticeTitle, R.string.sportPracticeHint, R.string.noSportPracticeMessage, R.string.addNewSportPractice, SportPractice::class.java),
    knowledgeFindings(R.string.knowledgeFindingsTitle, R.string.knowledgeFindingsHint, R.string.noFindingMessage, R.string.addNewFinding, KnowledgeFinding::class.java),
    hobbies(R.string.hobbiesTitle, R.string.hobbiesHint, R.string.noHobbiesMessage, R.string.addNewHobby, HobbyItem::class.java),
    mediaDiscoveries(R.string.mediaDiscoveriesTitle, R.string.mediaDiscoveriesHint, R.string.noMediaDiscoveriesMessage, R.string.addNewMediaDiscovery, MediaDiscovery::class.java),
    goodDeeds(R.string.goodDeedsTitle, R.string.goodDeedsHint, R.string.noGoodDeedsMessage, R.string.addNewGoodDeed, GoodDeed::class.java),
    dailySchedule(R.string.dailyScheduleTitle, R.string.dailyScheduleHint, R.string.noDailyScheduleMessage, R.string.addNewDailySchedule, DailySchedule::class.java),
    pleasantActions(R.string.pleasantActionsTitle, R.string.pleasantActionsHint, R.string.noPleasantActionMessage, R.string.addNewPleasantAction, PleasantAction::class.java),
    usefulActions(R.string.usefulActionsTitle, R.string.usefulActionsHint, R.string.noUsefulActionMessage, R.string.addNewUsefulAction, UsefulAction::class.java),
    familyTasks(R.string.familyTasksTitle, R.string.familyTasksHint, R.string.noTasksMessage, R.string.addNewTask, FamilyTask::class.java),
    schoolTasks(R.string.schoolTasksTitle, R.string.schoolTasksHint, R.string.noTasksMessage, R.string.addNewTask, SchoolTask::class.java),
    churchTasks(R.string.churchTasksTitle, R.string.churchTasksHint, R.string.noTasksMessage, R.string.addNewTask, ChurchTask::class.java),
    finances(R.string.financesTitle, R.string.financesHint, R.string.noFinancesMessage, R.string.addNewFinance, Finance::class.java),
    borrows(R.string.borrowsTitle, R.string.borrowsHint, R.string.noBorrowsMessage, R.string.addNewBorrow, Borrows::class.java),
    lends(R.string.lendsTitle, R.string.lendsHint, R.string.noLendsMessage, R.string.addNewLend, Lends::class.java);
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

