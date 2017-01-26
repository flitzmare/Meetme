package riksasuviana.apps.meetme;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddfriendActivity extends AppCompatActivity {

    DatabaseReference db;

    String searchkey;

    String mykey;

    @BindView(R.id.textsearch) EditText text;
    @BindView(R.id.friendtv) TextView tv;
    @BindView(R.id.textname) TextView name;
    @BindView(R.id.textemail) TextView email;

    @OnClick(R.id.btnsearch) void search(){

        db.orderByChild("email").equalTo(text.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    searchkey = ds.getKey();
                    tv.setText(searchkey);
                    name.setText(ds.child("name").getValue(String.class));
                    email.setText(ds.child("email").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                tv.setText("CANCELED");
            }
        });
    }

    @OnClick(R.id.btnadd)void addfriend(){
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        mykey = pref.getString("key", "");
        db.child(mykey).child(searchkey).setValue("0");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);
        ButterKnife.bind(this);

        db = FirebaseDatabase.getInstance().getReference().child("profiles");
    }
}
