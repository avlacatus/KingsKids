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
    healthDiscoveries;
}

enum class FirestoreConstants : FirestoreCollection {
    users,
    favoriteCategories;
}

enum class PrayerType : LocalizedString {
    worship, confession, gratefulness, request, intercession, praise, gratitude
}