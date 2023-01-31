package ro.azs.kidsdevelopment.ui.firestoreEntry

import ro.azs.kidsdevelopment.base.BaseContract
import ro.azs.kidsdevelopment.models.CategorySectionType
import ro.azs.kidsdevelopment.models.FirestoreModel

class EntryDetailsContract {

    interface View : BaseContract.View {

        fun playSuccessSound()
        fun showSuccessToast(messageRes: Int, onDismiss: () -> Unit)
        fun showSuccessToast(message: String, onDismiss: () -> Unit)
        fun setupTitle(titleRes: Int)
        fun setShowDeleteOption(show: Boolean)
        fun closeScreen()
        fun setupView(categorySectionType: CategorySectionType, existingEntryModel: FirestoreModel?)
        fun getModelFromInput(): FirestoreModel
    }

    interface Presenter : BaseContract.Presenter {
        fun onSaveButtonPressed()
        fun onDeleteButtonClicked()
    }
}