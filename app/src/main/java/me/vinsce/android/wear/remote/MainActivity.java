package me.vinsce.android.wear.remote;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import me.vinsce.android.wear.remote.databinding.ActivityMainBinding;

public class MainActivity extends Activity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;
    private ConfigurationStore configurationStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adjustInset();
        loadConfiguration();

        binding.btnRemoteSendMessage.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SendMessageActivity.class)));
        binding.btnRemoteTouchpad.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TouchpadActivity.class)));
    }

    private static final double FACTOR = 0.146467f;

    private void adjustInset() {
        // https://www.deviantdev.com/journal/android-wear-scrollable-boxinsetlayout
        if (getApplicationContext().getResources().getConfiguration().isScreenRound()) {
            int inset = (int) (FACTOR * Resources.getSystem().getDisplayMetrics().widthPixels);
            binding.getRoot().setPadding(inset, inset, inset, inset);
        }
    }

    private void loadConfiguration() {
        configurationStore = new ConfigurationStore(this);

        // Update view with saved configuration
        binding.inServerAddress.setText(configurationStore.getServerAddress());
        binding.inServerPort.setText(String.valueOf(configurationStore.getServerPort()));

        // Add listeners to EditTexts to update configuration
        binding.inServerAddress.addTextChangedListener((AfterTextChangedListener) editable -> configurationStore.setServerAddress(editable.toString()));
        binding.inServerPort.addTextChangedListener((AfterTextChangedListener) editable -> configurationStore.setServerPort(Integer.parseInt(editable.toString())));
    }
}