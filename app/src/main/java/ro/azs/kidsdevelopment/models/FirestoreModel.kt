package ro.azs.kidsdevelopment.models

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
open class FirestoreModel {

    @Exclude
    @DocumentId
    var id: String? = null

    fun <T : FirestoreModel> withId(id: String?): T {
        this.id = id
        return this as T
    }
}