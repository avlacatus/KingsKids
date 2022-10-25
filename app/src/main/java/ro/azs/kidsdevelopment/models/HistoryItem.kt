package ro.azs.kidsdevelopment.models

import com.google.firebase.Timestamp

public enum class HistoryEventType : Labeled {
    deleted,
    created,
    updated,
}

data class HistoryItem(
    var date: Timestamp,
    var eventType: HistoryEventType,
    var objectId: String,
    var type: CategorySectionType) : FirestoreModel() {

    constructor() : this(Timestamp.now(), HistoryEventType.created, "",  CategorySectionType.bibleDiscoveries)
}