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
        }

        if (existingEntryModel != null && existingEntryModel::class.java != categorySectionType.modelClass) {
            Logger.e(TAG, "data inconsistency between $categorySectionType and $existingEntryModel")
        } else {
            view.setupView(categorySectionType, existingEntryModel)
        }
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
        val modelFromView = view.getModelFromInput()
        if (modelFromView::class.java != categorySectionType.modelClass) {
            Logger.e(TAG, "data inconsistency between $categorySectionType and $existingEntryModel")
        } else {
            if (existingEntryModel != null) {
                modelFromView.id = existingEntryModel.id
            }

            FirestoreDataManager.updateModel(categorySectionType, modelFromView, {
                if (existingEntryModel == null)
                    view.showToastMessage("adaugare cu succes")
                else
                    view.showToastMessage("actualizare cu succes")
                view.closeScreen()
            }, {
                Logger.e(TAG, "update error: ${it.message}", it)
                if (existingEntryModel == null)
                    view.showToastMessage("adaugare cu eroare")
                else
                    view.showToastMessage("actualizare cu eroare")
            })
        }
    }

    companion object {
        private val TAG = EntryDetailsPresenter::class.java.simpleName
    }
}