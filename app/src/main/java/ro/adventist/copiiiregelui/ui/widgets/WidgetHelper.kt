package ro.adventist.copiiiregelui.ui.widgets

import ro.adventist.copiiiregelui.models.CategoryType
import ro.adventist.copiiiregelui.ui.widgets.base.BaseWidgetOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.finance.BorrowsOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.finance.FinancesOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.finance.LendsOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.intellectual.HobbiesOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.intellectual.KnowledgeFindingsOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.intellectual.MediaDiscoveriesOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.spiritual.BibleDiscoveriesOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.spiritual.BibleFavoriteTextsOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.spiritual.BiblePrayerTextsOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.physical.BodyRoutinesOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.physical.HealthDiscoveriesOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.physical.HealthMetricsOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.physical.SportPracticesOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.skills.DailySchedulesOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.skills.GoodDeedsOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.skills.PleasantActionsOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.skills.UsefulActionsOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.social.ChurchTasksOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.social.FamilyTasksOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.social.SchoolTasksOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.spiritual.PrayerPeopleOverviewFragment
import ro.adventist.copiiiregelui.ui.widgets.spiritual.PrayerSubjectsOverviewFragment


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
