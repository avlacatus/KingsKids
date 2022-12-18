package ro.azs.kidsdevelopment.ui.firestoreEntry

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Timestamp
import ro.azs.kidsdevelopment.R
import ro.azs.kidsdevelopment.base.BaseActivity
import ro.azs.kidsdevelopment.databinding.ActivityEntryDetailsBinding
import ro.azs.kidsdevelopment.models.*
import ro.azs.kidsdevelopment.ui.firestoreEntry.control.BibleVerseReferenceItemViewModel
import ro.azs.kidsdevelopment.ui.firestoreEntry.control.EntryItemViewModel
import ro.azs.kidsdevelopment.ui.firestoreEntry.control.StringItemViewModel
import ro.azs.kidsdevelopment.ui.firestoreEntry.control.LongTimestampItemViewModel
import java.util.Date


@Suppress("DEPRECATION")
class EntryDetailsActivity : BaseActivity<EntryDetailsContract.Presenter>(), EntryDetailsContract.View {

    private val viewModel by lazy { ViewModelProvider(this)[EntryDetailsViewModel::class.java] }
    private val presenter by lazy {
        val categorySectionType = intent.getSerializableExtra(INTENT_EXTRA_CATEGORY_SECTION_TYPE) as CategorySectionType
        val existingModel = (intent.getParcelableExtra(INTENT_EXTRA_EXISTING_MODEL) as FirestoreModel?)?.apply {
            id = intent.getStringExtra(INTENT_EXTRA_EXISTING_MODEL_ID)
        }

        EntryDetailsPresenter(this, categorySectionType, existingModel)
    }
    private var _binding: ActivityEntryDetailsBinding? = null

    private val binding get() = _binding!!

