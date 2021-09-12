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

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> moveMouse(float deltaX, float deltaY) {
        CommandServiceGrpc.CommandServiceFutureStub stub = CommandServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        CommandProto.MouseMoveCommand request = CommandProto.MouseMoveCommand.newBuilder().setDeltaX(deltaX).setDeltaY(deltaY).build();
        handleLiveData(responseLiveData, stub.moveMouse(request));

        return responseLiveData;
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> clickMouse(boolean single) {
        CommandServiceGrpc.CommandServiceFutureStub stub = CommandServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        CommandProto.MouseClickCommand request = CommandProto.MouseClickCommand.newBuilder().setSingleTap(single).build();
        handleLiveData(responseLiveData, stub.clickMouse(request));

        return responseLiveData;
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> mouseEvent(CommandProto.MouseEventCommand.EventType eventType) {
        CommandServiceGrpc.CommandServiceFutureStub stub = CommandServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        CommandProto.MouseEventCommand request = CommandProto.MouseEventCommand.newBuilder().setEventType(eventType).build();
        handleLiveData(responseLiveData, stub.mouseEvent(request));

        return responseLiveData;
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> powerAction(CommandProto.PowerCommand.PowerAction powerAction) {
        CommandServiceGrpc.CommandServiceFutureStub stub = CommandServiceGrpc.newFutureStub(channel);

        MutableLiveData<GrpcResponse<CommandProto.GenericCommandResponse>> responseLiveData = new MutableLiveData<>();

        CommandProto.PowerCommand request = CommandProto.PowerCommand.newBuilder().setAction(powerAction).build();
        handleLiveData(responseLiveData, stub.runPowerCommand(request));

        return responseLiveData;
    }
}
