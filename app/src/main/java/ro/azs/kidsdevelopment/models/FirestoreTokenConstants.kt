package ro.azs.kidsdevelopment.models

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


enum class CategorySectionType : FirestoreCollection {
    bibleDiscoveries,
    bibleFavoriteTexts,
    biblePrayerTexts,
    prayerSubjects,
    prayerPeople,
    bodyRoutines,
    healthMetrics,
    healthDiscoveries,
    sportPractice,
    knowledgeFindings,
    hobbies,
    mediaDiscoveries,
    goodDeeds,
    dailySchedule,
    pleasantActions,
    usefulActions,
    familyTasks,
    schoolTasks,
    churchTasks,
    finances,
    borrows,
    lends
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

