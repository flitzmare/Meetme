package riksasuviana.apps.meetme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by riksasuviana on 19/01/17.
 */

public class CustomDialogClass extends Dialog implements View.OnClickListener {

    DatabaseReference db;

    String searchkey;

    String mykey;

    Activity a;
    Dialog d;
    Button add, search;
    EditText text;

    TextView tv, name, email;

    Toast t;

//    String searchtext;


    public CustomDialogClass(Activity a){
        super(a);
        this.a = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_addfriend);
        add =  (Button)findViewById(R.id.btnadd);
        add.setOnClickListener(this);
        search = (Button)findViewById(R.id.btnsearch);
        search.setOnClickListener(this);
        text = (EditText)findViewById(R.id.textsearch);

        tv = (TextView)findViewById(R.id.friendtv);
        name = (TextView)findViewById(R.id.textname);
        email = (TextView)findViewById(R.id.textemail);

        db = FirebaseDatabase.getInstance().getReference().child("profiles");

        SharedPreferences pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        mykey = pref.getString("key", "");
//        tv.setText(mykey);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnadd:
                if(searchkey.equals(mykey)){
                    showToast("You cannot add yourself as friend");
                }

                else {
                    db.child(searchkey).child("friendrequest").child(mykey).setValue("0").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            showToast("Friend Request Sended");
                        }
                    });
                    dismiss();
                }
                break;
            case R.id.btnsearch:
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
                break;
            default:
                break;
        }
    }

    public void showToast(String msg){
        if(t!=null){
            t.cancel();
        }
        t = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        t.show();
    }
}
