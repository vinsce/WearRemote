package me.vinsce.android.wear.remote.remotes;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import me.vinsce.android.wear.remote.R;
import me.vinsce.android.wear.remote.SendMessageActivity;
import me.vinsce.android.wear.remote.TouchpadActivity;

public class RemotesRegistry {
    @Getter
    private final List<RemoteDefinition> remotes = new ArrayList<>();

    private RemotesRegistry() {
        remotes.add(new RemoteDefinition(R.string.remote_touchpad, R.drawable.ic_remote_touchpad_24, TouchpadActivity.class));
        remotes.add(new RemoteDefinition(R.string.remote_send_message, R.drawable.ic_remote_send_message_24, SendMessageActivity.class));
    }

    private static final RemotesRegistry instance = new RemotesRegistry();

    public static RemotesRegistry getInstance() {
        return instance;
    }
}
