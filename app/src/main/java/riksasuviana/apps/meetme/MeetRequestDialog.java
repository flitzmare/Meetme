package riksasuviana.apps.meetme;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by riksasuviana on 22/01/17.
 */

public class MeetRequestDialog extends Dialog implements View.OnClickListener {
    Button acc, rej;

    TextView namedialog, photodialog;

    String name, photo, key, mykey;

    Activity a;

    DatabaseReference ref, myref;

    double longitude, latitude;

    public MeetRequestDialog(Activity a, String key){
        super(a);
        this.a = a;
        this.key = key;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.formeetrequest);

        acc = (Button)findViewById(R.id.macc);
        acc.setOnClickListener(this);
        rej = (Button)findViewById(R.id.mrej);
        rej.setOnClickListener(this);

        SharedPreferences pref = getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
        mykey = pref.getString("key", "");

        ref = FirebaseDatabase.getInstance().getReference().child("profiles").child(key);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("name").getValue(String.class);
                photo = dataSnapshot.child("photo").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myref = FirebaseDatabase.getInstance().getReference().child("profiles").child(mykey);

        namedialog = (TextView)findViewById(R.id.mname);
        namedialog.setText(name);
        photodialog = (TextView)findViewById(R.id.mphoto);
        photodialog.setText(photo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.macc:
                //go to map activity

                latitude = 0;
                longitude = 0;
                PositionInput p = new PositionInput(latitude, longitude);
                myref.child("pos").setValue(p);
                ref.child("pos").setValue(p);
                ref.child("meetrequest").removeValue();
                Intent i = new Intent(getContext(), MapsActivity.class);
                i.putExtra("key", key);
                getContext().startActivity(i);
                break;
            case R.id.mrej:

                break;
            default:
                break;
        }
    }
}

