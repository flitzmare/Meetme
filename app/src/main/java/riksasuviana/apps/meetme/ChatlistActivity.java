package riksasuviana.apps.meetme;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatlistActivity extends Fragment {

    List<ChatList> prof = new ArrayList<>();

    ArrayList<String> list = new ArrayList<>();

    ArrayList<String> friendkey = new ArrayList<>();

    DatabaseReference myref, fl, profileref;

    String key;

    int i;

//    @BindView(R.id.tvchatlist) TextView tv;

//    @BindView(R.id.rvc) RecyclerView rvchatlist;

    String nantihapus;

    ChatlistAdapter adp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(getActivity());

        View rootView = inflater.inflate(R.layout.activity_chatlist, container, false);

        RecyclerView rvchatlist = (RecyclerView)rootView.findViewById(R.id.rvc);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());

        rvchatlist.setLayoutManager(lm);

        adp = new ChatlistAdapter(prof);

//        ChatList c = new ChatList("asd", "qwe");
//        prof.add(c);

        rvchatlist.setAdapter(adp);


        SharedPreferences pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        key = pref.getString("key", "");

        myref = FirebaseDatabase.getInstance().getReference().child("profiles").child(key).child("friends");

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendkey.clear();
                list.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if(dsp.hasChildren()) {
                        for (DataSnapshot ds : dsp.getChildren()) {
                            friendkey.add(dsp.getKey());
                            list.add(ds.getKey());
                            Log.wtf("FRIENDKEY ADDED : ",dsp.getKey());
                            Log.wtf("DSGETKEY ADDED : ", ds.getKey());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        profileref = FirebaseDatabase.getInstance().getReference().child("profiles");
        profileref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (int j = 0; j < friendkey.size(); j++) {
                        if(ds.getKey().equals(friendkey.get(j))){
                            ChatList c = new ChatList(ds.child("photo").getValue(String.class), ds.child("name").getValue(String.class), list.get(j));
                            prof.add(c);
                            adp.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return rootView;
    }

}
