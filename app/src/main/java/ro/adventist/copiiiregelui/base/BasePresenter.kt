package ro.adventist.copiiiregelui.base

import androidx.annotation.CallSuper
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.functions.Supplier
import ro.adventist.copiiiregelui.R
import ro.adventist.copiiiregelui.utils.NetworkChecker
import ro.adventist.copiiiregelui.utils.Logger
import ro.adventist.copiiiregelui.utils.RxUtils
import ro.adventist.copiiiregelui.utils.UseCase

open class BasePresenter<T : BaseContract.View?> protected constructor(protected val view: T) : BaseContract.Presenter {
    private var compositeDisposable: CompositeDisposable? = null
    override var isViewAttached = false
    private var networkDisposable: Disposable? = null

    /**
     * adds a disposable to a queue that gets canceled when the presenter is destroyed. useful for making sure we don't leave subscriptions hanging
     *
     * @param disposable a disposable
     */
    protected fun addPresenterSubscription(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable!!.add(disposable)
    }

    /**
     * Performs the use case, adds a base error handler that redirects to login if needed, if the use case fails, and shows/hides progress bars according to the
     * use case lifecycle
     *
     * @param useCase the use case to be performed
     */
    @Suppress("SameParameterValue")
    protected fun <Y> performUseCase(useCase: UseCase<Y>): Observable<Y> {
        return useCase.perform()
    }

    /**
     * Performs the given operation in background thread, without blocking the UI thread.
     *
     * @param <Y>            the type parameter of the doInBackground output function
     * @param showProgress   if set to true, the presenter will invoke the
     * [BaseContract.View.setProgressLayoutVisible] methods before executing the background operation and afterwards.
     * @param doInBackground operation to be perform in a new thread
     * @param onFinish       operation to be performed after obtaining the doInBackground result. Executed on UI thread.
     * @param onException    operation to be performed if doInBackground throws error. executed on UI thread.
    </Y> */
    protected fun <Y> performOperationOnBackground(showProgress: Boolean,
        doInBackground: Supplier<Y>,
        onFinish: Consumer<Y>,
        onException: Consumer<Throwable?>?) {
        if (showProgress) {
            view!!.setProgressLayoutVisible(true)
        }
        addPresenterSubscription(Observable.fromCallable {
            try {
                return@fromCallable doInBackground.get()
            } catch (throwable: Throwable) {
                Logger.e("BasePresenter", "error while executing doInBackground action!", throwable)
                return@fromCallable null
            }
        }.compose(RxUtils.applyNewThread()).subscribe(Consumer { y: Y? ->
            if (showProgress) {
                view!!.setProgressLayoutVisible(false)
            }
            onFinish.accept(y)
        }, Consumer { throwable: Throwable? ->
            if (showProgress) {
                view!!.setProgressLayoutVisible(false)
            }
            onException?.accept(throwable)
        }))
    }

    protected fun <Y> performOperationOnBackground(doInBackground: Supplier<Y>, onFinish: Consumer<Y>, onException: Consumer<Throwable?>?) {
        performOperationOnBackground(false, doInBackground, onFinish, onException)
    }

    @CallSuper
    override fun onAttachView() {
        isViewAttached = true

        NetworkChecker.start((view as BaseContract.View).toString())
        networkDisposable = NetworkChecker.isNetworkConnectedSubject
            .compose(RxUtils.applySchedulers())
            .subscribe { isConnected: Boolean ->
                if (isConnected) {
                    onNetworkFound()
                } else {
                    onNetworkLost()
                }
            }
    }

    protected open fun onNetworkLost() {
        if (!hasDisplayedLostNetworkMessage) {
            hasDisplayedLostNetworkMessage = true
            view?.showConfirmOperationDialog(R.string.warning, R.string.offlineMessage, android.R.string.ok, null, {}, null)
        }
    }

    protected open fun onNetworkFound() {
        hasDisplayedLostNetworkMessage = false
    }

    override fun onDetachView() {
        isViewAttached = false
        unSubscribe()
        NetworkChecker.stop((view as BaseContract.View).toString())
    }

    override fun subscribe() {}
    override fun unSubscribe() {
        if (compositeDisposable != null && compositeDisposable!!.size() != 0) {
            compositeDisposable!!.clear()
        }
    }

    companion object {
        private var hasDisplayedLostNetworkMessage = false
    }
}