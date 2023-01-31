package ro.adventist.copiiiregelui.utils;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxUtils {

    private RxUtils() {
    }

    /**
     * Apply Scheduler for RX java
     * This is used to subscribe on IO (background Thread) and to Observe On Android main Thread
     *
     * @param <T> the type of the items emitted by the Observable
     * @return an Observable that, when a {@link org.reactivestreams.Subscriber} subscribes to it, will execute the specified function
     */
    @NonNull
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public static <T> ObservableTransformer<T, T> applyNewThread() {
        return observable -> observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
