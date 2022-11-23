package ro.azs.kidsdevelopment.ui.firestoreEntry

import ro.azs.kidsdevelopment.base.BasePresenter
import ro.azs.kidsdevelopment.models.*
import ro.azs.kidsdevelopment.utils.FirestoreDataManager
import ro.azs.kidsdevelopment.utils.Logger

class EntryDetailsPresenter(view: EntryDetailsContract.View,
    private val categorySectionType: CategorySectionType,
    private val existingEntryModel: FirestoreModel?) : BasePresenter<EntryDetailsContract.View>(view),
    EntryDetailsContract.Presenter {

    init {
        if (existingEntryModel == null) {
            view.setupTitle(categorySectionType.addLabelRes)
        } else {
            view.setShowDeleteOption(true)
            view.setupTitle(categorySectionType.editLabelRes)
            if (existingEntryModel is WithDescriptionTimestamp) {
                view.setMsg(existingEntryModel.getLongDescLabel())
            } else
                view.setMsg(existingEntryModel.toString())
        }

    }

    override fun onAttachView() {
        super.onAttachView()
    }


    override fun onDeleteButtonClicked() {
        view.showConfirmOperationDialog(message = categorySectionType.deleteMessageRes, onConfirm = {
            existingEntryModel?.let {
                FirestoreDataManager.removeModel(categorySectionType, it, { view.closeScreen() }, {
                    view.showToastMessage("Eroare: ${it.message}")
                })
            }
        })
    }

    override fun onSaveButtonPressed() {
        Logger.e("EntryDetailsPresenter", "saveButtonPressed")
    }

    companion object {
        private const val TAG = "CategoryDetailsPresenter"
    }
}