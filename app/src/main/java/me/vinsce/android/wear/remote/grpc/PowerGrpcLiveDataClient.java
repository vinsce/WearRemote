package me.vinsce.android.wear.remote.grpc;

import androidx.lifecycle.LiveData;

import io.grpc.ManagedChannel;
import me.vinsce.remote.server.proto.CommandProto;
import me.vinsce.remote.server.proto.PowerProto;
import me.vinsce.remote.server.proto.PowerServiceGrpc;

@SuppressWarnings("UnusedReturnValue")
public class PowerGrpcLiveDataClient extends GrpcLiveDataClient {
    private final PowerServiceGrpc.PowerServiceFutureStub stub;

    public PowerGrpcLiveDataClient(String address, int port) {
        super(address, port);
        stub = PowerServiceGrpc.newFutureStub(this.channel);
    }

    public PowerGrpcLiveDataClient(ManagedChannel channel) {
        super(channel);
        stub = PowerServiceGrpc.newFutureStub(this.channel);
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> powerAction(PowerProto.PowerCommandRequest.PowerAction powerAction) {
        PowerProto.PowerCommandRequest request = PowerProto.PowerCommandRequest.newBuilder().setAction(powerAction).build();
        return handleLiveData(stub.powerCommand(request));
    }
}
