package ro.azs.kidsdevelopment.models

class UserData(val profile: UserProfile,
    val favouriteCategories: List<FavoriteCategory>,
    val bibleDiscoveries: List<BibleDiscovery>,
    val bibleFavoriteTexts: List<BibleFavoriteText>,
    val biblePrayerTexts: List<BiblePrayerText>)