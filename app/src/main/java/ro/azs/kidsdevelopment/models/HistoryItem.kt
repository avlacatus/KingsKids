package ro.azs.kidsdevelopment.models

import com.google.firebase.Timestamp

data class HistoryItem(
    var date: Timestamp,
    var eventType: HistoryEventType,
    var objectId: String,
    var type: CategorySectionType) : FirestoreModel() {

    constructor() : this(Timestamp.now(), HistoryEventType.created, "",  CategorySectionType.bibleDiscoveries)
}