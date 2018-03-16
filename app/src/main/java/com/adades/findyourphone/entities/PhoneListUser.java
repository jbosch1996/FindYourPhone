package com.adades.findyourphone.entities;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

        final TextView txtPhoneName = listViewItem.findViewById(R.id.namePhoneUser);
        final ImageView imageViewPhone = listViewItem.findViewById(R.id.imagePhoneUser);

        final Phone phone = phoneList.get(pos);
        txtPhoneName.setText(phone.getName());
        Picasso.get().load(phone.getImageUri()).into(imageViewPhone);

        listViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(context);
                View mView = LayoutInflater.from(context).inflate(R.layout.dialog_show_phone_user,null);
                TextView phoneName = mView.findViewById(R.id.phoneNameDialog);
                ImageView ivPhone = mView.findViewById(R.id.ivDialog);
                TextView os = mView.findViewById(R.id.osDialogUser);
                TextView ram = mView.findViewById(R.id.ramDialogUser);
                TextView cpu = mView.findViewById(R.id.cpuDialogUser);
                TextView storage = mView.findViewById(R.id.storageDialogUser);

                phoneName.setText(txtPhoneName.getText());
                ivPhone.setImageBitmap(((BitmapDrawable) imageViewPhone.getDrawable()).getBitmap());
                os.setText(phone.getSo());
                ram.setText(phone.getRam());
                cpu.setText(phone.getCpu());
                storage.setText(phone.getStorage());

                mbuilder.setView(mView);
                AlertDialog dialog = mbuilder.create();
                dialog.show();
            }
        });

        return listViewItem;
    }
}
