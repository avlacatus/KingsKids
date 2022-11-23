package ro.azs.kidsdevelopment.ui.firestoreEntry

import ro.azs.kidsdevelopment.base.BaseContract

class EntryDetailsContract {

    interface View : BaseContract.View {

        fun setupTitle(titleRes: Int)
        fun setShowDeleteOption(show: Boolean)
        fun setMsg(msg: String)
        fun closeScreen()
    }

    interface Presenter : BaseContract.Presenter {
        fun onSaveButtonPressed()
        fun onDeleteButtonClicked()
    }
}