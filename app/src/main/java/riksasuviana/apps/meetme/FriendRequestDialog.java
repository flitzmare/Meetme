package riksasuviana.apps.meetme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by riksasuviana on 22/01/17.
 */

public class FriendRequestDialog extends Dialog implements View.OnClickListener{

    Button acc, rej;

    TextView namedialog, photodialog;

    String name, photo, mykey, key;

    Activity a;

    DatabaseReference ref;

    public FriendRequestDialog(Activity a, String name, String photo, String key){
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
        setContentView(R.layout.forfriendrequest);

        ref = FirebaseDatabase.getInstance().getReference().child("profiles");

        SharedPreferences pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        mykey = pref.getString("key", "");

        namedialog = (TextView)findViewById(R.id.namerq);
        namedialog.setText(name + " key = "+key);
        photodialog = (TextView)findViewById(R.id.photorq);
        photodialog.setText(photo + "mykey = "+mykey);

        acc = (Button)findViewById(R.id.acc);
        acc.setOnClickListener(this);
        rej = (Button)findViewById(R.id.rej);
        rej.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.acc:
                ref.child(key).child("friends").child(mykey).setValue("0");
                ref.child(mykey).child("friends").child(key).setValue("0");
                ref.child(mykey).child("friendrequest").removeValue();
                dismiss();
                Toast.makeText(getContext(), "Friend Request Accepted", Toast.LENGTH_SHORT).show();
                //remove rv

                break;
            case R.id.rej:
                ref.child(mykey).child("friendrequest").removeValue();
                dismiss();
                break;
            default:
                break;
        }
    }
}

