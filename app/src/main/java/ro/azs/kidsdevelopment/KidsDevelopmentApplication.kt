package ro.azs.kidsdevelopment

import android.app.Application
import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import ro.azs.kidsdevelopment.utils.FirestoreDataManager
import ro.azs.kidsdevelopment.utils.LocaleUtils

class KidsDevelopmentApplication : Application() {
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
        private const val TAG = "KidsDevelopmentApplication"
        private var instance: KidsDevelopmentApplication? = null

        @JvmStatic
        val appContext: Context
            get() = LocaleUtils.getLocalizedContext(instance!!)
    }
}