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
import ro.azs.kidsdevelopment.ui.firestoreEntry.control.*
import java.util.*


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
                } ?: BibleReference.getDefaultChapter(), this, false)
                val timestamp = TimestampItemViewModel(existingDiscovery?.date ?: Timestamp.now(), this)
                val discovery = StringItemViewModel(R.string.discoveryLabel, existingDiscovery?.discovery)

                linkedMapOf(K.timestamp to timestamp,
                    K.bibleBookChapter to bibleRef,
                    K.discovery to discovery
                )
            }
            CategorySectionType.bibleFavoriteTexts -> {
                val existingModel = existingEntryModel as? BibleFavoriteText?
                val bibleRef = BibleVerseReferenceItemViewModel(existingModel?.let {
                    BibleReference(it.book, it.chapter, it.verse)
                } ?: BibleReference.getDefaultVerse(), this)
                val timestamp = TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this)

                linkedMapOf(K.timestamp to timestamp,
                    K.bibleBookChapter to bibleRef
                )
            }
            CategorySectionType.biblePrayerTexts -> {
                val existingModel = existingEntryModel as? BiblePrayerText?
                val bibleRef = BibleVerseReferenceItemViewModel(existingModel?.let {
                    BibleReference(it.book, it.chapter, it.verse)
                } ?: BibleReference.getDefaultVerse(), this)
                val timestamp = TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this)
                val discovery = StringItemViewModel(R.string.discoveryLabel, existingModel?.discovery)
                val prayerType =
                    SingleChoiceItemViewModel(R.string.prayerTypeLabel, existingModel?.prayerType ?: PrayerType.confession, PrayerType.values(), this)

                linkedMapOf(
                    K.timestamp to timestamp,
                    K.bibleBookChapter to bibleRef,
                    K.prayerType to prayerType,
                    K.discovery to discovery
                )
            }
            CategorySectionType.prayerSubjects -> {
                val existingModel = existingEntryModel as? PrayerSubject?
                val timestamp = TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this)
                val subject = StringItemViewModel(R.string.subjectLabel, existingModel?.subject)
                val description = StringItemViewModel(R.string.descriptionLabel, existingModel?.description, true)

                //TODO add prayerAnswer
                linkedMapOf(
                    K.timestamp to timestamp,
                    K.subject to subject,
                    K.description to description
                )

            }
            CategorySectionType.prayerPeople -> {
                val existingModel = existingEntryModel as? PrayerPeople?
                val timestamp = TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this)
                val person = StringItemViewModel(R.string.personLabel, existingModel?.person)
                val reason = StringItemViewModel(R.string.reasonLabel, existingModel?.reason, true)

                linkedMapOf(
                    K.timestamp to timestamp,
                    K.person to person,
                    K.reason to reason
                )
            }
            CategorySectionType.bodyRoutines -> TODO()
            CategorySectionType.healthMetrics -> {
                val existingModel = existingEntryModel as? HealthMetric?
                linkedMapOf(
                    K.timestamp to TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this),
                    K.height to NumberItemViewModel(R.string.heightLabel, existingModel?.height),
                    K.weight to NumberItemViewModel(R.string.weightLabel, existingModel?.weight)
                )

            }
            CategorySectionType.healthDiscoveries -> {
                val existingModel = existingEntryModel as? HealthDiscovery?
                val timestamp = TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this)
                val discoveryType =
                    SingleChoiceItemViewModel(R.string.healthTypeLabel, existingModel?.type ?: HealthDiscoveryType.food, HealthDiscoveryType.values(), this)
                val author = StringItemViewModel(R.string.authorLabel, existingModel?.book?.author)
                val bookName = StringItemViewModel(R.string.bookNameLabel, existingModel?.book?.name)
                val discovery = StringItemViewModel(R.string.discoveryLabel, existingModel?.discovery)

                linkedMapOf(
                    K.timestamp to timestamp,
                    K.discoveryType to discoveryType,
                    K.bookAuthor to author,
                    K.bookName to bookName,
                    K.discovery to discovery
                )
            }

            CategorySectionType.sportPractice -> {
                val existingModel = existingEntryModel as? SportPractice?
                val timestamp = TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this)
                val sport = StringItemViewModel(R.string.sportLabel, existingModel?.sport)
                val location = StringItemViewModel(R.string.locationLabel, existingModel?.location)

                linkedMapOf(
                    K.timestamp to timestamp,
                    K.sport to sport,
                    K.location to location
                )
            }

            CategorySectionType.knowledgeFindings -> {
                val existingModel = existingEntryModel as? KnowledgeFinding?
                val timestamp = TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this)
                val findingType =
                    SingleChoiceItemViewModel(R.string.whereLabel, existingModel?.type ?: KnowledgeFindingType.family, KnowledgeFindingType.values(), this)
                val discovery = StringItemViewModel(R.string.discoveryLabel, existingModel?.discovery, true)

                linkedMapOf(
                    K.timestamp to timestamp,
                    K.findingType to findingType,
                    K.discovery to discovery
                )
            }
            CategorySectionType.hobbies -> {
                val existingModel = existingEntryModel as? HobbyItem?
                val timestamp = TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this)
                val hobbyType = SingleChoiceItemViewModel(R.string.hobbyTypeLabel, existingModel?.type ?: HobbyItemType.reading, HobbyItemType.values(), this)
                val hobbyTypeDetails = StringItemViewModel(R.string.hobbyTypeDetailsLabel, existingModel?.typeDetails)
                val details = StringItemViewModel(R.string.hobbyDetailsLabel, existingModel?.details)

                linkedMapOf(
                    K.timestamp to timestamp,
                    K.hobbyType to hobbyType,
                    K.typeDetails to hobbyTypeDetails,
                    K.details to details,
                )
            }
            CategorySectionType.mediaDiscoveries -> {
                val existingModel = existingEntryModel as? MediaDiscovery?
                linkedMapOf(
                    K.timestamp to TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this),
                    K.discoveryType to SingleChoiceItemViewModel(R.string.mediaTypeLabel,
                        existingModel?.type ?: MediaDiscoveryType.internet,
                        MediaDiscoveryType.values(),
                        this),
                    K.discovery to StringItemViewModel(R.string.discoveryLabel, existingModel?.discovery, true),
                )
            }
            CategorySectionType.goodDeeds -> {
                val existingModel = existingEntryModel as? GoodDeed?
                linkedMapOf(
                    K.timestamp to TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this),
                    K.deedType to SingleChoiceItemViewModel(R.string.goodDeedTypeLabel,
                        existingModel?.type ?: GoodDeedType.volunteering,
                        GoodDeedType.values(),
                        this),
                    K.details to StringItemViewModel(R.string.detailsLabel, existingModel?.details, true),
                )
            }
            CategorySectionType.dailySchedule -> TODO()
            CategorySectionType.pleasantActions -> {
                val existingModel = existingEntryModel as? PleasantAction?
                linkedMapOf(
                    K.timestamp to TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this),
                    K.action to StringItemViewModel(R.string.pleasantActionLabel, existingModel?.action, true),
                )
            }

            CategorySectionType.usefulActions -> {
                val existingModel = existingEntryModel as? UsefulAction?
                linkedMapOf(
                    K.timestamp to TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this),
                    K.actionType to SingleChoiceItemViewModel(R.string.usefulActionTypeLabel,
                        existingModel?.type ?: UsefulActionType.family,
                        UsefulActionType.values(),
                        this),
                    K.details to StringItemViewModel(R.string.usefulActionLabel, existingModel?.action, true),
                )
            }

            CategorySectionType.familyTasks -> {
                val existingTask = existingEntryModel as? FamilyTask?

                linkedMapOf(K.timestamp to TimestampItemViewModel(existingTask?.date ?: Timestamp.now(), this),
                    K.task to StringItemViewModel(R.string.taskLabel, existingTask?.task))
            }
            CategorySectionType.schoolTasks -> {
                val existingTask = existingEntryModel as? SchoolTask?

                linkedMapOf(K.timestamp to TimestampItemViewModel(existingTask?.date ?: Timestamp.now(), this),
                    K.task to StringItemViewModel(R.string.taskLabel, existingTask?.task))
            }
            CategorySectionType.churchTasks -> {
                val existingTask = existingEntryModel as? ChurchTask?

                linkedMapOf(K.timestamp to TimestampItemViewModel(existingTask?.date ?: Timestamp.now(), this),
                    K.task to StringItemViewModel(R.string.taskLabel, existingTask?.task))
            }
            CategorySectionType.finances -> {
                val existingModel = existingEntryModel as? Finance?
                linkedMapOf(
                    K.timestamp to TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this),
                    K.financeType to SingleChoiceItemViewModel(R.string.financeTypeLabel,
                        existingModel?.type ?: FinanceType.savings,
                        FinanceType.values(),
                        this),
                    K.amount to NumberItemViewModel(R.string.amountLabel, existingModel?.amount),
                    K.currency to SingleChoiceItemViewModel(R.string.currencyLabel,
                        existingModel?.amountCurrency ?: FinanceCurrency.RON,
                        FinanceCurrency.values(),
                        this)
                )
            }
            CategorySectionType.borrows -> {
                val existingModel = existingEntryModel as? Borrows?
                linkedMapOf(
                    K.timestamp to TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this),
                    K.reason to StringItemViewModel(R.string.reasonLabel, existingModel?.reason),
                    K.amount to NumberItemViewModel(R.string.amountLabel, existingModel?.amount),
                    K.currency to SingleChoiceItemViewModel(R.string.currencyLabel,
                        existingModel?.amountCurrency ?: FinanceCurrency.RON,
                        FinanceCurrency.values(),
                        this)
                )
            }

            CategorySectionType.lends -> {
                val existingModel = existingEntryModel as? Lends?
                linkedMapOf(
                    K.timestamp to TimestampItemViewModel(existingModel?.date ?: Timestamp.now(), this),
                    K.reason to StringItemViewModel(R.string.reasonLabel, existingModel?.reason),
                    K.amount to NumberItemViewModel(R.string.amountLabel, existingModel?.amount),
                    K.currency to SingleChoiceItemViewModel(R.string.currencyLabel,
                        existingModel?.amountCurrency ?: FinanceCurrency.RON,
                        FinanceCurrency.values(),
                        this)
                )
            }
        }
    }

    override fun getModelFromInput(): FirestoreModel {
        return when (categorySectionType) {
            CategorySectionType.bibleDiscoveries -> {
                val bibleRef = viewModel.getItemForTag(K.bibleBookChapter) as BibleVerseReferenceItemViewModel?
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val discovery = viewModel.getItemForTag(K.discovery) as StringItemViewModel?

                BibleDiscovery(
                    bibleRef?.selectedReference?.get()?.bibleBookType ?: BibleBookType.genesis,
                    bibleRef?.selectedReference?.get()?.chapter ?: -1,
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    discovery?.value?.get() ?: ""
                )
            }
            CategorySectionType.bibleFavoriteTexts -> {
                val bibleRef = viewModel.getItemForTag(K.bibleBookChapter) as BibleVerseReferenceItemViewModel?
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?

                BibleFavoriteText(
                    bibleRef?.selectedReference?.get()?.bibleBookType ?: BibleBookType.genesis,
                    bibleRef?.selectedReference?.get()?.chapter ?: -1,
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    bibleRef?.selectedReference?.get()?.verse ?: -1,
                )
            }

            CategorySectionType.biblePrayerTexts -> {
                val bibleRef = viewModel.getItemForTag(K.bibleBookChapter) as BibleVerseReferenceItemViewModel?
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val discovery = viewModel.getItemForTag(K.discovery) as StringItemViewModel?
                val prayerType = viewModel.getItemForTag(K.prayerType) as SingleChoiceItemViewModel<PrayerType>?

                BiblePrayerText(
                    bibleRef?.selectedReference?.get()?.bibleBookType ?: BibleBookType.genesis,
                    bibleRef?.selectedReference?.get()?.chapter ?: -1,
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    discovery?.value?.get() ?: "",
                    prayerType?.selectedOption?.get() ?: PrayerType.confession,
                    bibleRef?.selectedReference?.get()?.verse ?: -1,
                )
            }

            CategorySectionType.prayerSubjects -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val subject = viewModel.getItemForTag(K.subject) as StringItemViewModel?
                val description = viewModel.getItemForTag(K.description) as StringItemViewModel?

                PrayerSubject(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    subject?.value?.get() ?: "",
                    description?.value?.get() ?: ""
                )
            }

            CategorySectionType.prayerPeople -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val person = viewModel.getItemForTag(K.person) as StringItemViewModel?
                val reason = viewModel.getItemForTag(K.reason) as StringItemViewModel?

                //TODO add prayerAnswer
                PrayerPeople(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    person?.value?.get() ?: "",
                    reason?.value?.get() ?: ""
                )
            }

            CategorySectionType.healthDiscoveries -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val discoveryType = viewModel.getItemForTag(K.discoveryType) as SingleChoiceItemViewModel<HealthDiscoveryType>?
                val bookAuthor = viewModel.getItemForTag(K.bookAuthor) as StringItemViewModel?
                val bookName = viewModel.getItemForTag(K.bookName) as StringItemViewModel?
                val discovery = viewModel.getItemForTag(K.discovery) as StringItemViewModel?

                HealthDiscovery(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    Book(bookAuthor?.value?.get() ?: "", bookName?.value?.get() ?: ""),
                    discoveryType?.selectedOption?.get() ?: HealthDiscoveryType.food,
                    discovery?.value?.get() ?: ""
                )
            }

            CategorySectionType.sportPractice -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val sport = viewModel.getItemForTag(K.sport) as StringItemViewModel?
                val location = viewModel.getItemForTag(K.location) as StringItemViewModel?

                SportPractice(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    sport?.value?.get() ?: "",
                    location?.value?.get() ?: ""
                )
            }

            CategorySectionType.knowledgeFindings -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val findingType = viewModel.getItemForTag(K.findingType) as SingleChoiceItemViewModel<KnowledgeFindingType>?
                val discovery = viewModel.getItemForTag(K.discovery) as StringItemViewModel?

                KnowledgeFinding(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    discovery?.value?.get() ?: "",
                    findingType?.selectedOption?.get() ?: KnowledgeFindingType.church
                )
            }

            CategorySectionType.hobbies -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val hobbyType = viewModel.getItemForTag(K.hobbyType) as SingleChoiceItemViewModel<HobbyItemType>?
                val hobbyTypeDetails = viewModel.getItemForTag(K.typeDetails) as StringItemViewModel?
                val details = viewModel.getItemForTag(K.details) as StringItemViewModel?

                HobbyItem(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    hobbyType?.selectedOption?.get() ?: HobbyItemType.playing,
                    hobbyTypeDetails?.value?.get() ?: "",
                    details?.value?.get() ?: "",
                )
            }

            CategorySectionType.mediaDiscoveries -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val mediaDiscoveryType = viewModel.getItemForTag(K.discoveryType) as SingleChoiceItemViewModel<MediaDiscoveryType>?
                val discovery = viewModel.getItemForTag(K.discovery) as StringItemViewModel?

                MediaDiscovery(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    mediaDiscoveryType?.selectedOption?.get() ?: MediaDiscoveryType.internet,
                    discovery?.value?.get() ?: ""
                )
            }

            CategorySectionType.goodDeeds -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val goodDeedType = viewModel.getItemForTag(K.deedType) as SingleChoiceItemViewModel<GoodDeedType>?
                val details = viewModel.getItemForTag(K.details) as StringItemViewModel?

                GoodDeed(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    goodDeedType?.selectedOption?.get() ?: GoodDeedType.kindness,
                    details?.value?.get() ?: ""
                )
            }

            CategorySectionType.pleasantActions -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val details = viewModel.getItemForTag(K.details) as StringItemViewModel?

                PleasantAction(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    details?.value?.get() ?: ""
                )
            }

            CategorySectionType.usefulActions -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val actionType = viewModel.getItemForTag(K.actionType) as SingleChoiceItemViewModel<UsefulActionType>?
                val details = viewModel.getItemForTag(K.details) as StringItemViewModel?

                UsefulAction(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    actionType?.selectedOption?.get() ?: UsefulActionType.family,
                    details?.value?.get() ?: ""
                )
            }

            CategorySectionType.familyTasks -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val task = viewModel.getItemForTag(K.task) as StringItemViewModel?

                FamilyTask(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    task?.value?.get() ?: ""
                )
            }
            CategorySectionType.schoolTasks -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val task = viewModel.getItemForTag(K.task) as StringItemViewModel?

                SchoolTask(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    task?.value?.get() ?: ""
                )
            }
            CategorySectionType.churchTasks -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val task = viewModel.getItemForTag(K.task) as StringItemViewModel?

                ChurchTask(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    task?.value?.get() ?: ""
                )
            }
            CategorySectionType.bodyRoutines -> TODO()
            CategorySectionType.healthMetrics -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val height = viewModel.getItemForTag(K.height) as NumberItemViewModel?
                val weight = viewModel.getItemForTag(K.weight) as NumberItemViewModel?

                HealthMetric(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    height?.value?.get() ?: 0.0,
                    weight?.value?.get() ?: 0.0
                )

            }
            CategorySectionType.dailySchedule -> TODO()
            CategorySectionType.finances -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val financeType = viewModel.getItemForTag(K.financeType) as SingleChoiceItemViewModel<FinanceType>?
                val amount = viewModel.getItemForTag(K.amount) as NumberItemViewModel?
                val currency = viewModel.getItemForTag(K.currency) as SingleChoiceItemViewModel<FinanceCurrency>?

                Finance(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    financeType?.selectedOption?.get() ?: FinanceType.earning,
                    amount?.value?.get() ?: 0.0,
                    currency?.selectedOption?.get() ?: FinanceCurrency.RON
                )
            }
            CategorySectionType.borrows -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val reason = viewModel.getItemForTag(K.reason) as StringItemViewModel?
                val amount = viewModel.getItemForTag(K.amount) as NumberItemViewModel?
                val currency = viewModel.getItemForTag(K.currency) as SingleChoiceItemViewModel<FinanceCurrency>?

                Borrows(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    reason?.value?.get() ?: "",
                    amount?.value?.get() ?: 0.0,
                    currency?.selectedOption?.get() ?: FinanceCurrency.RON
                )
            }
            CategorySectionType.lends -> {
                val timestamp = viewModel.getItemForTag(K.timestamp) as TimestampItemViewModel?
                val reason = viewModel.getItemForTag(K.reason) as StringItemViewModel?
                val amount = viewModel.getItemForTag(K.amount) as NumberItemViewModel?
                val currency = viewModel.getItemForTag(K.currency) as SingleChoiceItemViewModel<FinanceCurrency>?

                Lends(
                    timestamp?.selectedDate?.get() ?: Timestamp(Date()),
                    reason?.value?.get() ?: "",
                    amount?.value?.get() ?: 0.0,
                    currency?.selectedOption?.get() ?: FinanceCurrency.RON
                )
            }
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