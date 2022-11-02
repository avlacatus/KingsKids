package ro.azs.kidsdevelopment.ui.widgets

import ro.azs.kidsdevelopment.models.CategoryType
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.bible.BibleDiscoveriesOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.bibleFavoriteTexts.BibleFavoriteTextsOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.biblePrayerTexts.BiblePrayerTextsOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.prayer.PrayerPeopleOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.prayer.PrayerSubjectsOverviewFragment


object WidgetHelper {

    fun getWidgetsForCategory(categoryType: CategoryType): List<Class<out BaseWidgetOverviewFragment<*>?>> {
        return when (categoryType) {
            CategoryType.bible -> return listOf(
                BibleDiscoveriesOverviewFragment::class.java,
                BibleFavoriteTextsOverviewFragment::class.java,
                BiblePrayerTextsOverviewFragment::class.java)
            CategoryType.prayer -> return listOf(
                PrayerSubjectsOverviewFragment::class.java,
                PrayerPeopleOverviewFragment::class.java
            )
            else -> emptyList()
        }
    }

}
