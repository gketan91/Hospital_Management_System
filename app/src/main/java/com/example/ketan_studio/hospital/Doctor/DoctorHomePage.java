package com.example.ketan_studio.hospital.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ketan_studio.hospital.Login_Activity;
import com.example.ketan_studio.hospital.R;
import com.example.ketan_studio.hospital.SeeAppinmentDoctorActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorHomePage extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference myref;
    Button logout_Doc,seeAppoinment;
    String value;
    ProgressDialog loadingBar;
    DatabaseReference docReff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home_page);
        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);
        loadingBar.setTitle("Downloading Info");
        loadingBar.setMessage("Please wait until we getInfo...");
        loadingBar.show();

        ///Getting Name
        String id = mAuth.getCurrentUser().getUid();
        docReff = FirebaseDatabase.getInstance().getReference("Doctor").child(id).child("Name");

        docReff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue().toString();
                Toast.makeText(DoctorHomePage.this, "First :"+value, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                loadingBar.dismiss();
            }
        });


        logout_Doc = (Button)findViewById(R.id.logout_Doc);
        logout_Doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });
        seeAppoinment = (Button)findViewById(R.id.seeAppoinment_doctorHome_id);
        seeAppoinment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DoctorHomePage.this, SeeAppinmentDoctorActivity.class);
                i.putExtra("DocName",value+"");
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            SendUsertoLoginPage();
       }else {
            Toast.makeText(this, "CheingUserExist", Toast.LENGTH_SHORT).show();
            CheckUserExisitance();

        }
    }

    private void CheckUserExisitance() {
        final String curentuserid = mAuth.getCurrentUser().getUid();
        myref = FirebaseDatabase.getInstance().getReference("Doctor");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(curentuserid)){
                    SendUsertoExtraSetupPage();
                    Toast.makeText(DoctorHomePage.this, "Sending TO Setup Activity", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DoctorHomePage.this, "Doc Data Aldready Exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void SendUsertoExtraSetupPage() {
        Toast.makeText(DoctorHomePage.this,"SendUser To Startup Activity sucess:",Toast.LENGTH_SHORT);
        Intent sutsa=new Intent(DoctorHomePage.this, ExtraDoctorInfo.class);
        sutsa.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(sutsa);
        finish();
    }

    private void SendUsertoLoginPage() {
        Intent sul=new Intent(DoctorHomePage.this, Login_Activity.class);
        sul.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(sul);
        finish();
    }
}
