package ro.azs.kidsdevelopment.ui.firestoreEntry.control

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.fragment.app.DialogFragment
import com.google.android.material.tabs.TabLayout.TabGravity
import me.tatarka.bindingcollectionadapter2.ItemBinding
import ro.azs.kidsdevelopment.BR
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.databinding.DialogSelectBibleReferenceBinding
import ro.azs.kidsdevelopment.models.BibleBookType
import ro.azs.kidsdevelopment.models.BibleReference
import ro.azs.kidsdevelopment.models.Labeled
import ro.azs.kidsdevelopment.utils.Logger

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

        viewModel.onSave.set {
            viewModel.bibleReference.get()?.let {
                correspondingFieldVM!!.selectedReference.set(it)
                dismiss()
            } ?: run {
                Toast.makeText(requireContext(), "Trebuie sa completezi toate campurile!", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.showVersePicker.set(arguments?.getBoolean(ARG_SHOW_VERSE_PICKER) ?: true)


        val initialReference = (arguments?.getParcelable(ARG_INITIAL_REFERENCE) as BibleReference?) ?: kotlin.run {
            getDefaultReference()
        }

        viewModel.bibleReference.set(initialReference)

        viewModel.bookItems.addAll(BibleBookType.values()
            .map { ItemViewModel(it, it.getLocalizedString(), it == initialReference.bibleBookType, this::onBibleBookSelected) })
        viewModel.chapterItems.addAll((1..initialReference.bibleBookType.chapters).map {
            ItemViewModel(
                it,
                it.toString(),
                it == initialReference.chapter,
                this::onChapterSelected
            )
        })
        viewModel.verseItems.addAll((1..initialReference.bibleBookType.verseCounts[initialReference.chapter - 1]).map {
            ItemViewModel(it,
                it.toString(),
                it == initialReference.verse,
                this::onVerseSelected)
        })

        binding.rvBooks.scrollToPosition(initialReference.bibleBookType.ordinal)
        binding.rvChapters.scrollToPosition(initialReference.chapter - 1)
        binding.rvVerses.scrollToPosition(if (initialReference.verse > 1) initialReference.verse - 1 else 0)
    }

    private fun getDefaultReference(): BibleReference =
        if (viewModel.showVersePicker.get()) BibleReference.getDefaultVerse() else BibleReference.getDefaultChapter()

    private fun onBibleBookSelected(bibleBook: Any) {
        if (bibleBook !is BibleBookType) return
        Logger.e(TAG, "onBibleBookSelected: $bibleBook")
        viewModel.bibleReference.get()?.let { safePrevReference ->
            if (safePrevReference.bibleBookType != bibleBook) {
                val bibleReference = getDefaultReference().copy(bibleBookType = bibleBook)
                viewModel.bibleReference.set(bibleReference)
                viewModel.bookItems.forEach { itemViewModel -> itemViewModel.isSelected.set(itemViewModel.tag == bibleBook) }

                viewModel.chapterItems.clear()
                viewModel.chapterItems.addAll((1..bibleReference.bibleBookType.chapters).map {
                    ItemViewModel(
                        it,
                        it.toString(),
                        it == bibleReference.chapter,
                        this::onChapterSelected
                    )
                })

                viewModel.verseItems.clear()
                viewModel.verseItems.addAll((1..bibleReference.bibleBookType.verseCounts[bibleReference.chapter - 1]).map {
                    ItemViewModel(it,
                        it.toString(),
                        it == bibleReference.verse,
                        this::onVerseSelected)
                })
            }
        }
    }

    private fun onChapterSelected(chapter: Any) {
        if (chapter !is Int) return
        Logger.e(TAG, "onChapterSelected: $chapter")

        viewModel.bibleReference.get()?.let { safePrevReference ->
            val bibleReference = safePrevReference.copy(chapter = chapter)
            viewModel.bibleReference.set(bibleReference)

            viewModel.chapterItems.forEach { itemViewModel -> itemViewModel.isSelected.set(itemViewModel.tag == chapter) }

            viewModel.verseItems.clear()
            viewModel.verseItems.addAll((1..bibleReference.bibleBookType.verseCounts[bibleReference.chapter - 1]).map {
                ItemViewModel(it,
                    it.toString(),
                    it == bibleReference.verse,
                    this::onVerseSelected)
            })
        }
    }

    private fun onVerseSelected(verse: Any) {
        if (verse !is Int) return
        Logger.e(TAG, "onVerseSelected: $verse")
        viewModel.bibleReference.get()?.let { safePrevReference ->
            val bibleReference = safePrevReference.copy(verse = verse)
            viewModel.bibleReference.set(bibleReference)

            viewModel.verseItems.forEach { itemViewModel -> itemViewModel.isSelected.set(itemViewModel.tag == verse) }
        }
    }

    data class LabeledBibleBook(val bibleBook: BibleBookType) : Labeled {
        override val name: String
            get() = bibleBook.getLocalizedString()

        override fun toString(): String = name

    }

    class ItemViewModel<T>(@JvmField val tag: T, initialLabel: String = "", initialSelected: Boolean = false, @JvmField val onSelected: (Any) -> Unit = {}) {
        @JvmField
        val label = ObservableField(initialLabel)

        @JvmField
        val isSelected = ObservableBoolean(initialSelected)
    }

    class ViewModel {
        val bibleReference = ObservableField<BibleReference>()

        val showVersePicker = ObservableBoolean(true)
        val onSave = ObservableField {}

        val bookItems = ObservableArrayList<ItemViewModel<BibleBookType>>()
        val chapterItems = ObservableArrayList<ItemViewModel<Int>>()
        val verseItems = ObservableArrayList<ItemViewModel<Int>>()

        val itemBindings = ItemBinding.of { itemBinding: ItemBinding<*>, _: Int, _: ItemViewModel<*>? ->
            itemBinding[BR.viewModel] = R.layout.item_list_verse_picker_element
        }
    }

    companion object {
        private val TAG = SelectBibleReferenceDialog::class.java.simpleName
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