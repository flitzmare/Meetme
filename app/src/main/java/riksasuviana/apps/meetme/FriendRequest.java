package riksasuviana.apps.meetme;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class FriendRequest extends AppCompatActivity {

    @BindView(R.id.rvrequest) RecyclerView rv;

    ArrayList<String> s = new ArrayList<>();

    List<FriendProfile> list = new ArrayList<>();

    String key;

    DatabaseReference ref, fl;

    FriendRequestAdapter adp;

    @BindView(R.id.reqtv) TextView reqtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);
        ButterKnife.bind(this);

        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(lm);

        adp = new FriendRequestAdapter(list);

        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        key = pref.getString("key", "");

        fl = FirebaseDatabase.getInstance().getReference().child("profiles");

        ref = FirebaseDatabase.getInstance().getReference().child("profiles").child(key).child("friendrequest");

        if(s.isEmpty() && list.isEmpty()) {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    adp.notifyDataSetChanged();

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
                    adp.notifyDataSetChanged();

                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        for (int i = 0; i < s.size(); i++) {
                            if (dsp.getKey().equals(s.get(i))) {
                                FriendProfile p = new FriendProfile(dsp.child("name").getValue(String.class), dsp.child("photo").getValue(String.class), dsp.getKey());
                                list.add(p);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            reqtv.setText("is empty");
        }else{
            reqtv.setText("not");
        }
        rv.setAdapter(adp);
    }
}
