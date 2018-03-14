package com.adades.findyourphone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.adades.findyourphone.entities.Phone;
import com.adades.findyourphone.entities.PhoneList;
import com.adades.findyourphone.entities.PhoneListUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    ListView listViewPhones;
    List<Phone> phones;

    public static final String DATABASE_PATH = "phones";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        phones = new ArrayList<Phone>();
        listViewPhones = findViewById(R.id.listPhonesUser);
        databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);
    }
    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                phones.clear();

                for(DataSnapshot postSnapShot: dataSnapshot.getChildren()){
                    Phone phone = postSnapShot.getValue(Phone.class);
                    phones.add(phone);
                }

                PhoneListUser phoneAdapter = new PhoneListUser(HomeActivity.this, phones,databaseReference);
                listViewPhones.setAdapter(phoneAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

