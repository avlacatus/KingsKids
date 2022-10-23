package ro.azs.kidsdevelopment.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;

import java.util.Locale;

public final class LocaleUtils {
    public static final Locale ROMANIAN = new Locale("ro", "RO");

    private LocaleUtils() {

    }

    public static ContextWrapper getLocalizedContext(Context context) {
        Configuration config = context.getResources().getConfiguration();
        Locale.setDefault(ROMANIAN);
        config.setLocale(ROMANIAN);
        return new ContextWrapper(context.createConfigurationContext(config));
    }
}
