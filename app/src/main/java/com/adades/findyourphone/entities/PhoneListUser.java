package com.adades.findyourphone.entities;

import android.app.Activity;
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
import com.adades.findyourphone.R;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alu2012081 on 12/03/2018.
 */

public class PhoneListUser extends ArrayAdapter<Phone> {

    private Activity context;
    private List<Phone> phoneList;
    DatabaseReference databaseReference;

    public PhoneListUser(Activity context, List<Phone> phoneList, DatabaseReference databaseReference) {
        super(context, R.layout.layout_phone_list_user,phoneList);
        this.context = context;
        this.phoneList = phoneList;
        this.databaseReference = databaseReference;
    }
    public View getView(int pos, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_phone_list_user,null,true);

        TextView txtPhoneName = listViewItem.findViewById(R.id.namePhoneUser);
        ImageView imageViewPhone = listViewItem.findViewById(R.id.imagePhoneUser);

        Phone phone = phoneList.get(pos);
        txtPhoneName.setText(phone.getName());
        Picasso.get().load(phone.getImageUri()).into(imageViewPhone);

        return listViewItem;
    }
}
