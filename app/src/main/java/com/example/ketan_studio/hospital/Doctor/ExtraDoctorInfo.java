package com.example.ketan_studio.hospital.Doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ketan_studio.hospital.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ExtraDoctorInfo extends AppCompatActivity {

    EditText docAddress,docQualification,docPhone,time;
    Button submit;
    DatabaseReference DocReff;
    DatabaseReference SepReff;
    FirebaseAuth mAuth;
    Spinner spinner;
    ValueEventListener lis;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerdatalist;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_doctor_info);
        mAuth = FirebaseAuth.getInstance();
        docAddress = (EditText)findViewById(R.id.address_extraDoc_id);
        docQualification = (EditText)findViewById(R.id.quali_extraDoc_id);
        docPhone = (EditText)findViewById(R.id.phone_extraDoc_id);
        submit = (Button) findViewById(R.id.submit_addoc_id);
        spinner = (Spinner)findViewById(R.id.spinner);

        Intent username = getIntent();
        name = username.getStringExtra("Username");
        //GETTING dATA FROM EXTRA ACTIVITY


        spinnerdatalist = new ArrayList<>();
        adapter = new ArrayAdapter<>(ExtraDoctorInfo.this,android.R.layout.simple_spinner_dropdown_item,spinnerdatalist);
        spinner.setAdapter(adapter);
        retriveData();

        time = (EditText)findViewById(R.id.timeSelector);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetailsofDoctor();
            }
        });
    }

    private void getDetailsofDoctor() {
        DocReff = FirebaseDatabase.getInstance().getReference("Doctor");
        final String uid = mAuth.getCurrentUser().getUid();
        String address = docAddress.getText().toString();
        String Qualification = docQualification.getText().toString();
        String Phone = docPhone.getText().toString();
        String timeResult = time.getText().toString();
        String s = String.valueOf(spinner.getSelectedItem());
        final HashMap<String,Object> extra = new HashMap<>();
        extra.put("UID",uid);
        extra.put("Name",name);
        extra.put("Address",address);
        extra.put("Qualification",Qualification);
        extra.put("Phone",Phone);
        extra.put("time",timeResult);
        extra.put("Specification",s);
        DocReff.child(uid).updateChildren(extra).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ExtraDoctorInfo.this, "Data Addedd", Toast.LENGTH_SHORT).show();
                    SendUserToDoctorHomePage();
                }else {
                    Toast.makeText(ExtraDoctorInfo.this, "Data Not Added Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SendUserToDoctorHomePage() {
        Intent homePage = new Intent(ExtraDoctorInfo.this, DoctorHomePage.class);
        startActivity(homePage);
        finish();
    }

    private void retriveData() {
        SepReff = FirebaseDatabase.getInstance().getReference("MainSpecification");
        lis = SepReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()){
                    String mess = item.getValue().toString();
                    String len = mess.substring(10,mess.length()-1);
                    spinnerdatalist.add(len);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
