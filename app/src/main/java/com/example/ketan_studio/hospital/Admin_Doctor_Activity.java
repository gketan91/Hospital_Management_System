package com.example.ketan_studio.hospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ketan_studio.hospital.Doctor.ExtraDoctorInfo;

public class Admin_Doctor_Activity extends AppCompatActivity {
    Button adddoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__pannel_admin);
        adddoc = (Button)findViewById(R.id.adddoctor_doc_id);
        adddoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adddoc = new Intent(Admin_Doctor_Activity.this, ExtraDoctorInfo.class);
                startActivity(adddoc);
            }
        });
    }
}
