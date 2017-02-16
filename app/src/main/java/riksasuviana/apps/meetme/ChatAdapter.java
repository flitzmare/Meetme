package riksasuviana.apps.meetme;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by riksasuviana on 24/01/17.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    public static final int VIEW_TYPE_NUURANG = 1;
    public static final int VIEW_TYPE_NUBATUR = 2;

    private List<Integer> listviewType;
    private List<InputChat> list;

    public ChatAdapter(List<Integer> listviewType, List<InputChat> list) {
        this.listviewType = listviewType;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.forfriendlist, parent, false);
//        return new ChatAdapter.ViewHolder(itemView);

        View view;
        if(viewType == VIEW_TYPE_NUURANG){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msgme, null);
            return new SisiUrang(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msgyou, null);
            return new SisiManeh(view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = listviewType.get(position);

        InputChat p = list.get(position);

        if(viewType == VIEW_TYPE_NUURANG){
            SisiUrang urang = (SisiUrang)holder;
            urang.msg.setText(p.getMsg());
        }else{
            SisiManeh maneh = (SisiManeh)holder;
            maneh.yourmsg.setText(p.getMsg());
        }

    }

    @Override
    public int getItemCount() {
        return listviewType.size();
//        return 0;
    }

    public int getItemViewType(int position){
        return listviewType.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
        }
    }

    public class SisiUrang extends ViewHolder {
        public TextView msg;

        public SisiUrang(View itemView) {
            super(itemView);
            msg = (TextView)itemView.findViewById(R.id.mychattv);
        }
    }

    public class SisiManeh extends ViewHolder{
        public TextView yourmsg;

        public SisiManeh(View view) {
            super(view);
            yourmsg = (TextView)view.findViewById(R.id.yourchattv);
        }
    }
}