package ro.adventist.copiiiregelui.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.rxjava3.subjects.BehaviorSubject
import ro.adventist.copiiiregelui.KingsKidsApplication
import java.util.*


object NetworkChecker {
    private val TAG = (NetworkChecker::class).simpleName ?: "NetworkChecker"

    private val availableNetworks = Collections.synchronizedSet(HashSet<String>())
    private val connectivityManager by lazy {
        (KingsKidsApplication.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)
    }

    private fun hasAvailableNetwork() = availableNetworks.isNotEmpty()

    private var isRegistered = false
    private val networkCheckerRegisters: HashSet<String> = HashSet()


    private val networkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                availableNetworks.add(network.toString())
                if (isNetworkConnectedSubject.value == false) {
                    isNetworkConnectedSubject.onNext(true)
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                availableNetworks.remove(network.toString())
                val hasAvailableNetwork = hasAvailableNetwork()
                if (hasAvailableNetwork != isNetworkConnectedSubject.value) {
                    isNetworkConnectedSubject.onNext(hasAvailableNetwork)
                }
            }
        }
    }


    val isNetworkConnectedSubject: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(getDefaultSubjectValue())

    private fun getDefaultSubjectValue() : Boolean{
        if (true) {
            return (connectivityManager?.activeNetworkInfo?.isAvailable ?: false) && (connectivityManager?.activeNetworkInfo?.isConnected ?: false)
        } else {
            return  false
        }
    }

     fun isNetworkConnected(): Boolean? = isNetworkConnectedSubject.value

     fun start(label: String) {
         networkCheckerRegisters.add(label)
        if (!isRegistered) {
            connectivityManager?.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
            isRegistered = true
        }
    }

     fun stop(label: String) {
        networkCheckerRegisters.remove(label)
        if (networkCheckerRegisters.isEmpty()) {
            try {
                connectivityManager?.unregisterNetworkCallback(networkCallback)
            } catch (e: Exception) {
                Logger.i(TAG, "unregisterNetworkCallback error: ${e.message}")
            }
            isRegistered = false
        }
    }
}