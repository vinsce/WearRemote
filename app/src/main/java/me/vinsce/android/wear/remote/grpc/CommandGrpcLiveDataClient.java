package me.vinsce.android.wear.remote.grpc;

import androidx.lifecycle.LiveData;

import io.grpc.ManagedChannel;
import me.vinsce.remote.server.proto.CommandProto;
import me.vinsce.remote.server.proto.CommandServiceGrpc;

public class CommandGrpcLiveDataClient extends GrpcLiveDataClient {
    private final CommandServiceGrpc.CommandServiceFutureStub stub;

    public CommandGrpcLiveDataClient(String address, int port) {
        super(address, port);
        stub = CommandServiceGrpc.newFutureStub(this.channel);
    }

    public CommandGrpcLiveDataClient(ManagedChannel channel) {
        super(channel);
        stub = CommandServiceGrpc.newFutureStub(this.channel);
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> sendMessage(String message) {
        CommandProto.GenericCommandRequest request = CommandProto.GenericCommandRequest.newBuilder().setCommand(message).build();
        return handleLiveData(stub.command(request));
    }
}
