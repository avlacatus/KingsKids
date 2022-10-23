package ro.azs.kidsdevelopment.models

import ro.azs.kidsdevelopment.R

enum class CategoryType(val categoryTypeId: String, val labelRes: Int, val iconRes: Int, val headerRes: Int, val messageRes: Int) {
    bible("bible", R.string.bible, R.drawable.bible_new, R.string.bibleHeader, R.string.bibleMessage),
    prayer("prayer", R.string.prayer, R.drawable.prayer, R.string.prayerHeader, R.string.prayerMessage),
    body("body", R.string.body, R.drawable.body, R.string.bodyHeader, R.string.bodyMessage),
    health("health", R.string.health, R.drawable.health, R.string.healthHeader, R.string.healthMessage),
    sport("sport", R.string.sport, R.drawable.sport, R.string.sportHeader, R.string.sportMessage),
    knowledge("knowledge", R.string.knowledge, R.drawable.knowledge, R.string.knowledgeHeader, R.string.knowledgeMessage),
    hobby("hobby", R.string.hobby, R.drawable.hobby, R.string.hobbyHeader, R.string.hobbyMessage),
    media("media", R.string.media, R.drawable.media, R.string.mediaHeader, R.string.mediaMessage),
    volunteering("volunteering", R.string.volunteering, R.drawable.volunteering, R.string.volunteeringHeader, R.string.volunteeringMessage),
    dailySchedule("dailySchedule", R.string.dailySchedule, R.drawable.daily_schedule, R.string.dailyScheduleHeader, R.string.dailyScheduleMessage),
    behavior("behavior", R.string.behavior, R.drawable.behavior, R.string.behaviorHeader, R.string.behaviorMessage),
    family("family", R.string.family, R.drawable.family, R.string.familyHeader, R.string.familyMessage),
    school("school", R.string.school, R.drawable.school, R.string.schoolHeader, R.string.schoolMessage),
    church("church", R.string.church, R.drawable.church, R.string.churchHeader, R.string.churchMessage),
    personalFinances("personalFinances",
        R.string.personalFinances,
        R.drawable.personal_finances,
        R.string.personalFinancesHeader,
        R.string.personalFinancesMessage),
    loans("loans", R.string.loans, R.drawable.loans, R.string.loansHeader, R.string.loansMessage),
    debts("debts", R.string.debts, R.drawable.debts, R.string.debtsHeader, R.string.debtsMessage);

    companion object {
        fun getCategoryById(categoryTypeId: String): CategoryType {
            return values().firstOrNull { categoryType -> categoryType.categoryTypeId == categoryTypeId } ?: debts
        }
    }
}
