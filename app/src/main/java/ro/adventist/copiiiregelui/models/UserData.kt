package ro.adventist.copiiiregelui.models

class UserData(
    val profile: UserProfile,
    val favouriteCategories: List<FavoriteCategory>,
    val historyItems: List<HistoryItem>/*,
    val bibleDiscoveries: List<BibleDiscovery>,
    val bibleFavoriteTexts: List<BibleFavoriteText>,
    val biblePrayerTexts: List<BiblePrayerText>*/)