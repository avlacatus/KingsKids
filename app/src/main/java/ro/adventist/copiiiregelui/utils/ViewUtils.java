package ro.adventist.copiiiregelui.utils;

import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ro.adventist.copiiiregelui.KingsKidsApplication;

public final class ViewUtils {

    private ViewUtils() {
    }

    @Nullable
    public static <T> T getListener(@NonNull Fragment fragment, @NonNull Class<T> interfaceClass) {
        Fragment parentFragment = fragment.getParentFragment();
        if (parentFragment != null && interfaceClass.isAssignableFrom(parentFragment.getClass())) {
            //noinspection unchecked
            return (T) parentFragment;
        }
        if (fragment.getContext() != null && interfaceClass.isAssignableFrom(fragment.getContext().getClass())) {
            //noinspection unchecked
            return (T) fragment.getContext();
        }
        return null;
    }


    public static void hideKeyboard(@Nullable IBinder iBinder) {
        InputMethodManager keyboard = (InputMethodManager) KingsKidsApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(iBinder, 0);
    }

    private static void showKeyboard(@NonNull EditText editText) {
        InputMethodManager keyboard = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.showSoftInput(editText, 0);
    }

}
