package me.vinsce.android.wear.remote.grpc;

import androidx.lifecycle.LiveData;

import io.grpc.ManagedChannel;
import me.vinsce.remote.server.proto.CommandProto;
import me.vinsce.remote.server.proto.MouseProto;
import me.vinsce.remote.server.proto.MouseServiceGrpc;

@SuppressWarnings("UnusedReturnValue")
public class MouseGrpcLiveDataClient extends GrpcLiveDataClient {

    private final MouseServiceGrpc.MouseServiceFutureStub stub;

    public MouseGrpcLiveDataClient(String address, int port) {
        super(address, port);
        stub = MouseServiceGrpc.newFutureStub(this.channel);
    }

    public MouseGrpcLiveDataClient(ManagedChannel channel) {
        super(channel);
        stub = MouseServiceGrpc.newFutureStub(this.channel);
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> moveMouse(float deltaX, float deltaY) {
        MouseProto.MouseMoveRequest request = MouseProto.MouseMoveRequest.newBuilder().setDeltaX(deltaX).setDeltaY(deltaY).build();
        return handleLiveData(stub.moveMouse(request));
    }

    public LiveData<GrpcResponse<CommandProto.GenericCommandResponse>> mouseEvent(MouseProto.MouseEventRequest.EventType eventType) {
        MouseProto.MouseEventRequest request = MouseProto.MouseEventRequest.newBuilder().setEventType(eventType).build();
        return handleLiveData(stub.mouseEvent(request));
    }

}
