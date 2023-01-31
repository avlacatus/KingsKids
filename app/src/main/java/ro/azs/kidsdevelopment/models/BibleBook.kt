package ro.azs.kidsdevelopment.models

data class BibleBook(
    var type: BibleBookType  = BibleBookType.genesis,
    var order: Int = 0,
    var chapters: Int = 0,
    var verses: Map<String, Int> = emptyMap()) {

}