package ro.azs.kidsdevelopment.models

import ro.azs.kidsdevelopment.R

enum class CategoryType(val categoryTypeId: String, val labelRes: Int, val iconRes: Int, val headerRes: Int, val messageRes: Int) {
    bible("bible", R.string.bible, R.drawable.category_bible, R.string.bibleHeader, R.string.bibleMessage),
    prayer("prayer", R.string.prayer, R.drawable.category_prayer, R.string.prayerHeader, R.string.prayerMessage),
    body("body", R.string.body, R.drawable.category_body, R.string.bodyHeader, R.string.bodyMessage),
    health("health", R.string.health, R.drawable.category_health, R.string.healthHeader, R.string.healthMessage),
    sport("sport", R.string.sport, R.drawable.category_sport, R.string.sportHeader, R.string.sportMessage),
    knowledge("knowledge", R.string.knowledge, R.drawable.category_knowledge, R.string.knowledgeHeader, R.string.knowledgeMessage),
    hobby("hobby", R.string.hobby, R.drawable.category_hobby, R.string.hobbyHeader, R.string.hobbyMessage),
    media("media", R.string.media, R.drawable.category_media, R.string.mediaHeader, R.string.mediaMessage),
    volunteering("volunteering", R.string.volunteering, R.drawable.category_volunteering, R.string.volunteeringHeader, R.string.volunteeringMessage),
    dailySchedule("dailySchedule", R.string.dailySchedule, R.drawable.category_daily_schedule, R.string.dailyScheduleHeader, R.string.dailyScheduleMessage),
    behavior("behavior", R.string.behavior, R.drawable.category_behavior, R.string.behaviorHeader, R.string.behaviorMessage),
    family("family", R.string.family, R.drawable.category_family, R.string.familyHeader, R.string.familyMessage),
    school("school", R.string.school, R.drawable.category_school, R.string.schoolHeader, R.string.schoolMessage),
    church("church", R.string.church, R.drawable.category_church, R.string.churchHeader, R.string.churchMessage),
    personalFinances("personalFinances", R.string.personalFinances, R.drawable.category_personal_finances, R.string.personalFinancesHeader, R.string.personalFinancesMessage),
    loans("loans", R.string.loans, R.drawable.category_loans, R.string.loansHeader, R.string.loansMessage),
    debts("debts", R.string.debts, R.drawable.category_debts, R.string.debtsHeader, R.string.debtsMessage);

    companion object {
        fun getCategoryById(categoryTypeId: String): CategoryType {
            return values().firstOrNull { categoryType -> categoryType.categoryTypeId == categoryTypeId } ?: debts
        }
    }
}
