package com.example.ketan_studio.hospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ketan_studio.hospital.Doctor.DoctorHomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Activity extends AppCompatActivity {
    TextView reg;
    TextInputEditText useremail,userpassword;
    Button login;
    FirebaseAuth mAuth;
    DatabaseReference UserReff;
    ProgressDialog loadingbar;
    String parentdb = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        mAuth = FirebaseAuth.getInstance();
        useremail = (TextInputEditText)findViewById(R.id.email_login_id);
        userpassword = (TextInputEditText)findViewById(R.id.password_login_id);
        login =(Button)findViewById(R.id.login_login_id);
        loadingbar = new ProgressDialog(this);

        final Spinner sp = (Spinner)findViewById(R.id.spin);
        ArrayAdapter<CharSequence> arrayAdapter;
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.spinner,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(arrayAdapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                if (text.equals("User")){
                    parentdb = "User";
                    Toast.makeText(Login_Activity.this, "User", Toast.LENGTH_SHORT).show();
                }else if (text.equals("Doctor"))
                {
                    parentdb = "Doctor";
                    Toast.makeText(Login_Activity.this, "Doctor", Toast.LENGTH_SHORT).show();
                }else if (text.equals("Admin"))
                {
                    parentdb = "Admins";
                    Toast.makeText(Login_Activity.this, "Admins", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Login_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAthentication();
            }
        });

        reg = (TextView)findViewById(R.id.register_login_id);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(Login_Activity.this,Register_Activity.class);
                startActivity(r);
                finish();
            }
        });
    }

    private void LoginAthentication() {
        loadingbar.setTitle("SignIN");
        loadingbar.setMessage("Please wait until we signIN");
        loadingbar.show();
        final String email = useremail.getText().toString();
        final String password = userpassword.getText().toString();
        if (TextUtils.isEmpty(email)){
            Toast.makeText(Login_Activity.this, "Email cannot be Empty", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(password)){
            Toast.makeText(Login_Activity.this, "Password cannot be Empty", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Login_Activity.this, "SignIN Sucessful", Toast.LENGTH_SHORT).show();
//                                ValidatingUserFromDatasebase(email,password);
                        if (parentdb.equals("Admins")){
                            Toast.makeText(Login_Activity.this, "Admins Sucess", Toast.LENGTH_SHORT).show();
                            SendAdminToAdminHomePage();
                            parentdb = "User";
                            finish();
                            loadingbar.dismiss();
                        }else if (parentdb.equals("Doctor")){
                            UserReff = FirebaseDatabase.getInstance().getReference("User");
                            final String uid = mAuth.getCurrentUser().getUid();
                            UserReff.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child(uid).exists()){
                                        Toast.makeText(Login_Activity.this, "You are User", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                    }else {
                                        SendDoctorToDoctorHomePage();
                                        parentdb = "User";
                                        Toast.makeText(Login_Activity.this, "Doctor Sucess", Toast.LENGTH_SHORT).show();
                                        loadingbar.dismiss();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }else {
                            SendUserToHomePage();
                            parentdb = "User";
                            Toast.makeText(Login_Activity.this, "User Sucess", Toast.LENGTH_SHORT).show();
                            loadingbar.dismiss();
                        }

                    }else {
                        String message = task.getException().getMessage();
                        Toast.makeText(Login_Activity.this, "Error"+message, Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                    }
                }
            });
        }
    }

    private void SendAdminToAdminHomePage() {
        Intent admin = new Intent(Login_Activity.this,Admin_Activity.class);
        startActivity(admin);
        finish();
    }

    private void SendUserToHomePage() {
        Intent su = new Intent(Login_Activity.this,MainActivity.class);
        startActivity(su);
        finish();
    }
    private void SendDoctorToDoctorHomePage(){
        Intent u = new Intent(Login_Activity.this, DoctorHomePage.class);
        startActivity(u);
        finish();
    }
}
