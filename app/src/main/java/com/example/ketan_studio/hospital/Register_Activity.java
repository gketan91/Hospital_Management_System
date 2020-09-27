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

import com.example.ketan_studio.hospital.Doctor.ExtraDoctorInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register_Activity extends AppCompatActivity {
    TextView login;
    TextInputEditText email,password,name;
    Button signin;
    FirebaseAuth mAuth;
    ProgressDialog loadingbar;
    String parentdb="User";
    String username;
    DatabaseReference userReff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        login = (TextView)findViewById(R.id.login_reg_id);
        name = (TextInputEditText)findViewById(R.id.name_reg_id);
        email = (TextInputEditText)findViewById(R.id.email_reg_id);
        password = (TextInputEditText)findViewById(R.id.password_reg_id);
        signin = (Button)findViewById(R.id.signin_reg_id);
        loadingbar = new ProgressDialog(this);

        userReff = FirebaseDatabase.getInstance().getReference("User");

        final Spinner spin = (Spinner)findViewById(R.id.spinner_register_id);
        ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(this,R.array.Reg_spinner,android.R.layout.simple_spinner_item);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adap);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                if (text.equals("User")){
                    parentdb = "User";
                    Toast.makeText(Register_Activity.this, "User", Toast.LENGTH_SHORT).show();
                }else if (text.equals("Doctor"))
                {
                    parentdb = "Doctor";
                    Toast.makeText(Register_Activity.this, "Doctor", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Register_Activity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewUserAccount();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendUserToLoginPage();
            }
        });
    }

    private void CreateNewUserAccount() {

        final String mail = email.getText().toString();
        final String pass = password.getText().toString();
        username = name.getText().toString();
        mAuth = FirebaseAuth.getInstance();
        if(TextUtils.isEmpty(mail)){
            Toast.makeText(Register_Activity.this, "Email cannot be Empty", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pass)){
            Toast.makeText(Register_Activity.this, "Password cannot be Empty", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(username)){
            Toast.makeText(Register_Activity.this, "Username cannot be Empty", Toast.LENGTH_SHORT).show();
        }else {
            loadingbar.setTitle("Creating User Account");
            loadingbar.setMessage("Please wait until we create Account");
            loadingbar.show();
            mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Register_Activity.this, "Created User", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                        if (parentdb.equals("Doctor")){
                            SendExtraTOHomePage();
                        }if (parentdb.equals("User")){
                            HashMap<String,Object> map = new HashMap<>();
                            map.put("Email",mAuth.getCurrentUser().getEmail()+"");
                            map.put("UID",mAuth.getCurrentUser().getUid()+"");
                            map.put("Pateint Name",username+"");
                            userReff.child(mAuth.getCurrentUser().getUid()).setValue(map);
                            SendUserToHomePage();
                        }
                    }else {
                        loadingbar.dismiss();
                        String message = task.getException().getMessage();
                        Toast.makeText(Register_Activity.this,"Error:"+message,Toast.LENGTH_SHORT).show();                    }
                }
            });
        }
    }

    private void SendUserToLoginPage() {
        Intent lp = new Intent(Register_Activity.this,Login_Activity.class);
        startActivity(lp);
        finish();
    }

    private void SendExtraTOHomePage() {
        Intent doc = new Intent(Register_Activity.this, ExtraDoctorInfo.class);
        doc.putExtra("Username",username);
        startActivity(doc);
        finish();
    }

    private void SendUserToHomePage() {
        Intent su = new Intent(Register_Activity.this,MainActivity.class);
        startActivity(su);
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }
}
