package ro.azs.kidsdevelopment.ui.widgets

import ro.azs.kidsdevelopment.models.CategoryType
import ro.azs.kidsdevelopment.models.ChurchTask
import ro.azs.kidsdevelopment.ui.widgets.base.BaseWidgetOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.finance.BorrowsOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.finance.FinancesOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.finance.LendsOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.intellectual.HobbiesOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.intellectual.KnowledgeFindingsOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.intellectual.MediaDiscoveriesOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.spiritual.BibleDiscoveriesOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.spiritual.BibleFavoriteTextsOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.spiritual.BiblePrayerTextsOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.physical.BodyRoutinesOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.physical.HealthDiscoveriesOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.physical.HealthMetricsOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.physical.SportPracticesOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.skills.DailySchedulesOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.skills.GoodDeedsOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.skills.PleasantActionsOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.skills.UsefulActionsOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.social.ChurchTasksOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.social.FamilyTasksOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.social.SchoolTasksOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.spiritual.PrayerPeopleOverviewFragment
import ro.azs.kidsdevelopment.ui.widgets.spiritual.PrayerSubjectsOverviewFragment


object WidgetHelper {

    fun getWidgetsForCategory(categoryType: CategoryType): List<Class<out BaseWidgetOverviewFragment<*>?>> {
        return when (categoryType) {
            CategoryType.bible -> listOf(
                BibleDiscoveriesOverviewFragment::class.java,
                BibleFavoriteTextsOverviewFragment::class.java,
                BiblePrayerTextsOverviewFragment::class.java)
            CategoryType.prayer -> listOf(
                BiblePrayerTextsOverviewFragment::class.java,
                PrayerSubjectsOverviewFragment::class.java,
                PrayerPeopleOverviewFragment::class.java)
            CategoryType.body -> listOf(
                BodyRoutinesOverviewFragment::class.java)
            CategoryType.health -> listOf(HealthMetricsOverviewFragment::class.java,
                HealthDiscoveriesOverviewFragment::class.java)
            CategoryType.sport -> listOf(
                SportPracticesOverviewFragment::class.java
            )
            CategoryType.knowledge -> listOf(
                KnowledgeFindingsOverviewFragment::class.java
            )
            CategoryType.hobby -> listOf(
                HobbiesOverviewFragment::class.java
            )
            CategoryType.media -> listOf(
                MediaDiscoveriesOverviewFragment::class.java
            )
            CategoryType.volunteering -> listOf(
                GoodDeedsOverviewFragment::class.java
            )
            CategoryType.dailySchedule -> listOf(
                DailySchedulesOverviewFragment::class.java
            )
            CategoryType.behavior -> listOf(
                PleasantActionsOverviewFragment::class.java,
                UsefulActionsOverviewFragment::class.java
            )
            CategoryType.family -> listOf(
                FamilyTasksOverviewFragment::class.java
            )
            CategoryType.school -> listOf(
                SchoolTasksOverviewFragment::class.java
            )
            CategoryType.church -> listOf(
                ChurchTasksOverviewFragment::class.java
            )
            CategoryType.personalFinances -> listOf(
                FinancesOverviewFragment::class.java
            )
            CategoryType.loans -> listOf(
                LendsOverviewFragment::class.java
            )
            CategoryType.debts -> listOf(
                BorrowsOverviewFragment::class.java
            )
        }
    }

}
