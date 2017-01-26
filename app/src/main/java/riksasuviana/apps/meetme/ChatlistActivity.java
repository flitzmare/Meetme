package riksasuviana.apps.meetme;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    List<FriendProfile> prof = new ArrayList<>();

    ArrayList<String> list = new ArrayList<>();

    DatabaseReference ref, fl;

    String key;

    int i;

    @BindView(R.id.tvchatlist) TextView tv;

//    @BindView(R.id.rvchatlist) RecyclerView rvchatlist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(getActivity());

        ChatlistAdapter adp = new ChatlistAdapter(prof);

        View rootView = inflater.inflate(R.layout.activity_chatlist, container, false);

        RecyclerView rvchatlist = (RecyclerView)rootView.findViewById(R.id.rvchatlist);

        SharedPreferences pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        key = pref.getString("key", "");

        fl = FirebaseDatabase.getInstance().getReference().child("profiles");

        ref = FirebaseDatabase.getInstance().getReference().child("profiles").child(key).child("friends");

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rvchatlist.setLayoutManager(lm);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){

//                    if(!ds.getValue(String.class).equals("0")){
//                        list.add(ds.getKey());
//                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dsp : dataSnapshot.getChildren()) {
                    for (i = 0; i < list.size(); i++) {
                        if(dsp.getKey().equals(list.get(i))){
                            FriendProfile p = new FriendProfile(dsp.child("name").getValue(String.class), dsp.child("photo").getValue(String.class), dsp.getKey());
                            prof.add(p);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        rvchatlist.setAdapter(adp);

        return rootView;
    }

}
