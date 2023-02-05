package ro.adventist.copiiiregelui

import android.app.Application
import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import ro.adventist.copiiiregelui.utils.FirestoreDataManager
import ro.adventist.copiiiregelui.utils.LocaleUtils

class KingsKidsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleListener())
    }

    class AppLifecycleListener : DefaultLifecycleObserver {

        override fun onStart(owner: LifecycleOwner) {
            FirestoreDataManager.startDataSync()
        }

        override fun onStop(owner: LifecycleOwner) {
            FirestoreDataManager.stopDataSync()
        }
    }

    companion object {
        private const val TAG = "KingsKidsApplication"
        private var instance: KingsKidsApplication? = null

        @JvmStatic
        val appContext: Context
            get() = LocaleUtils.getLocalizedContext(instance!!)
    }
}