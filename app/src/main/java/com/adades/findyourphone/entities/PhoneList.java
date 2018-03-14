package com.adades.findyourphone.entities;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.adades.findyourphone.AdminActivity;
import com.adades.findyourphone.HomeActivity;
import com.adades.findyourphone.R;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alu2012081 on 09/03/2018.
 */

public class PhoneList extends ArrayAdapter<Phone> {

    private Activity context;
    private List<Phone> phones;
    DatabaseReference databaseReference;
    EditText edtPhoneName;

    public PhoneList(@NonNull Activity context,List<Phone> phones,DatabaseReference databaseReference,EditText edtPhoneName) {
        super(context, R.layout.layout_phone_list,phones);
        this.context = context;
        this.phones = phones;
        this.databaseReference = databaseReference;
        this.edtPhoneName = edtPhoneName;
    }


    public View getView(int pos, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_phone_list,null,true);

        TextView txtPhoneName = listViewItem.findViewById(R.id.txtPhoneName);
        ImageView imageViewPhone = listViewItem.findViewById(R.id.imgPhone);
        Button btnDelete = listViewItem.findViewById(R.id.btnDelete);
        Button btnUpdate = listViewItem.findViewById(R.id.btnUpdate);

        final Phone phone = phones.get(pos);
        txtPhoneName.setText(phone.getName());
        Picasso.get().load(phone.getImageUri()).into(imageViewPhone);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child(phone.getId()).removeValue();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtPhoneName.setText(phone.getName());
                AdminActivity.phoneId = phone.getId();
            }
        });
        return listViewItem;
    }
}
