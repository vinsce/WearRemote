package me.vinsce.android.wear.remote;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.core.view.GestureDetectorCompat;

import io.grpc.ManagedChannelBuilder;
import me.vinsce.android.wear.remote.databinding.ActivityTouchpadBinding;
import me.vinsce.android.wear.remote.grpc.CommandGrpcLiveDataClient;
import me.vinsce.remote.server.proto.CommandProto;

public class TouchpadActivity extends Activity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private static final String LOG_TAG = SendMessageActivity.class.getSimpleName();

    private CommandGrpcLiveDataClient commandGrpcLiveDataClient;
    private GestureDetectorCompat gestureDetector;
    private float scaleFactor;

    me.vinsce.android.wear.remote.databinding.ActivityTouchpadBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTouchpadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ConfigurationStore configurationStore = new ConfigurationStore(this);

        scaleFactor = configurationStore.getScaleFactor();

        commandGrpcLiveDataClient = new CommandGrpcLiveDataClient(
                ManagedChannelBuilder.forAddress(
                        configurationStore.getServerAddress(),
                        configurationStore.getServerPort()
                ).usePlaintext().build()
        );

        gestureDetector = new GestureDetectorCompat(this, this);
        gestureDetector.setOnDoubleTapListener(this);

        binding.btnClose.setOnClickListener(v -> finish());
    }

    private float curX = -1;
    private float curY = -1;

    private boolean ignoreUp = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.gestureDetector.onTouchEvent(event)) {
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d(LOG_TAG, "ACTION_DOWN");
            // Initialization
            curX = event.getX();
            curY = event.getY();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            Log.d(LOG_TAG, "ACTION_MOVE");
            float deltaX = scaleFactor * (event.getX() - curX);
            float deltaY = scaleFactor * (event.getY() - curY);
            curX = event.getX();
            curY = event.getY();
            commandGrpcLiveDataClient.moveMouse(deltaX, deltaY);
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d(LOG_TAG, "ACTION_UP");
            if (ignoreUp)
                ignoreUp = false;
            else
                commandGrpcLiveDataClient.mouseEvent(CommandProto.MouseEventCommand.EventType.SINGLE_TAP_UP);
            return true;
        } else if (event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
            int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            Log.d(LOG_TAG, "ACTION_POINTER_UP[" + pointerIndex + "]");
            if (pointerIndex == 1) { // Two finger
                // TODO[improve]
                commandGrpcLiveDataClient.mouseEvent(CommandProto.MouseEventCommand.EventType.RIGHT_TAP);
                ignoreUp = true;
                return true;
            }
        }

        return super.onTouchEvent(event);
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        commandGrpcLiveDataClient.mouseEvent(CommandProto.MouseEventCommand.EventType.SINGLE_TAP);
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        commandGrpcLiveDataClient.mouseEvent(CommandProto.MouseEventCommand.EventType.DOUBLE_TAP);
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {}

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        commandGrpcLiveDataClient.mouseEvent(CommandProto.MouseEventCommand.EventType.SINGLE_TAP_UP);
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        commandGrpcLiveDataClient.mouseEvent(CommandProto.MouseEventCommand.EventType.LONG_PRESS);
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
