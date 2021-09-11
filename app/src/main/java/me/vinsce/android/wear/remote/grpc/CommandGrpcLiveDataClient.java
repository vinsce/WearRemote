package me.vinsce.android.wear.remote.grpc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.grpc.ManagedChannel;
import me.vinsce.remote.server.proto.CommandProto;
import me.vinsce.remote.server.proto.CommandServiceGrpc;

public class CommandGrpcLiveDataClient extends GrpcLiveDataClient {
    private static final String LOG_TAG = CommandGrpcLiveDataClient.class.getSimpleName();

    public CommandGrpcLiveDataClient(String address, int port) {
        super(address, port);
    }

    public CommandGrpcLiveDataClient(ManagedChannel channel) {
        super(channel);
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> sendMessage(String message) {
        CommandServiceGrpc.CommandServiceFutureStub stub = CommandServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        CommandProto.GenericCommand request = CommandProto.GenericCommand.newBuilder().setCommand(message).build();
        handleLiveData(responseLiveData, stub.runCommand(request));

        return responseLiveData;
    }
}
