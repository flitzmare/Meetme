package riksasuviana.apps.meetme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    DatabaseReference db, ncheck, intentcheck;

    String name, email, key, uid;

    @BindView(R.id.vp) ViewPager vp;

    @BindView(R.id.sp) TextView sp;

    @BindView(R.id.tl) TabLayout tl;

    @OnClick(R.id.pushbtn) void push(){
        startActivity(new Intent(HomeActivity.this, MapsActivity.class));
        Toast.makeText(HomeActivity.this, "Clicked", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.fab) void fab(){
//        startActivity(new Intent(HomeActivity.this, AddfriendActivity.class));
        CustomDialogClass cdc = new CustomDialogClass(HomeActivity.this);
        cdc.show();
    }

    @OnClick(R.id.chatlistbtn) void chat(){
        startActivity(new Intent(HomeActivity.this, ChatlistActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        tl.addTab(tl.newTab().setText("Friends"));
        tl.addTab(tl.newTab().setText("Chat"));

        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl));
        tl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vp.setAdapter(new HomePagerAdapter(getSupportFragmentManager(), tl.getTabCount()));

        //firebase

        db = FirebaseDatabase.getInstance().getReference().child("profiles");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(firebaseAuth.getCurrentUser() == null){
                    Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        };

        FirebaseUser u = mAuth.getCurrentUser();
        if(u != null) {
            uid = u.getUid();
        }
        SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("key", uid);
        editor.apply();
        name = pref.getString("name","");
        email = pref.getString("email","");
        key = pref.getString("key","");

        ncheck = db.child(key);
        ncheck.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("meetrequest")){
                    sp.setText(dataSnapshot.child("meetrequest").getValue(String.class));
                    MeetRequestDialog d = new MeetRequestDialog(HomeActivity.this, dataSnapshot.child("meetrequest").getValue(String.class));
                    d.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        intentcheck = db.child(key);
        intentcheck.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("pos")){
                    startActivity(new Intent(HomeActivity.this, MapsActivity.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(final MenuItem item){
        switch (item.getItemId()){
            case R.id.signout:
                mAuth.signOut();
                SharedPreferences pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.clear();
                edit.apply();
                Toast.makeText(HomeActivity.this, "Logout", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
