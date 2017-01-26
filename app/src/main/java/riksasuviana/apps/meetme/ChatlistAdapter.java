package riksasuviana.apps.meetme;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by riksasuviana on 24/01/17.
 */

public class ChatlistAdapter extends RecyclerView.Adapter<ChatlistAdapter.ViewHolder> {
    private List<FriendProfile> list;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, myphoto;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            myphoto = (TextView) view.findViewById(R.id.photo);
        }
    }

    public ChatlistAdapter(List<FriendProfile> list) {
        this.list = list;
    }

    @Override
    public ChatlistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forfriendlist, parent, false);
        return new ChatlistAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChatlistAdapter.ViewHolder holder, int position) {
        final FriendProfile p = list.get(position);
        holder.name.setText(p.getName());
        holder.myphoto.setText(p.getPhoto());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), ChatActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}