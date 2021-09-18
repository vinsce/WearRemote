package me.vinsce.android.wear.remote;

import android.app.Activity;
import android.os.Bundle;

import me.vinsce.android.wear.remote.databinding.ActivityRemotePowerBinding;
import me.vinsce.android.wear.remote.grpc.PowerGrpcLiveDataClient;
import me.vinsce.remote.server.proto.PowerProto;

public class PowerActionsActivity extends Activity {
    private static final String LOG_TAG = PowerActionsActivity.class.getSimpleName();

    private ConfigurationStore configurationStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRemotePowerBinding binding = ActivityRemotePowerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurationStore = new ConfigurationStore(this);

        binding.btnShutdown.setOnClickListener(v -> client().powerAction(PowerProto.PowerCommandRequest.PowerAction.SHUTDOWN));
        binding.btnRestart.setOnClickListener(v -> client().powerAction(PowerProto.PowerCommandRequest.PowerAction.RESTART));
        binding.btnSleep.setOnClickListener(v -> client().powerAction(PowerProto.PowerCommandRequest.PowerAction.SLEEP));
    }

    public PowerGrpcLiveDataClient client() {
        // Create a new client that will close the channel on execution complete
        return new PowerGrpcLiveDataClient(
                configurationStore.getServerAddress(),
                configurationStore.getServerPort()
        );
    }
}