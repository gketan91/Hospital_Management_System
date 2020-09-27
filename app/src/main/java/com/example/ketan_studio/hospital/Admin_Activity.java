package com.example.ketan_studio.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Admin_Activity extends AppCompatActivity {

    Button userlist,specifictionButton,doctor;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_);
        mAuth = FirebaseAuth.getInstance();
        userlist = (Button)findViewById(R.id.userlist_admin_id);
        userlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent list = new Intent(Admin_Activity.this,UserList_Activity.class);
                startActivity(list);
            }
        });

        specifictionButton = (Button)findViewById(R.id.specification_admin_id);
        specifictionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent spec = new Intent(Admin_Activity.this,Specification_Activity.class);
                startActivity(spec);
            }
        });
        doctor = (Button)findViewById(R.id.doctor_admin_id);
        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent doc = new Intent(Admin_Activity.this, Admin_Doctor_Activity.class);
                startActivity(doc);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
        mAuth.signOut();
        finish();
    }
}
