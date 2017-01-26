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
 * Created by riksasuviana on 21/01/17.
 */

public class ProfileDialog extends Dialog implements View.OnClickListener{

    Button chat, meet;

    TextView namedialog, photodialog;

    String name, photo, mykey, key;

    Activity a;

    DatabaseReference ref;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chat:
                ref.child(key).child("friends").child(mykey).setValue("1");
                ref.child(mykey).child("friends").child(key).setValue("1");
                break;
            case R.id.meet:
                ref.child(key).child("meetrequest").setValue(mykey);
                Toast.makeText(getContext(), "Meet Request Sended", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
