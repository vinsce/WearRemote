package me.vinsce.android.wear.remote;

import android.text.TextWatcher;

@FunctionalInterface
public interface AfterTextChangedListener extends TextWatcher {
    @Override
    default void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    default void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
}
