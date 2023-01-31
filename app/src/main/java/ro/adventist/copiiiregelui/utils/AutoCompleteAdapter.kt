package ro.adventist.copiiiregelui.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import ro.adventist.copiiiregelui.R
import ro.adventist.copiiiregelui.models.Labeled
import ro.adventist.copiiiregelui.utils.AutoCompleteUtils.normalizeIgnoreDiacritics
import java.util.*

open class AutoCompleteAdapter<T : Labeled?>(context: Context, items: List<T>) :
    ArrayAdapter<T>(context, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, items) {
    private val items: List<T>
    private var filter: AutoCompleteFilter? = null

    init {
        this.items = ArrayList(items)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val output = super.getView(position, convertView, parent)
        val text = output.findViewById<TextView>(R.id.text)
        if (text != null) {
            transformView(position, getItem(position), text)
        }
        return output
    }

    open fun transformView(position: Int, item: T?, textView: TextView?) {
        //no-op
    }

    override fun getFilter(): Filter {
        if (filter == null) {
            filter = AutoCompleteFilter()
        }
        return filter!!
    }

    private inner class AutoCompleteFilter : Filter() {
        override fun convertResultToString(resultValue: Any): CharSequence {
            return if (resultValue is Labeled) {
                resultValue.name
            } else super.convertResultToString(resultValue)
        }

        override fun performFiltering(prefix: CharSequence?): FilterResults {
            val results = FilterResults()
            if (prefix == null || prefix.isEmpty()) {
                results.values = ArrayList(items)
                results.count = items.size
            } else {
                val prefixString = normalizeIgnoreDiacritics(prefix.toString()).lowercase(Locale.getDefault())
                val count = items.size
                val newValues = ArrayList<T>()
                for (i in 0 until count) {
                    val value = items[i]
                    val normalizedValueText = normalizeIgnoreDiacritics(value!!.name).lowercase(Locale.getDefault())
                    if (normalizedValueText.startsWith(prefixString)) {
                        newValues.add(value)
                    } else {
                        val words = normalizedValueText.split(" ".toRegex()).toTypedArray()
                        for (word in words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value)
                                break
                            }
                        }
                    }
                }
                results.values = newValues
                results.count = newValues.size
            }
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            val filteredList = results?.values as ArrayList<T>
            if (results.count > 0) {
                clear()
                addAll(filteredList)
                notifyDataSetChanged()
            }
        }
    }

    companion object {
        private val TAG = AutoCompleteAdapter::class.java.simpleName
    }
}