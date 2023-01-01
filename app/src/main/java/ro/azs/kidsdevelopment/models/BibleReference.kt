package ro.azs.kidsdevelopment.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BibleReference(val bibleBookType: BibleBookType,
    val chapter: Int,
    val verse: Int) : Parcelable {

    override fun toString(): String {
        return "${bibleBookType.getLocalizedString()} $chapter" + (if (verse == 0) "" else ":$verse")
    }

    companion object {
        fun getDefaultVerse(): BibleReference = BibleReference(BibleBookType.genesis, 1, 1)
        fun getDefaultChapter(): BibleReference = BibleReference(BibleBookType.genesis, 1, 0)
    }
}