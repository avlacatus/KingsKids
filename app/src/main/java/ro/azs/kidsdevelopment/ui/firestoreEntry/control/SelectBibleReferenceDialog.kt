package ro.azs.kidsdevelopment.ui.firestoreEntry.control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.fragment.app.DialogFragment
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.databinding.DialogSelectBibleReferenceBinding
import ro.azs.kidsdevelopment.models.BibleBookType
import ro.azs.kidsdevelopment.models.BibleReference
import ro.azs.kidsdevelopment.models.Labeled
import ro.azs.kidsdevelopment.utils.AutoCompleteUtils
import ro.azs.kidsdevelopment.utils.Logger
import ro.azs.kidsdevelopment.utils.ViewUtils

class SelectBibleReferenceDialog : DialogFragment() {

    private lateinit var binding: DialogSelectBibleReferenceBinding
    private val viewModel = ViewModel()
    private val correspondingFieldVM: BibleVerseReferenceItemViewModel? by lazy { arguments?.getSerializable(ARG_CORRESPONDING_FIELD_VM) as BibleVerseReferenceItemViewModel? }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
        setStyle(STYLE_NO_FRAME, R.style.Full_Dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_select_bible_reference, container, false)
        binding.viewModel = viewModel

        viewModel.action.set {
            val selectedBook = viewModel.bibleBook.get()
            val selectedChapter = viewModel.chapter.get()?.toIntOrNull()
            val selectedVerse = viewModel.verse.get()?.toIntOrNull()

            if (selectedBook != null && selectedChapter != null && (selectedVerse != null || !viewModel.showVersePicker.get()) && correspondingFieldVM != null) {
                correspondingFieldVM!!.selectedReference.set(BibleReference(selectedBook, selectedChapter, selectedVerse ?: 0))
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Trebuie sa completezi toate campurile!", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showVersePicker.set(arguments?.getBoolean(ARG_SHOW_VERSE_PICKER) ?: true)

        var bibleRefBook: BibleBookType? = null

        (arguments?.getParcelable(ARG_INITIAL_REFERENCE) as BibleReference?)?.let {
            viewModel.chapter.set(it.chapter.toString())
            viewModel.verse.set(it.verse.toString())
            bibleRefBook = it.bibleBookType
        }

        AutoCompleteUtils.setupAutoCompleteFieldValues(requireContext(),
            binding.bibleBook,
            BibleBookType.values().map { LabeledBibleBook(it) }.toList(),
            bibleRefBook?.ordinal ?: 0,
            { selectedOption ->
                viewModel.bibleBook.set(selectedOption?.bibleBook)
            },
            { selectedBibleBookType, tv ->
                Logger.e("SelectBibleReferenceDialog", "onItemTransformed: $selectedBibleBookType $tv")
            }
        )
    }

    data class LabeledBibleBook(val bibleBook: BibleBookType) : Labeled {
        override val name: String
            get() = bibleBook.getLocalizedString()

        override fun toString(): String = name

    }

    class ViewModel {
        val bibleBook = ObservableField<BibleBookType>()
        val chapter = ObservableField("")
        val verse = ObservableField("")
        val showVersePicker = ObservableBoolean(true)
        val action = ObservableField {}
    }

    companion object {
        private const val ARG_INITIAL_REFERENCE = "ARG_INITIAL_REFERENCE"
        private const val ARG_CORRESPONDING_FIELD_VM = "ARG_CORRESPONDING_FIELD_VM"
        private const val ARG_SHOW_VERSE_PICKER = "ARG_SHOW_VERSE_PICKER"
        fun getDialog(initialReference: BibleReference?,
            correspondingViewModel: BibleVerseReferenceItemViewModel,
            showVersePicker: Boolean = true): SelectBibleReferenceDialog {
            return SelectBibleReferenceDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_INITIAL_REFERENCE, initialReference)
                    putSerializable(ARG_CORRESPONDING_FIELD_VM, correspondingViewModel)
                    putBoolean(ARG_SHOW_VERSE_PICKER, showVersePicker)
                }
            }
        }
    }
}