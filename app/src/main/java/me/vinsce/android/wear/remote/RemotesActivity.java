package me.vinsce.android.wear.remote;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.wear.widget.WearableLinearLayoutManager;

import me.vinsce.android.wear.remote.databinding.ActivityMainBinding;
import me.vinsce.android.wear.remote.databinding.ActivityRemotesBinding;
import me.vinsce.android.wear.remote.remotes.RemoteDefinition;
import me.vinsce.android.wear.remote.remotes.RemotesRegistry;

public class RemotesActivity extends Activity {
    private static final String LOG_TAG = RemotesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRemotesBinding binding = ActivityRemotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerView.setLayoutManager(new WearableLinearLayoutManager(this));
        binding.recyclerView.setAdapter(new RemotesAdapter());
        binding.recyclerView.setEdgeItemsCenteringEnabled(true);
    }

    static class RemoteVH extends RecyclerView.ViewHolder {
        private final ImageView icon;
        private final TextView title;

        public RemoteVH(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.icon = itemView.findViewById(R.id.icon);
        }
    }

    static class RemotesAdapter extends RecyclerView.Adapter<RemoteVH> {

        @NonNull
        @Override
        public RemoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RemoteVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_remote, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RemoteVH holder, int position) {
            final RemoteDefinition remote = RemotesRegistry.getInstance().getRemotes().get(position);
            holder.title.setText(remote.getTitleResId());
            holder.icon.setImageResource(remote.getIconResId());
            holder.itemView.setOnClickListener(view -> view.getContext().startActivity(new Intent(view.getContext(), remote.getActivityClass())));
        }

        @Override
        public int getItemCount() {
            return RemotesRegistry.getInstance().getRemotes().size();
        }
    }
}