package me.vinsce.android.wear.remote.grpc;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.vinsce.remote.server.proto.CommandProto;
import me.vinsce.remote.server.proto.CommandServiceGrpc;

@RequiredArgsConstructor
public class GrpcLiveDataClient {
    protected static final String LOG_TAG = GrpcLiveDataClient.class.getSimpleName();

    protected final ManagedChannel channel;
    protected final Executor executor = Executors.newSingleThreadExecutor();
    protected boolean shutdownOnExecutionComplete = false;

    public GrpcLiveDataClient(String address, int port) {
        channel = ManagedChannelBuilder.forAddress(address, port).usePlaintext().build();
        shutdownOnExecutionComplete = true;
    }

    protected <T> void handleLiveData(MutableLiveData<GrpcResponse<T>> responseLiveData, ListenableFuture<T> responseFuture) {
        responseFuture.addListener(() -> {
            if (responseFuture.isDone()) {
                try {
                    T response = responseFuture.get();
                    Log.v(LOG_TAG, "Received response: " + response);
                    responseLiveData.postValue(GrpcResponse.success(response));
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Error getting response from grpc future", e);
                    responseLiveData.postValue(GrpcResponse.failure(e));
                }

                if (shutdownOnExecutionComplete) {
                    closeChannel();
                }
            }
        }, executor);
    }

    protected void closeChannel() {
        try {
            channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class GrpcResponse<T> {
        private final boolean success;
        private final Exception exception;
        private final T response;

        static <T> GrpcResponse<T> success(T response) {
            return new GrpcResponse<>(true, null, response);
        }

        static <T> GrpcResponse<T> failure(Exception exception) {
            return new GrpcResponse<>(false, exception, null);
        }
    }
}
