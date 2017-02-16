package riksasuviana.apps.meetme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by riksasuviana on 21/01/17.
 */

public class ProfileDialog extends Dialog implements View.OnClickListener{

    Button chat, meet;

    TextView namedialog, photodialog;

    String name, photo, mykey, key, chatkey;

    Activity a;

    DatabaseReference ref, chatref,tref;

    public ProfileDialog(Activity a, String name, String photo, String key){
        super(a);
        this.a = a;
        this.name = name;
        this.photo = photo;
        this.key = key;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profiledialog);

        namedialog = (TextView)findViewById(R.id.namedialog);
        namedialog.setText(name);
        photodialog = (TextView)findViewById(R.id.photodialog);
        photodialog.setText(photo);

        chat = (Button)findViewById(R.id.chat);
        chat.setOnClickListener(this);
        meet = (Button)findViewById(R.id.meet);
        meet.setOnClickListener(this);

        SharedPreferences pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        mykey = pref.getString("key", "");
        ref = FirebaseDatabase.getInstance().getReference().child("profiles");
        tref = FirebaseDatabase.getInstance().getReference().child("profiles");
        chatref = FirebaseDatabase.getInstance().getReference().child("chat");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chat:
                ref.child(mykey).child("friends").child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.hasChildren()){
                            ref.child(mykey).child("friends").child(key).push().addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot ds) {
                                    chatkey = ds.getKey();
                                    ref.child(mykey).child("friends").child(key).child(chatkey).setValue("true");
                                    ref.child(key).child("friends").child(mykey).child(chatkey).setValue("true");

                                    Intent i = new Intent(getContext(), ChatActivity.class);
                                    i.putExtra("key", key);
                                    i.putExtra("chatkey", chatkey);
                                    getContext().startActivity(i);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }else{
                            tref.child(mykey).child("friends").child(key).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot ds) {
                                    for(DataSnapshot d : ds.getChildren()){
                                    chatkey = d.getKey();
                                    }
                                    Intent i = new Intent(getContext(), ChatActivity.class);
                                    i.putExtra("key", key);
                                    i.putExtra("chatkey", chatkey);
                                    getContext().startActivity(i);
//                                    photodialog.setText(chatkey);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

//                ref.child(mykey).child("friends").child(key).push().addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if(!dataSnapshot.hasChildren()) {
//                            chatkey = dataSnapshot.getKey();
//                            photodialog.setText(chatkey);
//                            ref.child(mykey).child("friends").child(key).child(chatkey).setValue("true");
//                            ref.child(key).child("friends").child(mykey).child(chatkey).setValue("true");
////                        chatref.child(chatkey).setValue("false");
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });

//                Intent i = new Intent(getContext(), ChatActivity.class);
//                i.putExtra("key", key);
//                i.putExtra("chatkey", chatkey);
//                getContext().startActivity(i);

                break;
            case R.id.meet:
                ref.child(key).child("meetrequest").setValue(mykey);
                Toast.makeText(getContext(), "Meet Request Sended", Toast.LENGTH_LONG).show();
                dismiss();
                break;
            default:
                break;
        }
    }
}
