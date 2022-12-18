package ro.azs.kidsdevelopment.models

data class BibleReference(val bibleBookType: BibleBookType,
    val chapter: Int,
    val verse: Int) {
    override fun toString(): String {

        return "${bibleBookType.getLocalizedString()} ${chapter}" + (if (verse == 0) "" else ":$chapter")
    }
}