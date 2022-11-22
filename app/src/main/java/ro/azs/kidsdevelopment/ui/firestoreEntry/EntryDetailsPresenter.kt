package ro.azs.kidsdevelopment.ui.firestoreEntry

import ro.azs.kidsdevelopment.base.BasePresenter
import ro.azs.kidsdevelopment.models.*

class EntryDetailsPresenter(view: EntryDetailsContract.View,
    private val categorySectionType: CategorySectionType,
    private val existingEntryModel: FirestoreModel?) : BasePresenter<EntryDetailsContract.View>(view),
    EntryDetailsContract.Presenter {

    init {
        if (existingEntryModel == null) {
            view.setupTitle(categorySectionType.addLabelRes)
        } else {
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


    companion object {
        private const val TAG = "CategoryDetailsPresenter"
    }
}