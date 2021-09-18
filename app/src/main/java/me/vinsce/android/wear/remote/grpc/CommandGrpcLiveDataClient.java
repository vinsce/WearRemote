package me.vinsce.android.wear.remote.grpc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.protobuf.Empty;

import io.grpc.ManagedChannel;
import me.vinsce.remote.server.proto.CommandProto;
import me.vinsce.remote.server.proto.CommandServiceGrpc;
import me.vinsce.remote.server.proto.MediaServiceGrpc;
import me.vinsce.remote.server.proto.MouseProto;
import me.vinsce.remote.server.proto.MouseServiceGrpc;
import me.vinsce.remote.server.proto.PowerProto;
import me.vinsce.remote.server.proto.PowerServiceGrpc;

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

        CommandProto.GenericCommandRequest request = CommandProto.GenericCommandRequest.newBuilder().setCommand(message).build();
        handleLiveData(responseLiveData, stub.command(request));

        return responseLiveData;
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> moveMouse(float deltaX, float deltaY) {
        MouseServiceGrpc.MouseServiceFutureStub stub = MouseServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        MouseProto.MouseMoveRequest request = MouseProto.MouseMoveRequest.newBuilder().setDeltaX(deltaX).setDeltaY(deltaY).build();
        handleLiveData(responseLiveData, stub.moveMouse(request));

        return responseLiveData;
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> mouseEvent(MouseProto.MouseEventRequest.EventType eventType) {
        MouseServiceGrpc.MouseServiceFutureStub stub = MouseServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        MouseProto.MouseEventRequest request = MouseProto.MouseEventRequest.newBuilder().setEventType(eventType).build();
        handleLiveData(responseLiveData, stub.mouseEvent(request));

        return responseLiveData;
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> powerAction(PowerProto.PowerCommandRequest.PowerAction powerAction) {
        PowerServiceGrpc.PowerServiceFutureStub stub = PowerServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        PowerProto.PowerCommandRequest request = PowerProto.PowerCommandRequest.newBuilder().setAction(powerAction).build();
        handleLiveData(responseLiveData, stub.powerCommand(request));

        return responseLiveData;
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> volumeUp() {
        MediaServiceGrpc.MediaServiceFutureStub stub = MediaServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        handleLiveData(responseLiveData, stub.volumeUp(Empty.newBuilder().build()));

        return responseLiveData;
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> volumeDown() {
        MediaServiceGrpc.MediaServiceFutureStub stub = MediaServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        handleLiveData(responseLiveData, stub.volumeDown(Empty.newBuilder().build()));

        return responseLiveData;
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> volumeMute() {
        MediaServiceGrpc.MediaServiceFutureStub stub = MediaServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        handleLiveData(responseLiveData, stub.mute(Empty.newBuilder().build()));

        return responseLiveData;
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> mediaPlayPause() {
        MediaServiceGrpc.MediaServiceFutureStub stub = MediaServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        handleLiveData(responseLiveData, stub.playPause(Empty.newBuilder().build()));

        return responseLiveData;
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> mediaNext() {
        MediaServiceGrpc.MediaServiceFutureStub stub = MediaServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        handleLiveData(responseLiveData, stub.mediaNext(Empty.newBuilder().build()));

        return responseLiveData;
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> mediaPrevious() {
        MediaServiceGrpc.MediaServiceFutureStub stub = MediaServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        handleLiveData(responseLiveData, stub.mediaPrev(Empty.newBuilder().build()));

        return responseLiveData;
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> mediaStop() {
        MediaServiceGrpc.MediaServiceFutureStub stub = MediaServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        handleLiveData(responseLiveData, stub.stop(Empty.newBuilder().build()));

        return responseLiveData;
    }
}
