package com.adades.findyourphone;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.adades.findyourphone.entities.Phone;
import com.adades.findyourphone.entities.PhoneList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    Button btnSave,btnBrowseImage,showDialog,btnSavePhoneDialog,btnBrowseImageDialog;
    EditText edtName,namePhoneDialog,cpuPhoneDialog,storagePhoneDialog,ramPhoneDialog,soPhoneDialog;
    DatabaseReference databaseReference;
    ListView listViewPhones;
    List<Phone> phones;
    ImageView imageView,insertImageDialog;
    public static AlertDialog dialog;
    private StorageReference storageReference;
    public static final String STORAGE_PATH = "images/";
    public static final String DATABASE_PATH = "phones";
    private Uri imageUri;


    public static String phoneId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
    phones = new ArrayList<Phone>();
    databaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH);

    //btnSave = findViewById(R.id.btnSave);
    //edtName = findViewById(R.id.editPhone);
    listViewPhones = findViewById(R.id.listViewPhones);
   // imageView = findViewById(R.id.insertImages);
    //btnBrowseImage = findViewById(R.id.btnBrowseImage);
    storageReference = FirebaseStorage.getInstance().getReference();
        showDialog = findViewById(R.id.btnshowDialog);
        showDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mbuilder = new AlertDialog.Builder(AdminActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_show_phone_admin,null);


                namePhoneDialog = mView.findViewById(R.id.namePhoneDialog);
                btnSavePhoneDialog = mView.findViewById(R.id.btnSavePhoneDialog);
                btnBrowseImageDialog = mView.findViewById(R.id.btnBrowseImageDialog);
                insertImageDialog = mView.findViewById(R.id.insertImageDialog);
                cpuPhoneDialog = mView.findViewById(R.id.cpuPhoneDialog);
                storagePhoneDialog = mView.findViewById(R.id.storagePhoneDialog);
                ramPhoneDialog = mView.findViewById(R.id.ramPhoneDialog);
                soPhoneDialog = mView.findViewById(R.id.soPhoneDialog);
                btnBrowseImageDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent,"Select Image"),0);
                    }
                });
                btnSavePhoneDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String nameDialog = namePhoneDialog.getText().toString();
                        String cpuDialog = cpuPhoneDialog.getText().toString();
                        String storageDialog = storagePhoneDialog.getText().toString();
                        String ramDialog = ramPhoneDialog.getText().toString();
                        String soDialog = soPhoneDialog.getText().toString();
                        final String id = databaseReference.push().getKey();
                        final Phone phoneDialog = new Phone(id,nameDialog,null,cpuDialog,storageDialog,ramDialog,soDialog);
                        if(imageUri != null){
                            //INSERT DATA

                            Toast.makeText(AdminActivity.this, "Uploading!",Toast.LENGTH_SHORT).show();

                            StorageReference reference = storageReference.child(STORAGE_PATH + System.currentTimeMillis() + "." + imageUri);
                            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Toast.makeText(AdminActivity.this, "Uploaded!",Toast.LENGTH_SHORT).show();
                                    phoneDialog.setImageUri(taskSnapshot.getDownloadUrl().toString());
                                    Log.d("Image",phoneDialog.getImageUri());
                                    databaseReference.child(id).setValue(phoneDialog);
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AdminActivity.this, "Fail image upload!",Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        }
                                    });
                        }
                        dialog.dismiss();
                    }
                });

                mbuilder.setView(mView);
                dialog = mbuilder.create();

                //DIALOG FULL SCREEN

                //WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                //lp.copyFrom(dialog.getWindow().getAttributes());
                //lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                //lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.show();
                //dialog.getWindow().setAttributes(lp);

            }

        });







    /*btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = edtName.getText().toString();
            if(TextUtils.isEmpty(phoneId)){
                //save
                final String id = databaseReference.push().getKey();
                final Phone phone = new Phone(id,name,null);

                if(imageUri != null){
                    //INSERT DATA

                    Toast.makeText(AdminActivity.this, "Uploading!",Toast.LENGTH_SHORT).show();

                    StorageReference reference = storageReference.child(STORAGE_PATH + System.currentTimeMillis() + "." + imageUri);
                    reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(AdminActivity.this, "Uploaded!",Toast.LENGTH_SHORT).show();
                            phone.setImageUri(taskSnapshot.getDownloadUrl().toString());
                            Log.d("Image",phone.getImageUri());
                            databaseReference.child(id).setValue(phone);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminActivity.this, "Fail image upload!",Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        }
                    });
                }else{
                    //TODO
                }
                Toast.makeText(AdminActivity.this, "Phone Created Successfully!",Toast.LENGTH_SHORT).show();
            }else{
                //update
                databaseReference.child(phoneId).child("name").setValue(name);
                //databaseReference.child(phoneId).child("imageUri").setValue();


                Toast.makeText(AdminActivity.this,"Phone Updated Successfully!",Toast.LENGTH_SHORT).show();
            }
            edtName.setText(null);
        }
    });*/
    }
    public void browseImages(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Select Image"),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK){
            imageUri = data.getData();

            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                insertImageDialog.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getActualImage (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
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

                PhoneList phoneAdapter = new PhoneList(AdminActivity.this, phones,databaseReference,edtName);
                listViewPhones.setAdapter(phoneAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
