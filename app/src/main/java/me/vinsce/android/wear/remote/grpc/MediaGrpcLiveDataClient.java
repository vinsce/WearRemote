package me.vinsce.android.wear.remote.grpc;

import androidx.lifecycle.LiveData;

import com.google.protobuf.Empty;

import io.grpc.ManagedChannel;
import me.vinsce.remote.server.proto.CommandProto;
import me.vinsce.remote.server.proto.MediaServiceGrpc;

@SuppressWarnings("UnusedReturnValue")
public class MediaGrpcLiveDataClient extends GrpcLiveDataClient {
    private final MediaServiceGrpc.MediaServiceFutureStub stub;

    public MediaGrpcLiveDataClient(String address, int port) {
        super(address, port);
        stub = MediaServiceGrpc.newFutureStub(this.channel);
    }

    public MediaGrpcLiveDataClient(ManagedChannel channel) {
        super(channel);
        stub = MediaServiceGrpc.newFutureStub(this.channel);
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> volumeUp() {
        return handleLiveData(stub.volumeUp(Empty.newBuilder().build()));
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> volumeDown() {
        return handleLiveData(stub.volumeDown(Empty.newBuilder().build()));
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> volumeMute() {
        return handleLiveData(stub.mute(Empty.newBuilder().build()));
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> mediaPlayPause() {
        return handleLiveData(stub.playPause(Empty.newBuilder().build()));
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> mediaNext() {
        return handleLiveData(stub.mediaNext(Empty.newBuilder().build()));
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> mediaPrevious() {
        return handleLiveData(stub.mediaPrev(Empty.newBuilder().build()));
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> mediaStop() {
        return handleLiveData(stub.stop(Empty.newBuilder().build()));
    }
}
