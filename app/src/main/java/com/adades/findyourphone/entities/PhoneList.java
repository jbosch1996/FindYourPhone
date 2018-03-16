package com.adades.findyourphone.entities;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adades.findyourphone.AdminActivity;
import com.adades.findyourphone.HomeActivity;
import com.adades.findyourphone.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    public static AlertDialog alertDialogUpdate;

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
                //edtPhoneName.setText(phone.getName());
                //AdminActivity.phoneId = phone.getId();
                showUpdateDialog(phone.getId(),phone.getName(),phone.getImageUri(),phone.getCpu(),phone.getStorage(),phone.getRam(),phone.getSo());
            }
        });
        return listViewItem;

    }



    public void showUpdateDialog(final String phoneID,String phoneName,final String imageUri,final String phoneCpu,final String phoneStorage,final String phoneRam,final String phoneSo){
        AlertDialog.Builder dialogBuilderUpdate = new AlertDialog.Builder(context);
        final View dialogUpdateView = LayoutInflater.from(context).inflate(R.layout.dialog_update_phone_admin,null);

        //final View dialogUpdateView = inflaterUpdate.inflate(R.layout.dialog_update_phone_admin,null);

        dialogBuilderUpdate.setView(dialogUpdateView);

        final EditText namePhoneUpdate = dialogUpdateView.findViewById(R.id.nameUpdatePhone);
        final EditText cpuPhoneUpdate = dialogUpdateView.findViewById(R.id.cpuUpdatePhone);
        final EditText storagePhoneUpdate = dialogUpdateView.findViewById(R.id.storageUpdatePhone);
        final EditText ramPhoneUpdate = dialogUpdateView.findViewById(R.id.ramUpdatePhone);
        final EditText soPhoneUpdate = dialogUpdateView.findViewById(R.id.soUpdatePhone);
        final Button buttonUpdate = dialogUpdateView.findViewById(R.id.btnSaveUpdate);
        namePhoneUpdate.setText(phoneName);
        cpuPhoneUpdate.setText(phoneCpu);
        storagePhoneUpdate.setText(phoneStorage);
        ramPhoneUpdate.setText(phoneRam);
        soPhoneUpdate.setText(phoneSo);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newNamePhone = namePhoneUpdate.getText().toString().trim();
                String newCpuPhone = cpuPhoneUpdate.getText().toString().trim();
                String newStoragePhone = storagePhoneUpdate.getText().toString().trim();
                String newRamPhone = ramPhoneUpdate.getText().toString().trim();
                String newSoPhone = soPhoneUpdate.getText().toString().trim();
                updatePhone(phoneID,newNamePhone,imageUri,newCpuPhone,newStoragePhone,newRamPhone,newSoPhone);
                alertDialogUpdate.dismiss();
                Toast.makeText(context, "Updated!",Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogUpdate = dialogBuilderUpdate.create();
        alertDialogUpdate.show();
    }
    private boolean updatePhone(String phoneId,String phoneName,String imageUri,String phoneCpu,String phoneStorage,String phoneRam,String phoneSo){
        databaseReference = FirebaseDatabase.getInstance().getReference("phones").child(phoneId);
        Phone phoneUpdate = new Phone(phoneId,phoneName,imageUri,phoneCpu,phoneStorage,phoneRam,phoneSo);
        databaseReference.setValue(phoneUpdate);
        return true;
    }
}