    private var showDeleteOption: Boolean = false
    private lateinit var categorySectionType: CategorySectionType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = ""
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_entry_details)
        setSupportActionBar(binding.toolbar)
        binding.viewModel = viewModel
        binding.presenter = presenter
        supportActionBar?.displayOptions = ActionBar.DISPLAY_HOME_AS_UP
    }

    override fun setupView(categorySectionType: CategorySectionType, existingEntryModel: FirestoreModel?) {
        this.categorySectionType = categorySectionType
        viewModel.setItemsMap(getItemViewModels(categorySectionType, existingEntryModel))
    }

    private fun getItemViewModels(categorySectionType: CategorySectionType, existingEntryModel: FirestoreModel?): LinkedHashMap<String, EntryItemViewModel> {
        return when (categorySectionType) {
            CategorySectionType.bibleDiscoveries -> {
                val existingDiscovery = existingEntryModel as? BibleDiscovery?

                val bibleRef = BibleVerseReferenceItemViewModel(existingDiscovery?.let {
                    BibleReference(it.book, it.chapter, 0)
                })
                val timestamp = LongTimestampItemViewModel(existingDiscovery?.date)
                val discovery = StringItemViewModel(R.string.discoveryLabel, existingDiscovery?.discovery)

                linkedMapOf(K.timestamp to timestamp,
                    K.bibleBookChapter to bibleRef,
                    K.discovery to discovery
                )
            }
//            CategorySectionType.bibleFavoriteTexts -> TODO()
//            CategorySectionType.biblePrayerTexts -> TODO()
//            CategorySectionType.prayerSubjects -> TODO()
//            CategorySectionType.prayerPeople -> TODO()
//            CategorySectionType.bodyRoutines -> TODO()
//            CategorySectionType.healthMetrics -> TODO()
//            CategorySectionType.healthDiscoveries -> TODO()
//            CategorySectionType.sportPractice -> TODO()
//            CategorySectionType.knowledgeFindings -> TODO()
//            CategorySectionType.hobbies -> TODO()
//            CategorySectionType.mediaDiscoveries -> TODO()
//            CategorySectionType.goodDeeds -> TODO()
//            CategorySectionType.dailySchedule -> TODO()
//            CategorySectionType.pleasantActions -> TODO()
//            CategorySectionType.usefulActions -> TODO()
            CategorySectionType.familyTasks -> {
                val existingTask = existingEntryModel as? FamilyTask?
                val timestamp = LongTimestampItemViewModel(existingTask?.date)
                val taskString = StringItemViewModel(R.string.taskLabel, existingTask?.task)

                linkedMapOf(K.timestamp to timestamp,
                    K.task to taskString)
            }
            CategorySectionType.schoolTasks -> {
                val existingTask = existingEntryModel as? SchoolTask?
                val timestamp = LongTimestampItemViewModel(existingTask?.date)
                val taskString = StringItemViewModel(R.string.taskLabel, existingTask?.task)

                linkedMapOf(K.timestamp to timestamp,
                    K.task to taskString)
            }
            CategorySectionType.churchTasks -> {
                val existingTask = existingEntryModel as? ChurchTask?
                val timestamp = LongTimestampItemViewModel(existingTask?.date)
                val taskString = StringItemViewModel(R.string.taskLabel, existingTask?.task)

                linkedMapOf(K.timestamp to timestamp,
                    K.task to taskString)
            }
//            CategorySectionType.finances -> TODO()
//            CategorySectionType.borrows -> TODO()
//            CategorySectionType.lends -> TODO()
            else -> {
                linkedMapOf("" to StringItemViewModel(R.string.sometimesLabel))
            }
        }
    }

    override fun getModelFromInput(): FirestoreModel {
        return when (categorySectionType) {
            CategorySectionType.bibleDiscoveries -> {
                val bibleRef = viewModel.getItemForTag(K.bibleBookChapter) as BibleVerseReferenceItemViewModel?
                val timestamp = viewModel.getItemForTag(K.timestamp) as LongTimestampItemViewModel?
                val discovery = viewModel.getItemForTag(K.discovery) as StringItemViewModel?

                BibleDiscovery(
                    bibleRef?.selectedReference?.get()?.bibleBookType ?: BibleBookType.genesis,
                    bibleRef?.selectedReference?.get()?.chapter ?: -1,
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    discovery?.value?.get() ?: ""
                )
            }

            CategorySectionType.familyTasks -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as LongTimestampItemViewModel?
                val task = viewModel.getItemForTag(K.task) as StringItemViewModel?

                FamilyTask(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    task?.value?.get() ?: ""
                )
            }
            CategorySectionType.schoolTasks -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as LongTimestampItemViewModel?
                val task = viewModel.getItemForTag(K.task) as StringItemViewModel?

                SchoolTask(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    task?.value?.get() ?: ""
                )
            }
            CategorySectionType.churchTasks -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as LongTimestampItemViewModel?
                val task = viewModel.getItemForTag(K.task) as StringItemViewModel?

                ChurchTask(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    task?.value?.get() ?: ""
                )
            }
            else -> TODO()
        }
    }

    override fun setupTitle(titleRes: Int) {
        viewModel.title.set(getString(titleRes))
    }

    override fun setShowDeleteOption(show: Boolean) {
        this.showDeleteOption = show
        invalidateMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (showDeleteOption) {
            menuInflater.inflate(R.menu.menu_delete, menu)
            return true
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_delete) {
            presenter.onDeleteButtonClicked()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun closeScreen() {
        finish()
    }

    override fun getPresenter(): EntryDetailsContract.Presenter = presenter

    companion object {
        private val TAG = EntryDetailsActivity::class.java.simpleName
        private const val INTENT_EXTRA_CATEGORY_SECTION_TYPE = "INTENT_EXTRA_CATEGORY_SECTION_TYPE"
        private const val INTENT_EXTRA_EXISTING_MODEL = "INTENT_EXTRA_EXISTING_MODEL"
        private const val INTENT_EXTRA_EXISTING_MODEL_ID = "INTENT_EXTRA_EXISTING_MODEL_ID"

        fun getIntent(callingActivity: Activity, categorySection: CategorySectionType, existingModel: FirestoreModel? = null): Intent {
            val output = Intent(callingActivity, EntryDetailsActivity::class.java)
            output.putExtra(INTENT_EXTRA_CATEGORY_SECTION_TYPE, categorySection)
            output.putExtra(INTENT_EXTRA_EXISTING_MODEL, existingModel)
            output.putExtra(INTENT_EXTRA_EXISTING_MODEL_ID, existingModel?.id)
            return output
        }
    }
}