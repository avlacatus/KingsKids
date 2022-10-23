package ro.azs.kidsdevelopment.utils;


import androidx.annotation.NonNull;

import io.reactivex.rxjava3.core.Observable;

public interface UseCase<T> {

    @NonNull
    Observable<T> perform();
}
