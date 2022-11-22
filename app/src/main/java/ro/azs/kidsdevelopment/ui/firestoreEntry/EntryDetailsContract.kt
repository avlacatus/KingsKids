package ro.azs.kidsdevelopment.ui.firestoreEntry

import ro.azs.kidsdevelopment.base.BaseContract

class EntryDetailsContract {

    interface View : BaseContract.View {
       fun setupTitle(titleRes: Int)
       fun setMsg(msg: String)
    }

    interface Presenter : BaseContract.Presenter {
    }
}