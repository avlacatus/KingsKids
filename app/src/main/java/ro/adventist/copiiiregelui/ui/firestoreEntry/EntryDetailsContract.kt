package ro.adventist.copiiiregelui.ui.firestoreEntry

import ro.adventist.copiiiregelui.base.BaseContract
import ro.adventist.copiiiregelui.models.CategorySectionType
import ro.adventist.copiiiregelui.models.FirestoreModel

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