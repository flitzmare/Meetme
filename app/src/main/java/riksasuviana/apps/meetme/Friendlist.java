package riksasuviana.apps.meetme;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Friendlist extends Fragment {

    ArrayList<String> list = new ArrayList<String>();

    List<FriendProfile> profileList = new ArrayList<>();

    DatabaseReference ref;
    DatabaseReference fl;

    String key;

    int i;

    @BindView(R.id.rv) RecyclerView rv;

    MyAdapter adp;

    //friendrequest

    ArrayList<String> s = new ArrayList<>();

    List<FriendProfile> li = new ArrayList<>();

    DatabaseReference refRq;

    FriendRequestAdapter ad;

//    @BindView(R.id.refresh) SwipeRefreshLayout refresh;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ButterKnife.bind(getActivity());

        View rootView = inflater.inflate(R.layout.activity_friendlist, container, false);

        final SwipeRefreshLayout refresh = (SwipeRefreshLayout)rootView.findViewById(R.id.refresh);

        FriendProfile p = new FriendProfile("namanya", "yrdy", "123");
        profileList.add(p);

        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        rv.setLayoutManager(lm); //disini masalahnya

        adp = new MyAdapter(profileList);

        SharedPreferences pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        key = pref.getString("key", "");

        fl = FirebaseDatabase.getInstance().getReference().child("profiles");

        ref = FirebaseDatabase.getInstance().getReference().child("profiles").child(key).child("friends");

//        if(list.isEmpty()) {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        list.add(String.valueOf(dsp.getKey()));
                    }
//                t.setText("Size : "+list.size()+"\nIndex 0 : "+list.get(0));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
//        }

            fl.addListenerForSingleValueEvent(new ValueEventListener() { //ini masalah add auto
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    adp.notifyDataSetChanged();
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            for (i = 0; i < list.size(); i++) {
                                if (dsp.getKey().equals(list.get(i))) {
                                    FriendProfile p = new FriendProfile(dsp.child("name").getValue(String.class), dsp.child("photo").getValue(String.class), dsp.getKey());
                                    profileList.add(p);
                                }
                            }
                        }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        rv.setAdapter(adp);

        //friendrequest

        RecyclerView rvrequest = (RecyclerView)rootView.findViewById(R.id.rvrequest);

        LinearLayoutManager l = new LinearLayoutManager(getActivity());
        rvrequest.setLayoutManager(l);

        ad = new FriendRequestAdapter(li);

        refRq = FirebaseDatabase.getInstance().getReference().child("profiles").child(key).child("friendrequest");


            refRq.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ad.notifyDataSetChanged();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        s.add(String.valueOf(ds.getKey()));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            fl.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ad.notifyDataSetChanged();

                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        for (int i = 0; i < s.size(); i++) {
                            if (dsp.getKey().equals(s.get(i))) {
                                FriendProfile p = new FriendProfile(dsp.child("name").getValue(String.class), dsp.child("photo").getValue(String.class), dsp.getKey());
                                li.add(p);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                s.clear();
                li.clear();
                list.clear();
                profileList.clear();

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            list.add(String.valueOf(dsp.getKey()));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                fl.addListenerForSingleValueEvent(new ValueEventListener() { //ini masalah add auto
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        adp.notifyDataSetChanged();
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            for (i = 0; i < list.size(); i++) {
                                if (dsp.getKey().equals(list.get(i))) {
                                    FriendProfile p = new FriendProfile(dsp.child("name").getValue(String.class), dsp.child("photo").getValue(String.class), dsp.getKey());
                                    profileList.add(p);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                refRq.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ad.notifyDataSetChanged();

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            s.add(String.valueOf(ds.getKey()));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                fl.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ad.notifyDataSetChanged();

                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            for (int i = 0; i < s.size(); i++) {
                                if (dsp.getKey().equals(s.get(i))) {
                                    FriendProfile p = new FriendProfile(dsp.child("name").getValue(String.class), dsp.child("photo").getValue(String.class), dsp.getKey());
                                    li.add(p);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                ad.notifyDataSetChanged();
                adp.notifyDataSetChanged();
                refresh.setRefreshing(false);
            }
        });

        rvrequest.setAdapter(ad);

        return rootView;
    }

//    public void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//    }
}
