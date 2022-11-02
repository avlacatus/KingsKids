package ro.azs.kidsdevelopment.utils

import com.google.firebase.Timestamp
import ro.azs.kidsdevelopment.models.BibleBookType
import ro.azs.kidsdevelopment.models.BiblePrayerText
import java.text.SimpleDateFormat
import java.util.*

object ConversionUtils {

    const val PATTERN_D_M_Y = "d MMM. yyyy"
    private val GENERAL_DATE_FORMAT = SimpleDateFormat(PATTERN_D_M_Y, Locale.ENGLISH)

    fun getTimestampShortValue(timestamp: Timestamp): String {
        return GENERAL_DATE_FORMAT.format(timestamp.toDate())
    }

    fun getBibleReferenceDescription(bibleBookType: BibleBookType, chapter: Int, verse: Int = 0): String {
        return "${bibleBookType.getLocalizedString()} $chapter" + (if (verse == 0) "" else ":$verse")
    }

    fun getBiblePrayerTextDescription(biblePrayerText: BiblePrayerText): String {
        return "${biblePrayerText.prayerType.getLocalizedString()} - ${
            getBibleReferenceDescription(biblePrayerText.book,
                biblePrayerText.chapter,
                biblePrayerText.verse)
        }"
    }
}