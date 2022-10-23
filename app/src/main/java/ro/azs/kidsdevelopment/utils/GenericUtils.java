package ro.azs.kidsdevelopment.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.util.HashMap;

import ro.azs.kidsdevelopment.KidsDevelopmentApplication;
import ro.azs.kidsdevelopment.R;

public class GenericUtils {

    private static final HashMap<Class, HashMap<String, Integer>> lookupForRStringId;

    static {
        lookupForRStringId = new HashMap<>();
        Class stringClass = R.string.class;

        HashMap<String, Integer> fieldsMap = new HashMap<>();
        GenericUtils.lookupForRStringId.put(stringClass, fieldsMap);

        Field[] fields = stringClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                fieldsMap.put(field.getName(), field.getInt(null)); //We live in a static world here. No need for an object parameter
            } catch (IllegalAccessException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                Log.e("GenericUtils", "could not initialize resources map: " + e.getMessage());
            }
        }
    }

    private static int getResId(String variableName) {
        HashMap<String, Integer> map = GenericUtils.lookupForRStringId.get(R.string.class);
        if (map != null) {
            Integer result = map.get(variableName);
            if (result == null) {
                Log.i("UNDEFINED_TAG", "Tried to use a resource by name that is not bundled with the app. resource=" + variableName);
                return -1;
            } else {
                return result;
            }
        } else {
            Log.i("UNDEFINED_TAG", "The main resource map is empty.");
            return -1;
        }
    }

    @NonNull
    public static String getStringByResourceIdentifier(@Nullable String variableName, @NonNull String fallback) {
        if (variableName == null) {
            return fallback;
        }
        int resId = GenericUtils.getResId(variableName);
        if (resId < 0) {
            return fallback;
        } else {
            Context context = KidsDevelopmentApplication.getAppContext();
            return context.getString(resId);
        }
    }
}
