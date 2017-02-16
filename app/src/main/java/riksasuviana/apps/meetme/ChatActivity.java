package riksasuviana.apps.meetme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends AppCompatActivity {

    String chatkey, key, mykey;

    DatabaseReference ref,  chatref;

    @BindView(R.id.rvchat) RecyclerView rv;
//
    @BindView(R.id.chattext) EditText txt;

    List<Integer> listviewType;

    List<InputChat> listInput;

    InputChat in;

    ChatAdapter adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        chatkey = b.getString("chatkey");
//        key = b.getString("key");

//        txt.setText(chatkey+" asd");

        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        mykey = pref.getString("key", "");

        ref = FirebaseDatabase.getInstance().getReference().child("profiles");

        chatref = FirebaseDatabase.getInstance().getReference().child("chat").child(chatkey);

        listviewType = new ArrayList<>();
//        listviewType.add(ChatAdapter.VIEW_TYPE_NUURANG);

        listInput = new ArrayList<>();
//        InputChat c = new InputChat();
//        c.setMsg("Test Message");
//        c.setSender(mykey);
//        listInput.add(c);
//
//        c = new InputChat();
//        listviewType.add(ChatAdapter.VIEW_TYPE_NUBATUR);
//        c.setMsg("Opposite Message");
//        c.setSender("asd");
//        listInput.add(c);

        adp = new ChatAdapter(listviewType, listInput);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(adp);

        chatref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listviewType.clear();
                listInput.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.child("sender").getValue(String.class).equals(mykey)){
                        listviewType.add(ChatAdapter.VIEW_TYPE_NUURANG);
                        InputChat c =  new InputChat();
                        c.setMsg(ds.child("msg").getValue(String.class));
                        c.setSender(mykey);
                        listInput.add(c);
                    }else{
                        listviewType.add(ChatAdapter.VIEW_TYPE_NUBATUR);
                        InputChat c =  new InputChat();
                        c.setMsg(ds.child("msg").getValue(String.class));
                        c.setSender(mykey);
                        listInput.add(c);
                    }
                    adp.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        txt.setText(mykey);
    }

    @OnClick(R.id.sendbtn) void send(){

        InputChat input = new InputChat();
        input.setMsg(txt.getText().toString());
        input.setSender(mykey);
        listviewType.add(ChatAdapter.VIEW_TYPE_NUURANG);
        listInput.add(input);
        chatref.push().setValue(input);

    }
}
