package com.example.ketan_studio.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ConfirmBooking extends AppCompatActivity {
    TextView patienName,DrName;
    DatabaseReference patienReff;
    String name;

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);
        patienName = (TextView)findViewById(R.id.patienName_confirmbooking_id) ;
        DrName = (TextView)findViewById(R.id.DrName_confirmbooking_id) ;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        Intent i = getIntent();
        final String drname = i.getStringExtra("drname");
        String drquali = i.getStringExtra("drQuai");
        String uid = mAuth.getCurrentUser().getUid();
        Toast.makeText(this, ""+uid, Toast.LENGTH_LONG).show();
        patienReff = FirebaseDatabase.getInstance().getReference("User").child(uid).child("Pateint Name");
        patienReff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.getValue().toString();
                patienName.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ConfirmBooking.this, "DatabaseError"+databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        DrName.setText(drname);
        submit = (Button)findViewById(R.id.Button_confirmbooking_id);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference AppoinmentReff = FirebaseDatabase.getInstance().getReference("Appionment");
                String user = patienName.getText().toString();
                String dr = DrName.getText().toString();
                HashMap<String,Object> map = new HashMap<>();
                map.put("Patient_Name",user);
                map.put("Doctor_Name",dr);
                AppoinmentReff.push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ConfirmBooking.this, "Appoinmnet Booked", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ConfirmBooking.this,MainActivity.class);
                        startActivity(i);
                    }
                });
            }
        });
    }
}
