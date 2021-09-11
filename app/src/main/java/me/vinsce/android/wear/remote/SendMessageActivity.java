package me.vinsce.android.wear.remote;

import android.app.Activity;
import android.os.Bundle;

import androidx.lifecycle.LiveData;

import me.vinsce.android.wear.remote.databinding.ActivitySendMessageBinding;
import me.vinsce.android.wear.remote.grpc.CommandGrpcLiveDataClient;
import me.vinsce.remote.server.proto.CommandProto;

public class SendMessageActivity extends Activity {
    private static final String LOG_TAG = SendMessageActivity.class.getSimpleName();

    private ActivitySendMessageBinding binding;
    private ConfigurationStore configurationStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configurationStore = new ConfigurationStore(this);

        binding = ActivitySendMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSend.setOnClickListener(v -> {
            LiveData<CommandGrpcLiveDataClient.GrpcResponse<CommandProto.GenericCommandResponse>> sendMessageLiveData = new CommandGrpcLiveDataClient(
                    configurationStore.getServerAddress(),
                    configurationStore.getServerPort()
            ).sendMessage(binding.inMessage.getText().toString());

            binding.btnSend.setEnabled(false);

            sendMessageLiveData.observeForever(response -> {
                if (response != null) {
                    binding.response.setText(response.isSuccess() ? String.valueOf(response.getResponse()) : "Error: " + response.getException().getMessage());
                    binding.btnSend.setEnabled(true);
                }
            });
        });
    }
}
