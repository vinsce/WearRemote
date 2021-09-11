package me.vinsce.android.wear.remote.remotes;

import android.app.Activity;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoteDefinition {
    @StringRes
    private Integer titleResId;

    @DrawableRes
    private Integer iconResId;

    private Class<? extends Activity> activityClass;
}
