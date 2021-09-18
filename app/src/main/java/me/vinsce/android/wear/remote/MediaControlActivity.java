package me.vinsce.android.wear.remote;

import android.app.Activity;
import android.os.Bundle;

import me.vinsce.android.wear.remote.databinding.ActivityRemoteMediaBinding;
import me.vinsce.android.wear.remote.databinding.ActivityRemotePowerBinding;
import me.vinsce.android.wear.remote.grpc.CommandGrpcLiveDataClient;
import me.vinsce.remote.server.proto.CommandProto;

public class MediaControlActivity extends Activity {
    private static final String LOG_TAG = MediaControlActivity.class.getSimpleName();

    private ConfigurationStore configurationStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRemoteMediaBinding binding = ActivityRemoteMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurationStore = new ConfigurationStore(this);

        binding.btnVolumeUp.setOnClickListener(v -> client().volumeUp());
        binding.btnVolumeDown.setOnClickListener(v -> client().volumeDown());
        binding.btnVolumeMute.setOnClickListener(v -> client().volumeMute());

        binding.btnMediaNext.setOnClickListener(v -> client().mediaNext());
        binding.btnMediaPrevious.setOnClickListener(v -> client().mediaPrevious());
        binding.btnMediaPlay.setOnClickListener(v -> client().mediaPlayPause());
        binding.btnMediaStop.setOnClickListener(v -> client().mediaStop());
    }

    public CommandGrpcLiveDataClient client() {
        // Create a new client that will close the channel on execution complete
        return new CommandGrpcLiveDataClient(
                configurationStore.getServerAddress(),
                configurationStore.getServerPort()
        );
    }
}