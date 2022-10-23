package ro.azs.kidsdevelopment.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

public class BindingMethods {

    @BindingAdapter("show")
    public static void show(@NonNull View view, boolean shouldBeVisible) {
        if (shouldBeVisible) {
            if (view.getVisibility() != View.VISIBLE) {
                view.setVisibility(View.VISIBLE);
            }
        } else {
            if (view.getVisibility() != View.GONE) {
                view.setVisibility(View.GONE);
            }
        }
    }

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter("showSeparator")
    public static void setRecyclerViewSeparator(@NonNull RecyclerView recyclerView, boolean show) {
        if (show) {
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);
        } else {
            for (int i = 0; i < recyclerView.getItemDecorationCount(); i++) {
                recyclerView.removeItemDecorationAt(i);
            }
        }
    }

    @BindingAdapter({"android:layout_marginTop"})
    public static void setLayoutBindMarginMarginTop(@NonNull View view, float marginTop) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(p.leftMargin, (int) marginTop, p.rightMargin, p.bottomMargin);
            view.requestLayout();
        }
    }

    @BindingAdapter({"android:layout_marginStart"})
    public static void setLayoutBindMarginMarginStart(@NonNull View view, float marginStart) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins((int) marginStart, p.topMargin, p.rightMargin, p.bottomMargin);
            view.requestLayout();
        }
    }

    @BindingAdapter({"android:layout_marginBottom"})
    public static void setLayoutBindMarginMarginBottom(@NonNull View view, float marginBottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(p.leftMargin, p.topMargin, p.rightMargin, (int) marginBottom);
            view.requestLayout();
        }
    }

    @BindingAdapter({"android:layout_marginEnd"})
    public static void setLayoutBindMarginMarginEnd(@NonNull View view, float marginEnd) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(p.leftMargin, p.topMargin, (int) marginEnd, p.bottomMargin);
            view.requestLayout();
        }
    }
}
