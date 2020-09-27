package com.example.ketan_studio.hospital;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class Specification_Activity extends AppCompatActivity {

    DatabaseReference myReff;
    StorageReference storageReference;
    EditText add;
    int gallerycode = 1;
    Button submit;
    long maxid = 0;

    Uri imageUri;
    Button chooseButton;
    ImageView chooseImageView;
    String downloadlink;
    String url;
    ProgressDialog progressDialog;
    StorageReference finalupload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specification_);


        add = (EditText) findViewById(R.id.add_specification_id);
        submit = (Button) findViewById(R.id.submit_specification_id);
        chooseButton = (Button) findViewById(R.id.Button_chooseImage_Specification_id);
        chooseImageView = (ImageView) findViewById(R.id.Image_chooseImage_Specification_id);
        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToGallary();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addd = add.getText().toString();
                if (TextUtils.isEmpty(addd)) {
                    Toast.makeText(Specification_Activity.this, "Enter Specification", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog = new ProgressDialog(Specification_Activity.this);
                    progressDialog.setTitle("Adding Specification");
                    progressDialog.setMessage("Please wait until we Upload Image");
                    progressDialog.show();
                    SendDataToDatabase();
                }
            }
        });
    }

    private void SendDataToDatabase() {
        storageReference = FirebaseStorage.getInstance().getReference("Specifiction");
        finalupload = storageReference.child(imageUri.getLastPathSegment()+".jpg");
        Log.i("Exception","Finalupload Level 1 :"+finalupload);
        final UploadTask upload = finalupload.putFile(imageUri);
        upload.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Specification_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Specification_Activity.this, "Photo Uploaded", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask = upload.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            Toast.makeText(Specification_Activity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                        downloadlink = finalupload.getDownloadUrl().toString();
                        Log.i("Exception","Download link Level 1 :"+downloadlink);
                        Log.i("Exception","Final Upload link Level 2 :"+finalupload);
                        return finalupload.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadlink = task.getResult().toString();
                            SaveImageToDataBase();
                        }
                    }
                });
            }
        });

    }

    private void SaveImageToDataBase() {
        String c = add.getText().toString();
        DatabaseReference mainReff = FirebaseDatabase.getInstance().getReference("MainSpecification");
        myReff = FirebaseDatabase.getInstance().getReference("Specification");
        HashMap<String, Object> map = new HashMap<>();
        Log.i("Exception","Download link Level 2 :"+downloadlink);
        map.put("Image", downloadlink);
        map.put("Specification", c);
        myReff.child(c).setValue(map);
        HashMap<String, Object> main = new HashMap<>();
        main.put("MainSpec", c);
        mainReff.child(c).setValue(main);
        add.setText("");
        progressDialog.dismiss();

    }

    private void SendToGallary() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, gallerycode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallerycode && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            chooseImageView.setImageURI(imageUri);
        }
    }

    private String getFileExtention(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(cr.getType(uri));
    }

//    private void SendDataToDatabaseNew() {
//        if (imageUri != null){
//            storageReference = FirebaseStorage.getInstance().getReference("Specifiction");
//            StorageReference fileReff = storageReference.child(System.currentTimeMillis()+"."+getFileExtention(imageUri));
//            fileReff.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            progressDialog.setProgress(0);
//                        }
//                    },5000);
//                    Toast.makeText(Specification_Activity.this, "Photo Uploaded succes", Toast.LENGTH_SHORT).show();
//                    taskSnapshot.getUploadSessionUri();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(Specification_Activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
//                    double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                    progressDialog.setProgress((int)progress);
//                }
//            })
//        }else {
//            Toast.makeText(this, "No file Selected", Toast.LENGTH_SHORT).show();
//        }
//    }

}
